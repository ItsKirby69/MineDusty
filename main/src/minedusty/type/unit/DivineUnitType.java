package minedusty.type.unit;

import mindustry.type.*;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.world.meta.Env;
import minedusty.content.DustItems;
import minedusty.graphics.DustPalette;

public class DivineUnitType extends UnitType{
    

    public DivineUnitType(String name){
        super(name);

        outlineColor = DustPalette.divineDarkOutline;
        mechLegColor = DustPalette.divineOutline;
        envDisabled = Env.space;
        ammoType = new ItemAmmoType(DustItems.divinityMatter);
    
        healColor = DustPalette.divineHeal;
        
    
    }
}
