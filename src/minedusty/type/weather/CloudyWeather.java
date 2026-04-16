package minedusty.type.weather;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.*;
import mindustry.gen.*;
import mindustry.type.*;

import static mindustry.Vars.*;

public class CloudyWeather extends Weather {
    public int variants = 7;
    public TextureRegion[] regions;
    public Color baseColor = Color.white.cpy(), blendColor = Color.white.cpy();
    public Color shadowColor = Color.black.cpy();
    public float yspeed = -0.8f / 15f, xspeed = 1.2f / 15f;
    public float sizeMin = 240f, sizeMax = 260f, density = 240f * 800f;
    public float minAlpha = 0.6f, maxAlpha = 0.65f, shadowAlpha = 0.6f;
    public float degrees = 10;
    public float shadowOffset = 90f, baseSpeed = 6f;
    public boolean useWindVector = false;

    public static final float boundMax = 80000;

    public CloudyWeather(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();
        regions = new TextureRegion[variants];
        for(int i = 0; i < variants; i++){
            regions[i] = Core.atlas.find("minedusty-cloud" + (i + 1));
        }
    }

    @Override
    public void update(WeatherState state){
        // float speed = state.intensity * Time.delta;
        // if(speed > 0.001f){
        //     float windx = state.windVector.x * speed, windy = state.windVector.y * speed;

        //     for(Unit unit : Groups.unit){
        //         unit.impulse(windx, windy);
        //     }
        // }
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

        // Copy drawParticles essentially
        rand.setSeed(0);
        Tmp.r1.setCentered(Core.camera.position.x, Core.camera.position.y, Core.graphics.getWidth() / renderer.minScale(), Core.graphics.getHeight() / renderer.minScale());
        Tmp.r1.grow(sizeMax * 1.5f);
        Core.camera.bounds(Tmp.r2);
        int total = (int)(Tmp.r1.area() / density * state.intensity);
        Draw.color(baseColor, state.opacity);

        for(int i = 0; i < total; i++){
            float scl = rand.random(0.5f, 1f);
            float scl2 = rand.random(0.5f, 1f);
            float size = rand.random(sizeMin, sizeMax);
            float x = (rand.random(0f, boundMax) + Time.time * windx * scl2);
            float y = (rand.random(0f, boundMax) + Time.time * windy * scl);
            float alpha = rand.random(minAlpha, maxAlpha);
            float rot = rand.random(-degrees, degrees);

            TextureRegion region = regions[rand.random(regions.length - 1)];

            x -= Tmp.r1.x;
            y -= Tmp.r1.y;
            x = Mathf.mod(x, Tmp.r1.width);
            y = Mathf.mod(y, Tmp.r1.height);
            x += Tmp.r1.x;
            y += Tmp.r1.y;

            if(Tmp.r3.setCentered(x, y, size).overlaps(Tmp.r2)){
                Draw.alpha(alpha * state.opacity);
                Draw.rect(region, x, y, size, size, rot);
            }

            Draw.color(shadowColor, shadowAlpha * (alpha * state.opacity / maxAlpha));
            Draw.rect(region, x - shadowOffset, y - shadowOffset, size, size, rot);

            Draw.color(blendColor, alpha * state.opacity);
            Draw.rect(region, x, y, size, size, rot);

        }

        Draw.color();
    }
}
