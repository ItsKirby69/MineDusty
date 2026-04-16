package minedusty.world.ui;

import java.lang.reflect.Field;

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
import mindustry.ui.Fonts;
import minedusty.MineDustyMod;

public class DustSplashFrag {
    // TEMPORARY REFLECTIOn
    public String text;
    public static Field pageVerticesField, idxField;

    public float jiggle = 0f;
    public float splashX = 0f;
    public float splashY = 0f;

    static{
        try{
            pageVerticesField = FontCache.class.getDeclaredField("pageVertices");
            idxField = FontCache.class.getDeclaredField("idx");
            pageVerticesField.setAccessible(true);
            idxField.setAccessible(true);
        }catch(NoSuchFieldException e){
            throw new RuntimeException("No field! ", e);
        }
    }

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
            float bouncy = Core.settings.getInt("@setting.dusty-splash-bounciness");

            float rot = 17f + Mathf.sin(Time.time / 15f) * 2f * (bouncy / 10f);
            float scale = Scl.scl() * 5.5f + Mathf.sin(Time.time / 12f) * 0.2f * (bouncy / 10f) + Mathf.sin(jiggle * 2.5f) * (jiggle / 3f);

            text(text, splashX, splashY, rot, Color.yellow, scale, Align.center);
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

    public static void rotateText(FontCache cache, float angleDeg, float x, float y){
        try{
            float[][] pageVertices = (float[][]) pageVerticesField.get(cache);
            int[] idx = (int[]) idxField.get(cache);
            float rad = angleDeg * Mathf.degreesToRadians;
            float cos = (float) Math.cos(rad);
            float sin = (float) Math.sin(rad);

            for(int i = 0; i < pageVertices.length; i++){
                float[] verts = pageVertices[i];
                if(verts == null) continue;
                for(int ii = 0; ii < idx[i]; ii += 6){
                    float dx = verts[ii] - x;
                    float dy = verts[ii + 1] - y;
                    verts[ii] = x + dx * cos - dy * sin;
                    verts[ii + 1] = y + dx * sin + dy * cos;
                }
            }
        } catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    public static void text(String text, float x, float y, float rotation, Color color, float scale, int align){
        Font font = Fonts.outline;
        boolean ints = font.usesIntegerPositions();
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.25f / Scl.scl(1f) * scale);
        font.setColor(color);
        font.getCache().clear();
        GlyphLayout layout = font.getCache().addText(text, x, y, 0f, align, false);
        if(rotation != 0){
            float verticalFraction = (align & Align.bottom) != 0 ? 1f : (align & Align.top) != 0 ? 0f: 0.5f;
            rotateText(font.getCache(), rotation, x, y - layout.height * verticalFraction);
        }
        if(color.a < 1f){
            font.getCache().setAlphas(color.a);
        }
        font.getCache().draw();
        font.getData().setScale(1f);
        font.setColor(Color.white);
        font.setUseIntegerPositions(ints);
    }
}

            // Mat mat = new Mat();
            // mat.rotate(rot);
            // Draw.trans(mat);
            // Drawf.text(text, splashX, splashY, Color.yellow, scale);
            // Draw.trans();
            // Draw.reset();
