package minedusty.graphics;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import minedusty.blocks.*;
import minedusty.content.*;

import static mindustry.Vars.*;

/** MenuRenderer revamped to use my modded blocks */
public class DustMenuRender extends MenuRenderer{
    private static final float darkness = 0.3f;
    private final int width = !mobile ? 100 : 60, height = !mobile ? 50 : 40;

    private int cacheFloor, cacheWall;
    private Camera camera = new Camera();
    private Mat mat = new Mat();
    private FrameBuffer shadows;
    private CacheBatch batch;
    private float time = 0f;
    private float flyerRot = 45f;
    private int flyers = Mathf.chance(0.2) ? Mathf.random(35) : Mathf.random(15);
    //no longer random or "dynamic", mod units in the menu look jarring, and it's not worth the configuration effort
	//UnitTypes.flare, UnitTypes.horizon, UnitTypes.zenith, UnitTypes.mono, UnitTypes.poly, UnitTypes.mega, UnitTypes.alpha, UnitTypes.beta, UnitTypes.gamma
    // private UnitType flyerType = content.units().select(u -> !u.isHidden() && u.hitSize <= 20f && u.flying && u.region.found()).random();
	private UnitType flyerType = Seq.with(DustUnitTypes.dazzle, DustUnitTypes.cricket, UnitTypes.flare, UnitTypes.horizon, UnitTypes.zenith, UnitTypes.mono, UnitTypes.poly, UnitTypes.mega, UnitTypes.alpha, UnitTypes.beta, UnitTypes.gamma).random();

    public DustMenuRender(){
        Time.mark();
        generate();
        cache();
        Log.debug("Time to generate menu: @", Time.elapsed());
    }

	private static final Block[][] selection = {
		{Blocks.sand, Blocks.salt, Blocks.sandWall, Blocks.saltWall},
		{Blocks.shale, Blocks.dirt, Blocks.shaleWall, Blocks.dirtWall},
		{Blocks.grass, DustEnv.pattedGrass, Blocks.shrubs, Blocks.shrubs},
		{Blocks.stone, Blocks.basalt, Blocks.stoneWall, Blocks.duneWall},
		{DustEnv.clayFloor, Blocks.salt, DustEnv.hardenedClayWall, Blocks.saltWall},
		{Blocks.dacite, Blocks.salt, Blocks.daciteWall, Blocks.saltWall},
		{DustEnv.basaltFloor, Blocks.salt, DustEnv.basaltWall, Blocks.saltWall},
		{DustEnv.calciteFloor, DustEnv.basaltFloor, DustEnv.calciteWall, DustEnv.basaltWall},
		{Blocks.snow, Blocks.salt, Blocks.snowWall, Blocks.saltWall},
		{DustEnv.permafrost, Blocks.snow, DustEnv.permafrostWall, Blocks.snowWall},
		{Blocks.ice, Blocks.snow, Blocks.iceWall, Blocks.snowWall}
	};

	private static final Block[][] props = {
		{Blocks.sand, DustPlants.sandyshrub, DustPlants.deadTree},
		{Blocks.grass, DustPlants.shrub, DustPlants.aliveTree},
		{DustEnv.clayFloor, DustPlants.clayshrub, DustEnv.clayBall},
		{DustEnv.basaltFloor, DustPlants.dustyshrub, DustPlants.dustyshrub},
		{Blocks.dacite, Blocks.daciteBoulder, Blocks.daciteBoulder},
		{DustEnv.calciteFloor, DustEnv.calciteBoulder, DustEnv.divineSapling}
	};

	// private final Seq<int[]> treePropPos = new Seq<>();
	// private Block treePropB;

