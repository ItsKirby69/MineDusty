package minedusty.ui;

import static mindustry.Vars.*;

import arc.Core;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.*;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.*;
import minedusty.planets.DustPlanets;

import static mindustry.Vars.ui;

public class DustSettings {

    public static void load() {
        ui.settings.addCategory("@setting.dusty-settings-title.title", "minedusty-settings-icon", t -> {
            t.checkPref("@setting.dusty-falling-leaves-enabled", true);
            t.checkPref("@setting.dusty-fade-enabled", true);
            t.sliderPref("@setting.dusty-fade-opacity", 10, 0, 100, 5, s -> s + "%");
            t.sliderPref("@setting.dusty-sfx-volume", 50, 0, 100, 1, s -> s + "%");

            t.checkPref("@setting.dusty-disable-popup", false);

            t.pref(new TableSetting("github-linko", new Table(c -> {
                c.button("@setting.dusty-cleartree", Icon.trash, Styles.flatt, Vars.iconMed,() -> 
                    Vars.ui.showConfirm("@setting.dusty-cleartree-confirm", () -> DustData.resetTree(DustPlanets.theia.techTree)
                )).width(230f);

                c.button(Icon.github, new ImageButton.ImageButtonStyle(), () ->{
                    String url = "https://github.com/ItsKirby69/MineDusty";
                    if(!Core.app.openURI(url)){
                        ui.showInfoFade("@linkfail");  
                        Core.app.setClipboardText(url);
                    }
                }).size(50f).tooltip("@setting.dusty-github").right().bottom();    
            })));
            
        });
    }

    // Again, completely from Omaloon
    
    public static class TableSetting extends Setting {
        public Table t;

        public TableSetting(String name, Table table){
            super(name);
            this.t = table;
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table){
            table.add(t).fillX().row();
        }
    }
}
