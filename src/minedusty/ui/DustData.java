package minedusty.ui;

import mindustry.content.TechTree.*;

// Completely from Omaloon
public class DustData {
    public static void resetTree(TechNode node){
        // This should not work right now
        if(node.content.minfo.mod != null && node.content.minfo.mod.meta.name.equals("minedusty")){
            node.content.clearUnlock();
        }

        for(TechNode child : node.children){
            resetTree(child);
        }
        // tech.reset();
        // tech.content.clearUnlock();
        // tech.children.each(DustData::resetTree);
    }
}
