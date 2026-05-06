package minedusty.type.weather;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import mindustry.gen.WeatherState;
import mindustry.graphics.Layer;

/** Particle weather that has a color overlay */
// TODO generalize this for heavy rain and future weathers
public class SnowStormWeather extends BetterParticleWeather{
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