    private void generate(){
        //suppress tile change events.
        world.setGenerating(true);

        Tiles tiles = world.resize(width, height);
        //only uses base game ores now, mod ones usually contrast too much with the floor
        Seq<Block> ores = Seq.with(DustEnv.oreOxidecopper, DustEnv.oreGalena, DustEnv.oreChlorophyte, DustEnv.oreChlorophyteChunk, DustEnv.oreAquamerium, Blocks.oreScrap, Blocks.oreCoal);
        shadows = new FrameBuffer(width, height);
        int offset = Mathf.random(100000);
        int s1 = offset, s2 = offset + 1, s3 = offset + 2;

        Block[] selected = Structs.random(
			new Block[]{Blocks.sand, Blocks.sandWall},
			new Block[]{Blocks.shale, Blocks.shaleWall},
			new Block[]{Blocks.sand, Blocks.sandWall},
			new Block[]{Blocks.dirt, Blocks.dirtWall},
			new Block[]{DustEnv.clayFloor, DustEnv.hardenedClayWall},
			new Block[]{Blocks.grass, Blocks.shrubs},
			new Block[]{Blocks.dacite, Blocks.daciteWall},
            new Block[]{DustEnv.calciteFloor, DustEnv.calciteWall},
			new Block[]{Blocks.salt, Blocks.saltWall},
            new Block[]{DustEnv.permafrost, DustEnv.permafrostWall},
            new Block[]{Blocks.basalt, Blocks.duneWall}
        );
        Block[] selected2 = Structs.random(
			new Block[]{Blocks.basalt, Blocks.duneWall},
			new Block[]{Blocks.stone, Blocks.stoneWall},
			new Block[]{DustEnv.basaltFloor, DustEnv.basaltWall},
			new Block[]{DustEnv.pattedGrass, Blocks.shrubs},
            new Block[]{Blocks.snow, Blocks.snowWall},
			new Block[]{Blocks.ice, Blocks.iceWall}
        );

		Block[][] selected3 = {
			{Blocks.grass, DustPlants.shrub, DustPlants.aliveTree},
			{Blocks.sand, DustPlants.sandyshrub, DustPlants.deadTree},
			{Blocks.sand, DustPlants.deadShrub, DustPlants.deadTree},
			{DustEnv.clayFloor, DustEnv.clayBall, DustEnv.largeAmethystCrystals},
			{DustEnv.basaltFloor, DustPlants.dustyshrub, DustPlants.elmTree},
			{DustEnv.basaltFloor, Blocks.basaltBoulder, DustPlants.elmTree},
			{DustEnv.calciteFloor, DustEnv.divineSapling, DustPlants.divineTree},
			{Blocks.stone, Blocks.boulder, DustPlants.deadTree},
			{Blocks.basalt, Blocks.basaltBoulder, DustPlants.deadTree},
			{Blocks.dacite, Blocks.daciteBoulder, DustPlants.deadTree},
            {DustEnv.permafrost, DustEnv.permafrostBoulder, DustPlants.deadTree}
		};

        Block ore1 = ores.random();
        ores.remove(ore1);
        Block ore2 = ores.random();

        double tr1 = Mathf.random(0.65f, 0.85f);
        double tr2 = Mathf.random(0.65f, 0.85f);
        boolean doheat = Mathf.chance(0.25);
        boolean tendrils = Mathf.chance(0.25);
        boolean tech = Mathf.chance(0.25);
        int secSize = 10;

        Block floord = selected[0], walld = selected[1];
        Block floord2 = selected2[0], walld2 = selected2[1];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                Block floor = floord;
                Block ore = Blocks.air;
                Block wall = Blocks.air;

                if(Simplex.noise2d(s1, 3, 0.5, 1/20.0, x, y) > 0.5){
                    wall = walld;
                }

                if(Simplex.noise2d(s3, 3, 0.5, 1/20.0, x, y) > 0.5){
                    floor = floord2;
                    if(wall != Blocks.air){
                        wall = walld2;
                    }
                }

                if(Simplex.noise2d(s2, 3, 0.3, 1/30.0, x, y) > tr1){
                    ore = ore1;
                }

                if(Simplex.noise2d(s2, 2, 0.2, 1/15.0, x, y+99999) > tr2){
                    ore = ore2;
                }

                if(doheat){
                    double heat = Simplex.noise2d(s3, 4, 0.6, 1 / 50.0, x, y + 9999);
                    double base = 0.65;

                    if(heat > base){
                        ore = Blocks.air;
                        wall = Blocks.air;
                        floor = Blocks.basalt;

                        if(heat > base + 0.1){
                            floor = Blocks.hotrock;

                            if(heat > base + 0.15){
                                floor = Blocks.magmarock;
                            }
                        }
                    }
                }

                if(tech){
                    int mx = x % secSize, my = y % secSize;
                    int sclx = x / secSize, scly = y / secSize;
                    if(Simplex.noise2d(s1, 2, 1f / 10f, 0.5f, sclx, scly) > 0.4f && (mx == 0 || my == 0 || mx == secSize - 1 || my == secSize - 1)){
                        floor = Blocks.darkPanel3;
                        if(Mathf.dst(mx, my, secSize/2, secSize/2) > secSize/2f + 1){
                            floor = Mathf.chance(0.6f) ? Blocks.darkPanel4 : Blocks.darkPanel5;
                        }


                        if(wall != Blocks.air && Mathf.chance(0.7)){
                            wall = Blocks.darkMetal;
                        }
                    }
                }

                if(tendrils){
                    if(Ridged.noise2d(1 + offset, x, y, 1f / 17f) > 0f){
                        floor = Mathf.chance(0.2) ? DustEnv.prismite : DustEnv.prismate;

                        if(wall != Blocks.air){
                            wall = DustEnv.prismiteBlock;
                        }
                    }
                }


                Tile tile;
                tiles.set(x, y, (tile = new CachedTile()));
                tile.x = (short)x;
                tile.y = (short)y;
                tile.setFloor(floor.asFloor());
                tile.setBlock(wall);
                tile.setOverlay(ore);
            }
        }

		// Props
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
				Tile tile = tiles.get(x, y);
				if(tile == null || tile.block() != Blocks.air) continue;

				for(Block[] prop : selected3){
					Block maskFloor = prop[0];
					Block smallProp = prop[1];
					// Block treeProp = prop[2];

					if(tile.floor() != maskFloor) continue;

					// if(Mathf.chance(0.004f)){
					// 	int s = treeProp.size;
					// 	int half = s / 2;
					// 	boolean fits = true;
					// 	for(int dx = -half; dx <= half && fits; dx++){
					// 		for(int dy = -half; dy <= half && fits; dy++){
					// 			Tile n = world.tile(x + dx, y + dy);
					// 			if(n == null || n.block() != Blocks.air){
					// 				fits = false;
					// 			}
					// 		}
					// 	}
					// 	if(fits){
					// 		treePropPos.add(new int[]{x, y});
					// 		treePropB = treeProp;
					// 		break;
					// 	}
					// }
					if(Mathf.chance(0.04f)){
						tile.setBlock(smallProp);
					}

					break;
				}
			}
		}
        // //don't fire a world load event, it just causes lag and confusion
        world.setGenerating(false);
    }

    private void cache(){

        //draw shadows
        Draw.proj().setOrtho(0, 0, shadows.getWidth(), shadows.getHeight());
        shadows.begin(Color.clear);
        Draw.color(Color.black);

        for(Tile tile : world.tiles){
            if(tile.block() != Blocks.air){
                Fill.rect(tile.x + 0.5f, tile.y + 0.5f, 1, 1);
            }
        }

        Draw.color();
        shadows.end();

        Batch prev = Core.batch;

        Core.batch = batch = new CacheBatch(new SpriteCache(width * height * 6, false));
        batch.beginCache();

        for(Tile tile : world.tiles){
            tile.floor().drawBase(tile);
        }

        for(Tile tile : world.tiles){
            tile.overlay().drawBase(tile);
        }

        cacheFloor = batch.endCache();
        batch.beginCache();

        for(Tile tile : world.tiles){
            tile.block().drawBase(tile);
        }

        cacheWall = batch.endCache();

        Core.batch = prev;
    }

    public void render(){
        time += Time.delta;
        float scaling = Math.max(Scl.scl(4f), Math.max(Core.graphics.getWidth() / ((width - 1f) * tilesize), Core.graphics.getHeight() / ((height - 1f) * tilesize)));
        camera.position.set(width * tilesize / 2f, height * tilesize / 2f);
        camera.resize(Core.graphics.getWidth() / scaling,
        Core.graphics.getHeight() / scaling);

        mat.set(Draw.proj());
        Draw.flush();
        Draw.proj(camera);
        batch.setProjection(camera.mat);
        batch.beginDraw();
        batch.drawCache(cacheFloor);
        batch.endDraw();
        Draw.color();
        Draw.rect(Draw.wrap(shadows.getTexture()),
        width * tilesize / 2f - 4f, height * tilesize / 2f - 4f,
        width * tilesize, -height * tilesize);
        Draw.flush();
        batch.beginDraw();
        batch.drawCache(cacheWall);
        batch.endDraw();

        // Atrocious
		// if(!treePropPos.isEmpty() && treePropB instanceof LivingTreeBlock tree){
		// 	for(int[] pos : treePropPos){
		// 		float wx = pos[0] * tilesize;
		// 		float wy = pos[1] * tilesize;
		// 		int variation = Mathf.randomSeed(Point2.pack(pos[0], pos[1]), 0, Math.max(0, tree.variantRegions.length - 1));
		// 		int rot = Mathf.randomSeed(Point2.pack(pos[0], pos[1]), 0, 4) * 90;
		// 		if(tree.shadowRegions[variation].found()){
		// 			Draw.z(tree.baseLayer - 4f);
		// 			Draw.rect(tree.shadowRegions[variation], wx + tree.shadowOffset, wy + tree.shadowOffset, rot);
		// 		}
		// 		if(tree.variantRegions[variation].found()){
		// 			Draw.z(tree.baseLayer);
		// 			Draw.rect(tree.variantRegions[variation], wx, wy, rot);
		// 		}
		// 		if(tree.backRegions[variation].found()){
		// 			Draw.z(tree.baseLayer - 1f);
		// 			Draw.rect(tree.backRegions[variation], wx, wy, rot);
		// 		}
		// 		if(tree.centerRegions[variation].found()){
		// 			Draw.z(tree.baseLayer + 1f);
		// 			Draw.rect(tree.centerRegions[variation], wx, wy, rot);
		// 		}
		// 		if(tree.middleRegions[variation].found()){
		// 			Draw.z(tree.baseLayer + 2f);
		// 			Draw.rect(tree.middleRegions[variation], wx, wy, rot);
		// 		}
		// 		if(tree.topRegions[variation].found()){
		// 			Draw.z(tree.baseLayer + 4f);
		// 			Draw.rect(tree.topRegions[variation], wx, wy, rot);
		// 		}
		// 	}
		// 	Draw.flush();
		// }

        drawFlyers();

        Draw.proj(mat);
        Draw.color(0f, 0f, 0f, darkness);
        Fill.crect(0, 0, Core.graphics.getWidth(), Core.graphics.getHeight());
        Draw.color();
    }

    private void drawFlyers(){
        flyerType.sample.elevation = 1f;
        flyerType.sample.team = Team.sharded;
        flyerType.sample.rotation = flyerRot;
        flyerType.sample.heal();

        flyers((x, y) -> {
            flyerType.sample.set(x, y);
            flyerType.drawShadow(flyerType.sample);
            flyerType.draw(flyerType.sample);
        });

    }

    private void flyers(Floatc2 cons){
        float tw = width * tilesize * 1f + tilesize;
        float th = height * tilesize * 1f + tilesize;
        float range = 500f;
        float offset = -100f;

        for(int i = 0; i < flyers; i++){
            Tmp.v1.trns(flyerRot, time * (flyerType.speed));

            cons.get(
            (Mathf.randomSeedRange(i, range) + Tmp.v1.x + Mathf.absin(time + Mathf.randomSeedRange(i + 2, 500), 10f, 3.4f) + offset) % (tw + Mathf.randomSeed(i + 5, 0, 500)),
            (Mathf.randomSeedRange(i + 1, range) + Tmp.v1.y + Mathf.absin(time + Mathf.randomSeedRange(i + 3, 500), 10f, 3.4f) + offset) % th
            );
        }
    }

    @Override
    public void dispose(){
        batch.dispose();
        shadows.dispose();
    }
}
