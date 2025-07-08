package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Point2;
import arc.util.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.BuildVisibility;

import static arc.Core.*;

/** Custom Bush class having rare sprites at a chance as well as customized rotation of sprites. */
public class LivingBush extends Prop{
	public TextureRegion region, rareRegion;
	public TextureRegion[] bottomRegions, centerRegions, shadowRegions;

	public float layer = Layer.blockProp;
	/** Determines if center region rotates or not. not actually sure if it works or not lol*/
	public float rot = 0;
	/** Whether block uses single circle like seabushes or dual like ferns (which doesn't even use this option anymore lol) */
	public boolean dualCircleMode = false;
	public boolean rare = false;
	public float rareChance = 0.01f;

	public LivingBush(String name){
		this(name, 2);
	}
	public LivingBush(String name, int variants){
        super(name);
		this.variants = variants;
		hasShadow = true;
		breakSound = Sounds.plantBreak;
		buildVisibility = BuildVisibility.sandboxOnly;
		drawTeamOverlay = false;
		targetable = false;
		//moved these changes here as it seems like all bushes use these.
		lobesMin = 5;
		lobesMax = 6;
		magMin = 4;
		magMax = 6;
		sclMin = 20f;
		sclMax = 60f;
    }

	@Override
	public void load(){
		super.load();
		if(variants > 0){
			bottomRegions = new TextureRegion[variants];
			centerRegions = new TextureRegion[variants];
			shadowRegions = new TextureRegion[variants];

			for(int i = 0; i < variants; i++){
				bottomRegions[i] = atlas.find(name + "-bot" + (i + 1));
				centerRegions[i] = atlas.find(name + "-center" + (i + 1));
				shadowRegions[i] = atlas.find(name + "-shadow" + (i + 1));
			}
		}else{
			variantRegions = new TextureRegion[1];
			variantRegions[0] = atlas.find(name);
			bottomRegions = new TextureRegion[1];
			bottomRegions[0] = atlas.find(name + "-bot1");
			centerRegions = new TextureRegion[1];
			centerRegions[0] = atlas.find(name + "-center1");
			shadowRegions = new TextureRegion[1];
			shadowRegions[0] = atlas.find(name + "-shadow1");
		}
		rareRegion = atlas.find(name + "-rare");
		region = variantRegions[0];
	}

    public int lobesMin = 13, lobesMax = 13;
    public float botAngle = 60f, origin = 0.3f;
    public float sclMin = 30f, sclMax = 50f, magMin = 5f, magMax = 15f, timeRange = 40f, spread = 0f;
    static Rand rand = new Rand();


    @Override
    public void drawBase(Tile tile){
		rand.setSeed(tile.pos());

        float offset = rand.random(180f);
        int lobes = rand.random(lobesMin, lobesMax);
		float x = tile.worldx(), 
		y = tile.worldy(),
		w = region.width * region.scl(),
		h = region.height * region.scl(),
		//rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time + x, 50f, 0.5f) + Mathf.sin(Time.time - y, 65f, 0.9f) + Mathf.sin(Time.time + y - x, 85f, 0.9f),
		scl = 30f, mag = 0.2f;

		// I don't actually know if this is useful or not
		if (dualCircleMode) {
            // Dual circle mode: Draw bottom sprite
            for (int i = 0; i < lobes; i++) {
                float ba = i / (float)lobes * 360f + offset + rand.range(spread),
				angle = ba + Mathf.sin(Time.time + rand.random(0, timeRange), rand.random(sclMin, sclMax), rand.random(magMin, magMax));
                Draw.z(layer + 1);
                Draw.rect(bottomRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, bottomRegions.length - 1))],
					tile.worldx() - Angles.trnsx(angle, origin) + w*0.5f, 
					tile.worldy() - Angles.trnsy(angle, origin),
                        w, h,
                        origin*4f, h/2f,
                        angle
                );
            }
            // Dual circle mode: Draw main sprite
            for (int i = 0; i < lobes; i++) {
				float ba = (i + 0.5f) / (float)lobes * 360f + offset + rand.range(spread),
                angle = ba + Mathf.sin(Time.time + rand.random(0, timeRange), rand.random(sclMin, sclMax), rand.random(magMin, magMax));
                Draw.z(layer + 2);
                Draw.rect(variantRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, variantRegions.length - 1))],
					tile.worldx() - Angles.trnsx(angle, origin) + w*0.5f, 
					tile.worldy() - Angles.trnsy(angle, origin),
                        w, h,
                        origin*4f, h/2f,
                        angle
                );
            }
        } else {
            // Single circle mode
            for (int i = 0; i < lobes; i++) {
                float ba = i / (float) lobes * 360f + offset + rand.range(spread), 
                      angle = ba + Mathf.sin(Time.time + rand.random(0, timeRange), 
                                            rand.random(sclMin, sclMax), 
                                            rand.random(magMin, magMax));
                w = region.width * region.scl(); 
                h = region.height * region.scl();
                
                Draw.z(layer + 2);
                Draw.rect(Angles.angleDist(ba, 225f) <= botAngle ? bottomRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, bottomRegions.length - 1))] : variantRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, variantRegions.length - 1))],
                    tile.worldx() - Angles.trnsx(angle, origin) + w*0.5f, 
                    tile.worldy() - Angles.trnsy(angle, origin),
                    w, h,
                    origin*4f, h/2f,
                    angle
                );
            }
        }
		
		boolean useRare = rare && rareRegion.found() && rand.chance(rareChance);
		//centerRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, centerRegions.length - 1))]
		TextureRegion centerToDraw = useRare ? rareRegion : centerRegions[Mathf.randomSeed(tile.pos(), 0, centerRegions.length - 1)];
        if(centerToDraw.found()){ 
			Draw.z(layer + 3);
            //Draw.rect(centerRegions[sprite], tile.worldx(), tile.worldy());
			Draw.rectv(centerToDraw, x, y, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*2 + Time.time, scl, mag) + Mathf.sin(vec.x*2 - Time.time, 50, 0.6f),
				Mathf.cos(vec.x*2 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*2 - Time.time, 40, 0.1f)
				));
        }
		
		if(shadowRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, shadowRegions.length - 1))].found()){
			Draw.z(layer);
            Draw.rect(shadowRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, shadowRegions.length - 1))], tile.worldx(), tile.worldy());
        }
    }
}