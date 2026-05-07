package minedusty.ui;

import static arc.Core.settings;

import arc.Core;
import arc.input.KeyCode;
import arc.scene.actions.Actions;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.TextButton;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.util.*;
import mindustry.ui.dialogs.*;

// Gratefully taken from Omaloon's dialogue system.

public class DustyPopup extends BaseDialog{
    public static final float Best_Width = 600f;
    public static final float height_buttons = 120f;
    public static final float width_padding = 30f;

    public DustyPopup() {
        super(Core.app.isMobile() ? "@popup.dusty-message-mobile.title" : "@popup.dusty-message.title", Core.scene.getStyle(DialogStyle.class));
        
        float popupWidth = Math.min(Best_Width, Core.graphics.getWidth() / Scl.scl(1) - width_padding);
        float popupHeight = Core.graphics.getHeight() / Scl.scl(1f) - height_buttons - Scl.scl(40f);

        Table inner = new Table();
         // Adds the dog
        inner.image(Core.atlas.find("minedusty-dog")).size(Math.min(330f, popupWidth - 20f)).row();
        inner.add("@popup.dusty-message")
        .wrap()
        .width(popupWidth)
        .pad(7f)
        .get().setAlignment(Align.center, Align.center);

        ScrollPane pane = new ScrollPane(inner);
        pane.setScrollingDisabled(true, false);
        pane.setFadeScrollBars(false);
        cont.add(pane).maxHeight(popupHeight).width(popupWidth).row();

        // Two buttons. Once for closing, one for closing + disable future popups
        buttons.defaults().size(150f, 50f);
        setFillParent(false);
        
        keyDown(KeyCode.escape, this::hide);
        TextButton button_ok = buttons.button("@popup.popup-ok-button", this::hide).get();
        TextButton button_off = buttons.button("@popup.popup-disable-button", () -> {
            Core.settings.put("dusty-disable-popup", true);
            hide();
        }).get();
        
        // button_ok.setDisabled(() -> button_ok.color.a < 1);
        button_ok.actions(Actions.alpha(0), Actions.fadeIn(1.5f));
        button_off.actions(Actions.alpha(0), Actions.fadeIn(1.5f));

        button_ok.getStyle().disabledFontColor = button_ok.getStyle().fontColor;
        button_ok.getStyle().disabled = button_ok.getStyle().up;
        button_off.getStyle().disabledFontColor = button_off.getStyle().fontColor;
        button_off.getStyle().disabled = button_off.getStyle().up;
        
        // Timer to close popup
        int popDuration = 25;
        final int[] remaining = {popDuration};

        Core.app.post(() -> {
            button_ok.setText("Alrighty\n[lightgray](" + remaining[0] + ")");

            Timer.schedule(() -> {
                remaining[0]--;
                if(remaining[0] > 0){
                    button_ok.setText("Alrighty\n[lightgray](" + remaining[0] + ")");
                } else  {
                    hide();
                }
            }, 1f, 1f, popDuration - 1);
        });
    }
    
    public static void check(){
        if(!(settings.getBool("dusty-disable-popup"))){
            new DustyPopup().show();
        }
    }
}
