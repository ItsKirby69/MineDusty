package minedusty.type.unit;

import mindustry.type.*;
import mindustry.world.meta.Env;
import minedusty.graphics.DustPalette;

public class DivineUnitType extends UnitType{
    

    public DivineUnitType(String name){
        super(name);

        outlineColor = DustPalette.divineDarkOutline;
        mechLegColor = DustPalette.divineOutline;
        envDisabled = Env.space;
    
        healColor = DustPalette.divineHeal;
        
        useUnitCap = false;
    }
}
