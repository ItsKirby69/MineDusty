package minedusty.ui;

import mindustry.content.TechTree.*;

// Completely from Omaloon
public class DustData {
    public static void resetTree(TechNode tech){
        tech.reset();
        tech.content.clearUnlock();
        tech.children.each(DustData::resetTree);
    }
}
