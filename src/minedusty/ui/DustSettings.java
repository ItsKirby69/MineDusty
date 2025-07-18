package minedusty.ui;

import static mindustry.Vars.*;

import arc.Core;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;
import minedusty.planets.DustPlanets;

public class DustSettings {
    

    public static void load() {
        ui.settings.addCategory("@setting.dusty-settings-title.title", "minedusty-settings-icon", t -> {
            t.checkPref("@settings.dusty-falling-leaves-enabled", true);
            t.checkPref("@settings.dusty-fade-enabled", true);
            t.sliderPref("@settings.dusty-fade-opacity", 10, 0, 100, 5, s -> s + "%");
            t.sliderPref("@settings.dusty-sfx-volume", 50, 0, 100, 1, s -> s + "%");

            t.checkPref("@settings.dusty-disable-popup", false);

            t.row();
            // TODO fix these options disapearing when reseting to default
            t.button("@settings.dusty-cleartree", 
            Icon.trash,
            Styles.flatt, 
            Vars.iconMed,
            () -> Vars.ui.showConfirm("@settings.dusty-cleartree-confirm", () -> DustData.resetTree(DustPlanets.theia.techTree)));
            
            t.button(Icon.github, () ->{
                String url = "https://github.com/ItsKirby69/MineDusty";
                if(!Core.app.openURI(url)){
                    ui.showInfoFade("@linkfail");  
                    Core.app.setClipboardText(url);
                }
            }).size(50f).tooltip("@@settings.dusty-github").right().bottom();
        });
    }
}
