package minedusty.world.blocks.production;

import arc.Core;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.*;
import minedusty.world.meta.DustStat;

/** A crafter that requires Solar energy (sector environment light levels) to function */
public class SolarCrafter extends GenericCrafter{
    /** Needed solar Power for 100% efficiency. Max solar power is 1f (to my observations) */
    public float solarRequirement = 1f;
    /** Similar to overheatScale in the HeatCrafter, excess solar with this value multiplied */
    public float oversolarScale = 1f;

    public SolarCrafter(String name) {
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("light", (SolarCrafterBuild entity) ->
            new Bar(() -> 
            Core.bundle.format("bar.solarpercent", (int)(entity.solarLevel * 100f), (int)(entity.efficiencyScale() * 100f)),
            () -> Pal.accent,
            () -> entity.solarLevel / solarRequirement));
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(DustStat.solarRequired, (int)(solarRequirement * 100f), StatUnit.percent);
    }

    public class SolarCrafterBuild extends GenericCrafterBuild{
        public float solarLevel;

        @Override
        public void updateTile(){
            solarLevel = Mathf.maxZero(Attribute.light.env() +
                (Vars.state.rules.lighting ?
                    1f - Vars.state.rules.ambientLight.a :
                    1f));

            super.updateTile();
        }

        @Override
        public float warmupTarget(){
            return Mathf.clamp(solarLevel / solarRequirement);
        }

        @Override
        public float efficiencyScale(){
            float over = Math.max(solarLevel - solarRequirement, 0f);
            return Mathf.clamp(solarLevel / solarRequirement) + over / solarRequirement * oversolarScale;
        }
    }
}
