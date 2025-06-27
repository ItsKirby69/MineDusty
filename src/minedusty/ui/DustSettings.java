package minedusty.ui;

import static mindustry.Vars.*;

import arc.Core;
import mindustry.gen.Icon;

public class DustSettings {
    

    public static void load() {
        ui.settings.addCategory("@setting.dusty-settings-title.title", "minedusty-settings-icon", t -> {
            t.checkPref("dusty-falling-leaves-enabled", true);
            t.checkPref("dusty-fade-enabled", true);
            t.sliderPref("dusty-fade-opacity", 10, 0, 100, 5, s -> s + "%");
            t.sliderPref("dusty-sfx-volume", 50, 0, 100, 1, s -> s + "%");

            t.checkPref("dusty-disable-popup", false);
            t.table(inner -> {
                inner.right().bottom().button(Icon.github, () -> {
                    String url = "https://github.com/ItsKirby69/MineDusty";
                    if(!Core.app.openURI(url)){
                        ui.showInfoFade("@linkfail");  
                        Core.app.setClipboardText(url);
                    }
                })
                .size(50f)
                .tooltip("Open the Github Repo")
                .name("github");
            });
        });
    }
}
