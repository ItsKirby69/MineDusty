package minedusty.type.unit;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.gen.Unit;
import mindustry.type.*;
import mindustry.world.meta.Env;
import minedusty.graphics.DustPalette;

public class TemporUnitType extends UnitType{
    

    public TemporUnitType(String name){
        super(name);

        outlineColor = DustPalette.temporDarkOutline;
        mechLegColor = DustPalette.temporDarkOutline;
        envDisabled = Env.space;
    
        healColor = DustPalette.temporHeal;

        useUnitCap = false;
    }

    @Override
    public Color cellColor(Unit unit){
        float f = Mathf.clamp(unit.healthf());
        return Tmp.c1.set(Color.clear).lerp(Color.white, f + Mathf.absin(Time.time, Math.max(f * 5f, 1f), 1f - f));
    }
}
