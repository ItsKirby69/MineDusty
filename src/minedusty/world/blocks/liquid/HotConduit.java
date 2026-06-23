package minedusty.world.blocks.liquid;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.Conduit;

/** Conduit vunerable to hot liquids. Yes this idea was taken from Aquarion. */
public class HotConduit extends Conduit{
    public boolean melts = true;
    /** Temperature before conduit takes damage. 0.5f is room temp (default) */
    public float tempThresh = 0.5f;

    public HotConduit(String name){
        super(name);
    }


    public class HotConduitBuild extends ConduitBuild{
        @Override
        public void updateTile(){
            Liquid liquid = liquids.current();
            super.updateTile();

            if(liquids.currentAmount() > 0.01f && liquid.temperature > tempThresh && melts){
                damageContinuous(liquid.temperature / 100f);
                // Maybe be more original?
                if(Mathf.chanceDelta(0.01f)){
                    Fx.steam.at(x, y);
                }
            }
        }
    }
}
