package minedusty.world.ui;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.scene.*;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.scene.ui.layout.Scl;
import arc.struct.Seq;
import arc.util.*;
import mindustry.core.GameState;
import mindustry.game.EventType.StateChangeEvent;
import mindustry.graphics.Drawf;
import minedusty.MineDustyMod;

public class DustSplashFrag {
    public String text;
    
    public float jiggle = 0f;
    public float splashX = 0f;
    public float splashY = 0f;

    public void build(Group parent){
        Seq<String> subtitles = MineDustyMod.subtitles;
        Events.on(StateChangeEvent.class, e -> {
			if(e.to == GameState.State.menu){
                text = subtitles.random();
            }
		});
        text = subtitles.random();

        parent.fill((x,y,w,h) -> {
            jiggle = Math.max(0f, jiggle - 0.15f * Time.delta);

            TextureRegion logo = Core.atlas.find("logo");
            float screenW = Core.graphics.getWidth();
            float screenH = Core.graphics.getHeight() - Core.scene.marginTop;
            float logoScl = Scl.scl(1) * logo.scale;

            float logow = Math.min(logo.width * logoScl, screenW - Scl.scl(20));
            float logoh = logow * (float)logo.height / logo.width;
            splashX = (screenW / 2f) + logow / 2.5f;
            splashY = (screenH - logoh) + logoh / 3.5f;
            float bouncy = Core.settings.getInt("dusty-splash-bounciness");

            float rot = 17f + Mathf.sin(Time.time / 15f) * 2f * (bouncy / 10f);
            float scale = Scl.scl() * 5.5f + Mathf.sin(Time.time / 12f) * 0.2f * (bouncy / 10f) + Mathf.sin(jiggle * 2.5f) * (jiggle / 3f);

            Drawf.text(text, splashX, splashY, rot, Color.yellow, scale, Align.center);
        });

        parent.addListener(new InputListener(){
            // Easy radius check
            @Override
            public boolean touchDown(InputEvent event, float ex, float ey, int pointer, KeyCode button){
                float dx = ex - splashX / Scl.scl(1f);
                float dy = ey - splashY / Scl.scl(1f);
                if(dx * dx + dy * dy < Scl.scl(80f) * Scl.scl(80f)){
                    jiggle = Math.min(3f, jiggle + 3f);
                    return true;
                }
                return false;
            }
        });
    }
}
