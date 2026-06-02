package minedusty.ui;

import arc.Core;
import arc.scene.ui.ImageButton;
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
        ui.settings.addCategory("@settings.dusty-settings-title", "minedusty-settings-icon", t -> {
            t.pref(new Title("@settings.dusty-visual-title"));
            
            t.checkPref("dusty-falling-leaves-enabled", true);
            t.checkPref("dusty-fade-enabled", true);
            t.checkPref("dusty-toggle-mouse-fade", false);
            t.sliderPref("dusty-fade-opacity", 0, 0, 100, 5, s -> s + "%");
            t.sliderPref("dusty-godray-opacity", 70, 0, 100, 5, s -> s + "%");
            t.sliderPref("dusty-falling-density", 3, 1, 15, 1, s -> s + "");
            t.sliderPref("dusty-fade-dist-multi", 1, 1, 4, 1, s -> s + "");

            t.pref(new Title("@settings.dusty-misc-title"));

            t.sliderPref("dusty-splash-bounciness", 10, 1, 100, s -> s + "");
            t.sliderPref("dusty-sfx-volume", 50, 0, 100, 1, s -> s + "%");
            t.checkPref("dusty-disable-popup", false);

            t.pref(new Title("@settings.dusty-debug-title"));

            t.checkPref("dusty-block-debug", false);

            t.pref(new TableSetting("github-linko", new Table(c -> {
                c.button("@settings.dusty-cleartree", Icon.trash, Styles.flatt, Vars.iconMed,() -> 
                    Vars.ui.showConfirm("@settings.dusty-cleartree-confirm", () -> DustData.resetTree()
                )).width(230f);

                c.button("@settings.dusty-clearcampaign", Icon.trash, Styles.flatt, Vars.iconMed,() -> 
                    Vars.ui.showConfirm("@settings.dusty-clearcampaign-confirm", () -> DustData.resetCampaign(DustPlanets.theia)
                )).width(230f);

                c.button("@settings.dusty-unlocktree", Icon.trash, Styles.flatt, Vars.iconMed,() -> 
                    DustData.unlockTree(DustPlanets.theia.techTree)).width(230f);

                c.button(Icon.github, new ImageButton.ImageButtonStyle(), () ->{
                    String url = "https://github.com/ItsKirby69/MineDusty";
                    if(!Core.app.openURI(url)){
                        ui.showInfoFade("@linkfail");  
                        Core.app.setClipboardText(url);
                    }
                }).size(50f).tooltip("@settings.dusty-github").right().bottom();    
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
