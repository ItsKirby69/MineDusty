package minedusty.ui;

import static arc.Core.*;
import static mindustry.Vars.*;

public class DustSettings {

    public static void load() {
        ui.settings.addCategory(bundle.get("setting.dusty-title"), "minedusty-settings-icon", t -> {
            t.checkPref("dusty-fade-enabled", true);
            t.sliderPref("dusty-fade-opacity", 10, 0, 100, 10, s -> s + " units");
        });
    }
    
}
