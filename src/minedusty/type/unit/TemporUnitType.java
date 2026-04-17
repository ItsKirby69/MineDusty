package minedusty.type.unit;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.gen.Unit;
import mindustry.type.*;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.world.meta.Env;
import minedusty.content.DustItems;
import minedusty.graphics.DustPalette;

public class TemporUnitType extends UnitType{
    

    public TemporUnitType(String name){
        super(name);

        outlineColor = DustPalette.temporDarkOutline;
        mechLegColor = DustPalette.temporOutline;
        envDisabled = Env.space;
        ammoType = new ItemAmmoType(DustItems.chlorophyte);
    
        healColor = DustPalette.temporHeal;
    }

    @Override
    public Color cellColor(Unit unit){
        float f = Mathf.clamp(unit.healthf());
        return Tmp.c1.set(Color.clear).lerp(Color.white, f + Mathf.absin(Time.time, Math.max(f * 5f, 1f), 1f - f));
    }
}
