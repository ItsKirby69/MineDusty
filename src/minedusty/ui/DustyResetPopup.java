package minedusty.ui;

import static arc.Core.settings;

import arc.Core;
import arc.util.*;
import mindustry.Vars;
import mindustry.ui.dialogs.BaseDialog;
import minedusty.planets.DustPlanets;

public class DustyResetPopup extends BaseDialog{
    
    public DustyResetPopup(Runnable popup, int version){
        super("@popup.dusty-reset-campaign-save-title", Core.scene.getStyle(DialogStyle.class));
        cont.add(Core.bundle.format("popup.reset-save-message", Vars.mods.getMod("minedusty").meta.version))
        .width(Core.app.isMobile() ? 450f: 600f)
        .wrap()
        .pad(4f)
        .get()
        .setAlignment(Align.center, Align.center);
        buttons.defaults().size(200f, 50f).pad(2f);
        setFillParent(false);
        cont.row();
        buttons.button("@popup.reset-confirm", () -> {
            DustData.resetCampaign(DustPlanets.theia);
            DustData.resetTree();
            settings.put("@settings.dusty-campaign-version", version);
            hide();
            popup.run();
        });
        buttons.button("@popup.reset-later", () -> {
            hide();
            popup.run();
        });
    }

    public static void check(int save_version, Runnable popup){
        int storedVersion = settings.getInt("@settings.dusty-campaign-version", 0);
        if(storedVersion < save_version){
            boolean hasSectorSave = Vars.control.saves.getSaveSlots().contains(s -> 
                s.isSector() && s.getSector().planet == DustPlanets.theia);
            if(!hasSectorSave){
                settings.put("@settings.dusty-campaign-version", save_version);
                popup.run();
            }else{
                new DustyResetPopup(popup, save_version).show();
            }
        }else{
            popup.run();
        }
    }
}
