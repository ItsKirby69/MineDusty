package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

import static arc.Core.atlas;

public class LivingBush extends Prop{
	public TextureRegion botRegion;
	public TextureRegion centerRegion;

    public LivingBush(String name){
        super(name);
		variants = 0;
    }

	@Override
	public void load(){
		super.load();
		botRegion = atlas.find(name + "-bot");
		centerRegion = atlas.find(name + "-center");
	}

    public int lobesMin = 13, lobesMax = 13;
    public float botAngle = 60f, origin = 0.1f;
    public float sclMin = 30f, sclMax = 50f, magMin = 5f, magMax = 15f, timeRange = 40f, spread = 0f;

    static Rand rand = new Rand();

    @Override
    public void drawBase(Tile tile){
        rand.setSeed(tile.pos());
        float offset = rand.random(180f);
        int lobes = rand.random(lobesMin, lobesMax);
        for(int i = 0; i < lobes; i++){
            float ba =  i / (float)lobes * 360f + offset + rand.range(spread), angle = ba + Mathf.sin(Time.time + rand.random(0, timeRange), rand.random(sclMin, sclMax), rand.random(magMin, magMax));
            float w = region.width * region.scl(), h = region.height * region.scl();
            var region = Angles.angleDist(ba, 225f) <= botAngle ? botRegion : this.region;

            Draw.rect(region,
                tile.worldx() - Angles.trnsx(angle, origin) + w*0.5f, tile.worldy() - Angles.trnsy(angle, origin),
                w, h,
                origin*4f, h/2f,
                angle
            );
        }

        if(centerRegion.found()){
            Draw.rect(centerRegion, tile.worldx(), tile.worldy());
        }
    }
}