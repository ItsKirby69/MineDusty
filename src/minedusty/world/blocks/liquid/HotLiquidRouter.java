package minedusty.world.blocks.liquid;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.LiquidRouter;

/** Same as {@link HotConduit} */
public class HotLiquidRouter extends LiquidRouter{
    public boolean melts = true;
    /** Temperature before conduit takes damage. 0.5f is room temp (default) */
    public float tempThresh = 0.5f;
    public float damageMulti = 1.5f;

    public HotLiquidRouter(String name) {
        super(name);
    }

    public class HotLiquidRouterBuild extends LiquidRouterBuild{
        @Override
        public void updateTile(){
            Liquid liquid = liquids.current();
            super.updateTile();

            if(liquids.currentAmount() > 0.01f && liquid.temperature > tempThresh && melts){
                damageContinuous(liquid.temperature * damageMulti / 100f);
                // Maybe be more original?
                if(Mathf.chanceDelta(0.01f)){
                    Fx.steam.at(x, y);
                }
            }
        }
    }
    
}
