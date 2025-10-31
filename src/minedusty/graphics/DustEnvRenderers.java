package minedusty.graphics;

import static mindustry.Vars.*;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.Texture.TextureFilter;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.Rand;
import arc.util.Time;
import mindustry.graphics.Layer;
import minedusty.world.meta.DustEnvs;

public class DustEnvRenderers {
    public static void init(){
        Rand rand = new Rand();

        Core.assets.load("sprites/rays.png", Texture.class).loaded = t -> {
            t.setFilter(TextureFilter.linear);
        };

        renderer.addEnvRenderer(DustEnvs.lush, () -> {
            Draw.z(Layer.light + 2);

            int rays = 50;
            float timeScale = 2000f;
            rand.setSeed(0);

            Draw.blend(Blending.additive);

            float t = Time.time / timeScale;
            Texture tex = Core.assets.get("sprites/rays.png", Texture.class);

            for(int i = 0; i < rays; i++){
                float offset = rand.random(0f, 1f);
                float time = t + offset;

                int pos = (int)time;
                float life = time % 1f;
                float opacity = rand.random(0.2f, 0.7f) * Mathf.slope(life) * 0.7f;
                float x = (rand.random(0f, world.unitWidth()) + (pos % 100)*753) % world.unitWidth();
                float y = (rand.random(0f, world.unitHeight()) + (pos % 120)*453) % world.unitHeight();
                float rot = rand.range(7f);
                float sizeScale = 0.4f + rand.range(0.3f);

                float topDst = (Core.camera.position.y + Core.camera.height/2f) - (y + tex.height/2f + tex.height*1.9f*sizeScale/2f);
                float invDst = topDst/1000f;
                opacity = Math.min(opacity, -invDst);

                if(opacity > 0.01){
                    Color sunColor = Color.valueOf("#fff7b2");
                    Color moonColor = Color.valueOf("#a6cffe");
                    
                    float light = 1f;
                    if(state.rules.lighting){
                        light = 1f - state.rules.ambientLight.a;
                    }

                    Color rayColor = moonColor.cpy().lerp(sunColor, light);
    
                    Draw.color(rayColor, opacity);
                    Draw.rect(Draw.wrap(tex), x, y + tex.height/2f, tex.width*2*sizeScale, tex.height*2*sizeScale, rot);
                    Draw.color();
                }
            }
            
            Draw.blend();
        });
    }
}
