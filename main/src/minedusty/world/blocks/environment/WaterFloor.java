package minedusty.world.blocks.environment;

import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.graphics.CacheLayer;
import mindustry.world.blocks.environment.Floor;

/** Mainly just to make making water tiles easier. Potentially new features here? */
public class WaterFloor extends Floor{

    public WaterFloor(String name) {
        this(name, 0);
    }

    public WaterFloor(String name, int variants) {
        super(name);
        this.variants = variants;
        
        //norm // deep 0.2, shallow 0.5, material 0.8
        //trop // deep 0.4, shallow 0.6, material 0.9
        speedMultiplier = 0.8f; 
        // deep 120f, shallow, 90f, mat, 50f
        statusDuration = 90f;
        // deep 1.5f, shallow & mat 1f, unless unique.
        liquidMultiplier = 1f;
        albedo = 0.9f;
        supportsOverlay = true;
        isLiquid = true;
        status = StatusEffects.wet;
        liquidDrop = Liquids.water;
        cacheLayer = CacheLayer.water;
    }
    
}
