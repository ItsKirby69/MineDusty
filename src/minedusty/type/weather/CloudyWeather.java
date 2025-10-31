package minedusty.type.weather;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.*;
import mindustry.Vars;
import mindustry.gen.*;
import mindustry.type.*;

public class CloudyWeather extends Weather {
    public String particleRegion = "circle-shadow";
    public Color baseColor = Color.white.cpy(), blendColor = Color.white.cpy();
    public Color shadowColor = Color.black.cpy();
    public TextureRegion region;
    public float yspeed = -2f, xspeed = 0.4f;
    public float sizeMin = 60f, sizeMax = 140f, density = 20f;
    public float minAlpha = 0.2f, maxAlpha = 0.3f, shadowAlpha = 0.15f;
    public int clumpCopies = 3;
    public float clumpOffset = 30f, force = 0, shadowOffset = 50f, baseSpeed = 6f;
    public boolean useWindVector = false;

    public CloudyWeather(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();
        region = Core.atlas.find(particleRegion);
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
        int numParticles = (int)(density * state.intensity);

        for(int i = 0; i < numParticles; i++){
            float x = Mathf.random(0f, Vars.world.unitWidth());
            float y = Mathf.random(0f, Vars.world.unitHeight());

            float size = Mathf.random(sizeMin, sizeMax);
            float alpha = Mathf.random(minAlpha, maxAlpha);
            float rot = Mathf.random(360f);

            for(int j = 0; j < clumpCopies; j++){
                float ox = Mathf.range(clumpOffset);
                float oy = Mathf.range(clumpOffset);
                Draw.color(shadowColor, shadowAlpha);
                Draw.rect(region, x + ox, y + oy, size, size, rot);

                ox += shadowOffset;
                oy += shadowOffset;
                Draw.color(blendColor, alpha);
                Draw.rect(region, x + ox, y + oy, size, size, rot);
            }
        }

        Draw.color();
    }
}
