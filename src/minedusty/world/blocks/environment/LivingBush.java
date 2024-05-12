package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Point2;
import arc.util.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

import static arc.Core.*;

public class LivingBush extends SeaBush{
	public TextureRegion region;
	public TextureRegion[] variantRegions, bottomRegions, centerRegions, shadowRegions;

	public float layer = Layer.blockProp;

	public LivingBush(String name){
		this(name, 2);
	}
	public LivingBush(String name, int variants){
        super(name);
		this.variants = variants;
		hasShadow = true;
		breakSound = Sounds.plantBreak;
    }

	@Override
	public void load(){
		super.load();
		if(variants > 0){
			variantRegions = new TextureRegion[variants];
			bottomRegions = new TextureRegion[variants];
			centerRegions = new TextureRegion[variants];
			shadowRegions = new TextureRegion[variants];

			for(int i = 0; i < variants; i++){
				variantRegions[i] = atlas.find(name + (i + 1));
				bottomRegions[i] = atlas.find(name + "-bot" + (i + 1));
				centerRegions[i] = atlas.find(name + "-center" + (i + 1));
				shadowRegions[i] = atlas.find(name + "-shadow" + (i + 1));
			}
		}else{
			variantRegions = new TextureRegion[1];
			variantRegions[0] = atlas.find(name);
			bottomRegions = new TextureRegion[1];
			bottomRegions[0] = atlas.find(name + "-bot");
			centerRegions = new TextureRegion[1];
			centerRegions[0] = atlas.find(name + "-center");
			shadowRegions = new TextureRegion[1];
			shadowRegions[0] = atlas.find(name + "-shadow");
		}
		region = variantRegions[0];
	}

    public int lobesMin = 13, lobesMax = 13;
    public float botAngle = 60f, origin = 0.3f;
    public float sclMin = 30f, sclMax = 50f, magMin = 5f, magMax = 15f, timeRange = 40f, spread = 0f;

    static Rand rand = new Rand();

    @Override
    public void drawBase(Tile tile){
		//DO NOT CHANGE THIS TO Mathf.rand IT WILL MAKE THEM GROOVY
		rand.setSeed(tile.pos());
		int sprite = variant(tile.x, tile.y);

        float offset = rand.random(180f);
        int lobes = rand.random(lobesMin, lobesMax);
		
        for(int i = 0; i < lobes; i++){
            float ba =  i / (float)lobes * 360f + offset + rand.range(spread), angle = ba + Mathf.sin(Time.time + rand.random(0, timeRange), rand.random(sclMin, sclMax), rand.random(magMin, magMax));
            float w = region.width * region.scl(), h = region.height * region.scl()
			;
            var region = Angles.angleDist(ba, 225f) <= botAngle ? bottomRegions[sprite] : variantRegions[sprite];
			
			Draw.z(layer + 2);
            Draw.rect(region,
                tile.worldx() - Angles.trnsx(angle, origin) + w*0.5f, tile.worldy() - Angles.trnsy(angle, origin),
                w, h,
                origin*4f, h/2f,
                angle
            );
        }
		
        if(centerRegions[sprite].found()){ //no notes
			Draw.z(layer + 3);
            Draw.rect(centerRegions[sprite], tile.worldx(), tile.worldy());
        }
		
		if(shadowRegions[sprite].found()){
			Draw.z(layer);
            Draw.rect(shadowRegions[sprite], tile.worldx(), tile.worldy());
        }
    }

	public int variant(int x, int y){
		return Mathf.randomSeed(Point2.pack(x, y), 0, Math.max(0, variantRegions.length - 1));
	}
}