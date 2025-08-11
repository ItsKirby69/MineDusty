package minedusty.ui;

import static mindustry.Vars.*;

import arc.Core;
import arc.scene.ui.CheckBox;
import arc.scene.ui.ImageButton;
import arc.scene.ui.Tooltip;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.*;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.*;
import minedusty.planets.DustPlanets;

import static mindustry.Vars.ui;

public class DustSettings {

    public static void load() {
        ui.settings.addCategory("@setting.dusty-settings-title.title", "minedusty-settings-icon", t -> {
            //TODO hover mouse transparent and categories.
            t.pref(new Title("@setting.dusty-visual-title"));
            
            t.checkPref("@setting.dusty-falling-leaves-enabled", true);
            t.checkPref("@setting.dusty-fade-enabled", true);
            t.checkPref("@setting.dusty-toggle-mouse-fade", false);
            t.sliderPref("@setting.dusty-fade-opacity", 10, 0, 100, 5, s -> s + "%");

            t.pref(new Title("@setting.dusty-misc-title"));

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

    public static class Title extends Setting {
        public Title(String text){
            super("");
            this.title = text;
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table){
            table.add(title).color(Pal.accent).padTop(25f).padRight(110f).padBottom(-5).left().pad(5);
            table.row();
            table.image().color(Pal.accent).height(3f).padRight(110f).padBottom(25f).left().fillX().padBottom(5f);
            table.row();
        }
    }
}
