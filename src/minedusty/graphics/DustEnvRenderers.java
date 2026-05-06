package minedusty.graphics;

import static mindustry.Vars.*;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.Rand;
import arc.util.Time;
import mindustry.graphics.Layer;
import minedusty.world.meta.DustEnvs;

public class DustEnvRenderers {
    public static TextureRegion[] rayTexs;

    public static void init(){
        Rand rand = new Rand();

        // Core.assets.load("sprites/rays.png", Texture.class).loaded = t -> {
        //     t.setFilter(TextureFilter.linear);
        // };

        renderer.addEnvRenderer(DustEnvs.lush, () -> {
            if(rayTexs == null){
                rayTexs = new TextureRegion[]{
                    Core.atlas.find("minedusty-godrays1"),
                    Core.atlas.find("minedusty-godrays2"),
                    Core.atlas.find("minedusty-godrays3")
                };
            }
            boolean textureFound = false;
            for(TextureRegion r : rayTexs) if(r.found()){ 
                textureFound = true; 
                break;
            }
            if(!textureFound) return;

            Draw.z(Layer.light + 2);

            int rays = 60;
            float timeScale = 320f;
            rand.setSeed(0);

            Draw.blend(Blending.additive);

            float t = Time.time / timeScale;

            for(int i = 0; i < rays; i++){
                float timeSpeed = rand.random(0.8f, 1.2f);
                float offset = rand.random(0f, 1f);
                float time = (t * timeSpeed) + offset;

                int pos = (int)time;
                float life = Mathf.slope(time % 1f);

                float opacity = rand.random(0.3f, 0.7f) * life;
                float x = (rand.random(0f, world.unitWidth()) + (pos % 100)*753) % world.unitWidth();
                float y = (rand.random(0f, world.unitHeight()) + (pos % 120)*453) % world.unitHeight() - 200f;
                float rot = rand.range(7f);
                float sizeScale = 0.7f + rand.range(0.3f);

                // Randomize rays
                TextureRegion rayTex = rayTexs[rand.random(0, rayTexs.length - 1)];
                if(!rayTex.found()) continue;

                float topDst = (Core.camera.position.y + Core.camera.height/2f) - (y + rayTex.height/2f + rayTex.height*1.9f*sizeScale/2f);
                float invDst = topDst/1000f;
                opacity = Math.min(opacity, -invDst);

                if(opacity > 0.01){
                    Color sunColor = Color.valueOf("#fff7b1");
                    Color moonColor = Color.valueOf("#94c4ffff");
                    
                    float light = 1f;
                    if(state.rules.lighting){
                        light = 1f - state.rules.ambientLight.a;
                    }

                    Color rayColor = moonColor.cpy().lerp(sunColor, light);
    
                    Draw.color(rayColor, opacity);
                    Draw.rect(rayTex, x, y + rayTex.height/2f, rayTex.width*2*sizeScale, rayTex.height*2*sizeScale, rot);
                    Draw.color();
                }
            }
            
            Draw.blend();
        });
    }
}
