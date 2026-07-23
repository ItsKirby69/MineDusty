package minedusty.world.blocks.production;

import arc.Core;
import arc.audio.Sound;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.meta.*;
import minedusty.world.meta.DustStat;

/** A crafter that requires Solar energy (sector environment light levels) to function */
public class SolarCrafter extends BetterGenericCrafter{
    /** Needed solar Power for 100% efficiency. Max solar power is 1f (to my observations) */
    public float solarRequirement = 1f;
    /** Similar to overheatScale in the HeatCrafter, excess solar with this value multiplied */
    public float oversolarScale = 1f;
    /** Minimum solar energy needed to run */
    public float minSolar = 0.25f;

    public Sound craftSound = Sounds.none;
    public float craftSoundVolume = 0.7f;

    public SolarCrafter(String name) {
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("light", (SolarCrafterBuild entity) ->
            new Bar(
                () -> {
                    String colorTag = entity.solarLevel >= minSolar ? "[accent]" : "[red]";
                    return Core.bundle.format("bar.solarpercent", colorTag, (int)(entity.solarLevel * 100f));
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

    public class SolarCrafterBuild extends BetterGenericCrafterBuild{
        public float solarLevel;

        @Override
        public void craft(){
            super.craft();
            craftSound.play(craftSoundVolume, Mathf.random(0.95f, 1.1f), 0f);
        }

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
            if(solarLevel < minSolar) return 0f;
            float over = Math.max(solarLevel - solarRequirement, 0f);
            return Mathf.clamp(solarLevel / solarRequirement) + over / solarRequirement * oversolarScale;
        }
    }
}
