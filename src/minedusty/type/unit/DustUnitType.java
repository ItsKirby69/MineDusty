package minedusty.type.unit;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import minedusty.graphics.DustPalette;

public class DustUnitType extends UnitType{
    
    public DustUnitType(String name) {
        super(name);
        outlineColor = DustPalette.turretOutline;
        mechLegColor = DustPalette.turretOutline;
    }

    @Override
    public Color cellColor(Unit unit){
        float f = Mathf.clamp(unit.healthf());
        return Tmp.c1.set(Color.black).lerp(Color.white, f + Mathf.absin(Time.time, Math.max(f * 5f, 1f), 1f - f));
    }
}
