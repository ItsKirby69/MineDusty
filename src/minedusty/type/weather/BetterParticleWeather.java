package minedusty.type.weather;

import arc.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.*;
import mindustry.gen.*;
import mindustry.type.*;

import static mindustry.Vars.*;

/** A particle weather implmenting swaying particles and particles rotating to the direction of movement (WIP) */
public class BetterParticleWeather extends Weather{
    private static final float boundMax = 10000 * 8f;

    public String particleRegion = "circle-shadow";
    public Color color = Color.white.cpy();
    public TextureRegion region;
    public float yspeed = -2f, xspeed = 0.25f, padding = 16f, sizeMin = 2.4f, sizeMax = 12f, density = 1200f, minAlpha = 1f, maxAlpha = 1f, force = 0, noiseScale = 2000f, baseSpeed = 6.1f;
    public float sinSclMin = 30f, sinSclMax = 80f, sinMagMin = 1f, sinMagMax = 7f;
    public float swaySclMin = 40f, swaySclMax = 60f, swayMagMin = 8f, swayMagMax = 16f;

    public Color noiseColor = color;
    public boolean drawNoise = false, drawParticles = true, useWindVector = false, faceMoveDirection = false, swayParticles = false; // FaceMoveDir not working idk why
    public int noiseLayers = 1;
    public float noiseLayerSpeedM = 1.1f, noiseLayerAlphaM = 0.8f, noiseLayerSclM = 0.99f, noiseLayerColorM = 1f;
    public String noisePath = "noiseAlpha";
    public @Nullable Texture noise;

    public BetterParticleWeather(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();

        region = Core.atlas.find(particleRegion);

        //load noise texture
        if(drawNoise && Core.assets != null){
            Core.assets.load("sprites/" + noisePath + ".png", Texture.class);
        }
    }

    @Override
    public void update(WeatherState state){
        float speed = force * state.intensity * Time.delta;
        if(speed > 0.001f){
            float windx = state.windVector.x * speed, windy = state.windVector.y * speed;

            for(Unit unit : Groups.unit){
                unit.impulse(windx, windy);
            }
        }
    }

    @Override
    public void drawOver(WeatherState state){

        float windx, windy;
        if(useWindVector){
            float speed = baseSpeed * state.intensity;
            windx = state.windVector.x * speed;
            windy = state.windVector.y * speed;
        }else{
            windx = this.xspeed;
            windy = this.yspeed;
        }

        if(drawNoise){
            if(noise == null){
                noise = Core.assets.get("sprites/" + noisePath + ".png", Texture.class);
                noise.setWrap(TextureWrap.repeat);
                noise.setFilter(TextureFilter.linear);
            }

            float sspeed = 1f, sscl = 1f, salpha = 1f, offset = 0f;
            Color col = Tmp.c1.set(noiseColor);
            for(int i = 0; i < noiseLayers; i++){
                drawNoise(noise, noiseColor, noiseScale * sscl, state.opacity * salpha * opacityMultiplier, sspeed * (useWindVector ? 1f : baseSpeed), state.intensity, windx, windy, offset);
                sspeed *= noiseLayerSpeedM;
                salpha *= noiseLayerAlphaM;
                sscl *= noiseLayerSclM;
                offset += 0.29f;
                col.mul(noiseLayerColorM);
            }
        }

        if(drawParticles){
            drawBarticles(region, color, sizeMin, sizeMax, density, state.intensity, state.opacity, windx, windy, minAlpha, maxAlpha, sinSclMin, sinSclMax, sinMagMin, sinMagMax, swaySclMin, swaySclMax, swayMagMin, swayMagMax, faceMoveDirection, swayParticles);
        }
    }

    public void drawBarticles(TextureRegion region, Color color,
                              float sizeMin, float sizeMax,
                              float density, float intensity, float opacity,
                              float windx, float windy,
                              float minAlpha, float maxAlpha,
                              float sinSclMin, float sinSclMax, float sinMagMin, float sinMagMax,
                              float swaySclMin, float swaySclMax, float swayMagMin, float swayMagMax,
                              boolean faceMoveDirection, boolean swayParticles){
        rand.setSeed(0);
        Tmp.r1.setCentered(Core.camera.position.x, Core.camera.position.y, Core.graphics.getWidth() / renderer.minScale(), Core.graphics.getHeight() / renderer.minScale());
        Tmp.r1.grow(sizeMax * 1.5f);
        Core.camera.bounds(Tmp.r2);
        int total = (int)(Tmp.r1.area() / density * intensity);
        Draw.color(color, opacity);

        for(int i = 0; i < total; i++){
            float scl = rand.random(0.5f, 1f);
            float scl2 = rand.random(0.5f, 1f);
            float size = rand.random(sizeMin, sizeMax);
            float x = (rand.random(0f, boundMax) + Time.time * windx * scl2);
            float y = (rand.random(0f, boundMax) + Time.time * windy * scl);
            float alpha = rand.random(minAlpha, maxAlpha);
            float rotation = 0f;
            if(faceMoveDirection){
                rotation = Mathf.atan2(windx, windy);
            }else if(swayParticles){
                rotation = Mathf.sin(Time.time, rand.random(swaySclMin, swaySclMax), rand.random(swayMagMin, swayMagMax));
            }else{
                rotation = 0f;
            }
            x += Mathf.sin(y, rand.random(sinSclMin, sinSclMax), rand.random(sinMagMin, sinMagMax));

            x -= Tmp.r1.x;
            y -= Tmp.r1.y;
            x = Mathf.mod(x, Tmp.r1.width);
            y = Mathf.mod(y, Tmp.r1.height);
            x += Tmp.r1.x;
            y += Tmp.r1.y;

            if(Tmp.r3.setCentered(x, y, size).overlaps(Tmp.r2)){
                Draw.alpha(alpha * opacity);
                Draw.rect(region, x, y, size, size, rotation);
            }
        }

        Draw.reset();
    }
}
