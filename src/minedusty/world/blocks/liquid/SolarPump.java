package minedusty.world.blocks.liquid;

import arc.Core;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.Pump;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.StatUnit;
import minedusty.world.meta.DustStat;

public class SolarPump extends Pump{
    /** Needed solar Power for 100% efficiency. Max solar power is 1f (to my observations) */
    public float solarRequirement = 1f;
    /** Minimum solar energy needed to run */
    public float minSolar = 0.4f;

    public SolarPump(String name){
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("light", (SolarPumpBuild entity) ->
            new Bar(
                () -> {
                    String colorTag = entity.solarLevel >= minSolar ? "[accent]" : "[red]";
                    return Core.bundle.format("bar.solarpercent", colorTag, (int)(entity.solarLevel * 100f));//"Solar: " + colorTag + (int)(entity.solarLevel * 100f) + "[]%";
                },
                () -> Pal.accent,
                () -> (float)Mathf.clamp(entity.solarLevel / solarRequirement)
            ));
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(DustStat.solarRequired, (int)(minSolar * 100f), StatUnit.percent);
    }

    public class SolarPumpBuild extends PumpBuild{
        public float solarLevel;
        public float solarEff;

        @Override
        public void updateTile(){
            solarLevel = Mathf.maxZero(Attribute.light.env() +
                (Vars.state.rules.lighting ?
                    1f - Vars.state.rules.ambientLight.a :
                    1f));
            
            solarEff = Mathf.clamp(solarLevel/solarRequirement);
            if(solarLevel < minSolar) solarEff = 0f;

            if(solarEff > 0 && liquidDrop != null){
                float maxPump = Math.min(liquidCapacity - liquids.get(liquidDrop), amount * pumpAmount * edelta() * solarEff);
                liquids.add(liquidDrop, maxPump);

                //does nothing for most pumps, as those do not require items.
                if((consTimer += delta()) >= consumeTime){
                    consume();
                    consTimer %= 1f;
                }

                warmup = Mathf.approachDelta(warmup, maxPump > 0.001f ? 1f : 0f, warmupSpeed);
            }else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * Time.delta;

            if(liquidDrop != null){
                dumpLiquid(liquidDrop);
            }
        }

        @Override
        public float warmup(){
            return warmup;
        }

    }
}
