package minedusty.ui;

import static arc.Core.settings;

import arc.Core;
import arc.scene.actions.Actions;
import arc.scene.ui.TextButton;
import arc.util.*;
import mindustry.ui.dialogs.*;

// Gratefully taken from Omaloon's dialogue system.

public class DustyPopup extends BaseDialog{

    public DustyPopup() {
        super("@popup.dusty-message.title", Core.scene.getStyle(DialogStyle.class));

        // Adds the dog
        cont.image(Core.atlas.find("minedusty-dog")).size(330f).row();
        
        cont.add("@popup.dusty-message")
        .wrap()
        .width(600f)
        .pad(7f)
        .get().setAlignment(Align.center, Align.center);

        buttons.defaults().size(150f, 50f);
        setFillParent(false);
        
        TextButton button_ok = buttons.button("Alrighty", this::hide).get();
        
        button_ok.setDisabled(() -> button_ok.color.a < 1);
        button_ok.actions(Actions.alpha(0), Actions.fadeIn(2f));

        button_ok.getStyle().disabledFontColor = button_ok.getStyle().fontColor;
        button_ok.getStyle().disabled = button_ok.getStyle().up;

        // Timer to close popup
        int popDuration = 10;
        final int[] remaining = {popDuration};

        Core.app.post(() -> {
            button_ok.setText("Alrighty (" + remaining[0] + ")");

            Timer.schedule(() -> {
                remaining[0]--;
                if(remaining[0] > 0){
                    button_ok.setText("Alrighty (" + remaining[0] + ")");
                } else  {
                    hide();
                }
            }, 1f, 1f, popDuration - 1);
        });
    }
    
    public static void check(){
        if(!(settings.getBool("@setting.dusty-disable-popup"))){
            new DustyPopup().show();
        }
    }
}
