package minedusty.type.weather;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import mindustry.gen.WeatherState;
import mindustry.graphics.Layer;
import mindustry.type.weather.ParticleWeather;

/** Particle weather that has a color overlay */
public class SnowStormWeather extends ParticleWeather{
    /** Higher value, more opaque darkness over sector */
    public float darkness = 0.3f;
    /** Color of overcasing darkness */
    public Color darkColor = Color.valueOf("#0f121f");

    public SnowStormWeather(String name) {
        super(name);
    }

    @Override
    public void drawOver(WeatherState state){
        super.drawOver(state);

        Draw.z(Layer.weather - 1f);
        Draw.color(darkColor);
        Draw.alpha(darkness * state.opacity);
        Fill.rect(Core.camera.position.x, Core.camera.position.y, Core.camera.width, Core.camera.height);
        Draw.reset();
    }
    
}
