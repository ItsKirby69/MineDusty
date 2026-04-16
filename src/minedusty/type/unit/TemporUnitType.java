package minedusty.type.unit;

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
}
