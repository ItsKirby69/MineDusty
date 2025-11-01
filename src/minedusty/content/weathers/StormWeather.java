package minedusty.content.weathers;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.Texture.TextureFilter;
import arc.graphics.Texture.TextureWrap;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Liquids;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.gen.WeatherState;
import mindustry.graphics.Layer;
import mindustry.type.Liquid;
import mindustry.type.weather.*;

public class StormWeather extends ParticleWeather{

public float yspeed = 5f, xspeed = 1.5f, padding = 16f, density = 1200f, stroke = 0.75f, sizeMin = 8f, sizeMax = 40f, splashTimeScale = 22f;
    public Liquid liquid = Liquids.water;
    public TextureRegion[] splashes = new TextureRegion[12];
    public Color color = Color.valueOf("#92a6e7");

    /** Higher value, more opaque darkness over sector */
    public float darkness = 0.3f;
    /** Color of overcasing darkness */
    public Color darkColor = Color.valueOf("#0f121f");

    public StormWeather(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();

        for(int i = 0; i < splashes.length; i++){
            splashes[i] = Core.atlas.find("splash-" + i);
        }
    }

    @Override
    public void drawOver(WeatherState state){
        float speed = baseSpeed * state.intensity;
        float windx = xspeed /(Math.abs(xspeed) + Math.abs(yspeed)) * speed;
        float windy = -yspeed /(Math.abs(xspeed) + Math.abs(yspeed)) * speed;

        if(drawNoise){
            if(noise == null){
                noise = Core.assets.get("sprites/" + noisePath + ".png", Texture.class);
                noise.setWrap(TextureWrap.repeat);
                noise.setFilter(TextureFilter.linear);
            }

            float sspeed = 1f, sscl = 1f, salpha = 1.3f, offset = 0f;
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
        
        drawRain(sizeMin, sizeMax, xspeed, yspeed, density, state.intensity, stroke, color);
        
        float fade = state.opacity;
        
        // Dark Overlay (mimiking cloudiness?)
        Draw.z(Layer.weather - 1f);
        Draw.color(darkColor);
        Draw.alpha(darkness * fade);
        Fill.rect(Core.camera.position.x, Core.camera.position.y, Core.camera.width, Core.camera.height);
        Draw.reset();
    }

    @Override
    public void drawUnder(WeatherState state){
        drawSplashes(splashes, sizeMax, density, state.intensity, state.opacity, splashTimeScale, stroke, color, liquid);
    }

    @Override
    public void update(WeatherState state){
        float speed = force * state.intensity * Time.delta;
        if(speed > 0.001f){
            float windx = xspeed /(Math.abs(xspeed) + Math.abs(yspeed)) * speed;
            float windy = -yspeed /(Math.abs(xspeed) + Math.abs(yspeed)) * speed;

            for(Unit unit : Groups.unit){
                unit.impulse(windx, windy);
            }
        }
    }

}
