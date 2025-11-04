package minedusty.ui;

import static arc.Core.settings;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.content.TechTree.*;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Saves;
import mindustry.type.*;
import minedusty.planets.DustPlanets;

import static mindustry.Vars.*;

// from Aquarion (and Omaloon)
public class DustData {
    public static void resetTree(){
        universe.clearLoadoutInfo();

        for(TechNode node : TechTree.all){
            if(node.content != null && node.content.minfo != null
                && node.content.minfo.mod != null && node.content.minfo.mod.name.equals("minedusty")){
                    node.reset();
                }
        }

        // This should not work right now now
        content.each(c -> {
            if(c instanceof UnlockableContent u && u.minfo != null && 
                u.minfo.mod != null && u.minfo.mod.name.equals("minedusty")){
                    u.clearUnlock();
                }
        });

        settings.remove("unlocks");
    }

    public static void unlockTree(TechNode node){
        content.each(c -> {
            if(c instanceof UnlockableContent u && u.minfo != null && 
                u.minfo.mod != null && u.minfo.mod.name.equals("minedusty")){
                    u.unlock();
                }
        });

        settings.remove("unlocks");
    }

    public static void resetCampaign(Planet planet){
        for(var sec : planet.sectors){
            sec.clearInfo();
            if(sec.save != null){
                sec.save.delete();
                sec.save = null;
            }
        }

        for(var slot : control.saves.getSaveSlots().copy()){
            if(slot.isSector() && slot.getSector() != null && slot.getSector().planet == planet){
                slot.delete();
            }
        }
    }
}
