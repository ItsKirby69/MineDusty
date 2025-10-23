package minedusty.type;

import arc.Core;
import mindustry.gen.Icon;
import mindustry.type.*;

public class DustSectorPreset extends SectorPreset{

    public DustSectorPreset(String name, Planet planet, int sector) {
        super(name, planet, sector);
    }

    // Note to self: The campaign does not update when adding sector icons. Resetting campaign and tree seems to make them appear
    @Override
    public void loadIcon(){
        if(Icon.terrain != null){
            uiIcon = fullIcon = Core.atlas.find(name);
        }
    }

}
