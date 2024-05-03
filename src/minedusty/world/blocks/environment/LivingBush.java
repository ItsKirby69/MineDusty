package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.Sounds;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

import static arc.Core.atlas;

public class LivingBush extends Prop{
	public int sprites = 2;

	public TextureRegion[] regions;
	public TextureRegion[] botRegion;
	public TextureRegion[] centerRegion;

	@Override
	public void load(){
		super.load();
		regions = new TextureRegion[sprites];
		botRegion = new TextureRegion[sprites];
		centerRegion = new TextureRegion[sprites];

		for(int i = 0; i < sprites; i++){
			regions[i] = atlas.find(name + i, name);
			botRegion[i] = atlas.find(name + "-bot" + i, name);
			centerRegion[i] = atlas.find(name + "-center" + i, name);
		}
	}

	public LivingBush(String name){
        super(name);
		variants = 0;
		hasShadow = true;
		breakSound = Sounds.plantBreak;
    }

    public int lobesMin = 13, lobesMax = 13;
    public float botAngle = 60f, origin = 0.3f;
    public float sclMin = 30f, sclMax = 50f, magMin = 5f, magMax = 15f, timeRange = 40f, spread = 0f;

    static Rand rand = new Rand();

    @Override
    public void drawBase(Tile tile){

		int sprite = Mathf.randomSeed(id, 0, sprites - 1);

		rand.setSeed(tile.pos());
        float offset = rand.random(180f);
        int lobes = rand.random(lobesMin, lobesMax);
		//region = regions[sprite];

        for(int i = 0; i < lobes; i++){
            float ba =  i / (float)lobes * 360f + offset + rand.range(spread), angle = ba + Mathf.sin(Time.time + rand.random(0, timeRange), rand.random(sclMin, sclMax), rand.random(magMin, magMax));
            float w = region.width * region.scl(), h = region.height * region.scl();
            var region = Angles.angleDist(ba, 225f) <= botAngle ? botRegion[sprite] : regions[sprite];

            Draw.rect(region,
                tile.worldx() - Angles.trnsx(angle, origin) + w*0.5f, tile.worldy() - Angles.trnsy(angle, origin),
                w, h,
                origin*4f, h/2f,
                angle
            );
        }
		
        if(centerRegion[sprite] != null && centerRegion[sprite].found()){
            Draw.rect(centerRegion[sprite], tile.worldx(), tile.worldy());
        }
    }
}