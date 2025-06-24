package minedusty.content.weathers;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.content.Liquids;
import mindustry.gen.WeatherState;
import mindustry.type.Liquid;
import mindustry.type.weather.*;

public class StormWeather extends ParticleWeather{

public float yspeed = 5f, xspeed = 1.5f, padding = 16f, density = 1200f, stroke = 0.75f, sizeMin = 8f, sizeMax = 40f, splashTimeScale = 22f;
    public Liquid liquid = Liquids.water;
    public TextureRegion[] splashes = new TextureRegion[12];
    public Color color = Color.valueOf("7a95eaff");

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
        drawRain(sizeMin, sizeMax, xspeed, yspeed, density, state.intensity, stroke, color);
    }

    @Override
    public void drawUnder(WeatherState state){
        drawSplashes(splashes, sizeMax, density, state.intensity, state.opacity, splashTimeScale, stroke, color, liquid);
    }
}
