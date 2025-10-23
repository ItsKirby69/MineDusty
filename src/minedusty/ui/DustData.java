package minedusty.ui;

import static arc.Core.settings;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.TechTree.*;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Saves;
import mindustry.type.*;
import minedusty.planets.DustPlanets;

// from Aquarion (and Omaloon)
public class DustData {
    public static void resetTree(TechNode node){
        Vars.universe.clearLoadoutInfo();

        for(TechNode child : DustPlanets.theia.techTree.children){
            child.reset();
        }

        // This should not work right now now
        Vars.content.each(c -> {
            if(c instanceof UnlockableContent u && u.minfo != null && 
                u.minfo.mod != null && u.minfo.mod.name.equals("minedusty")){
                    u.clearUnlock();
                }
        });

        DustPlanets.theia.techTree.reset();

        settings.remove("unlocks");
    }

    public static void unlockTree(TechNode node){
        Vars.content.each(c -> {
            if(c instanceof UnlockableContent u && u.minfo != null && 
                u.minfo.mod != null && u.minfo.mod.name.equals("minedusty")){
                    u.unlock();
                }
        });

        settings.remove("unlocks");
    }

    public static void resetCampaign(Planet planet){
        Seq<Saves.SaveSlot> delSectors = new Seq<>();
        Vars.control.saves.getSaveSlots().each(s -> {
            if(s.getSector() == null) return;

            if(s.getSector().planet == planet){
                delSectors.add(s);
            }
        });
        delSectors.each(Saves.SaveSlot::delete);
    }
}
