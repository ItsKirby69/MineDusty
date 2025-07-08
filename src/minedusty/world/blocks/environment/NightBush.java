package minedusty.world.blocks.environment;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.world.*;
import mindustry.world.blocks.environment.SeaBush;

import static mindustry.Vars.*;

/** A Bush which glows at night. I mean what do you expect. */
public class NightBush extends SeaBush{
    public float glowStr = 0.3f;
    /** Range where fade starts. Lower has sharper fade */
    public float fadeRng = 0.25f;
    public float maxLightRadius = 30f;

    public NightBush(String name){
        super(name);

        lightRadius = maxLightRadius;
        lightColor = Color.valueOf("89f0ff");
        emitLight = true;
    }

    public float currFade(){
        float light = 1f;
        if(state.rules.lighting){
            light = 1f - state.rules.ambientLight.a;
        }

        return Mathf.clamp((1f - light) / fadeRng);
    }

    @Override
    public void drawBase(Tile tile) {
        super.drawBase(tile); // bush base

        float fade = currFade();
        float x = tile.drawx();
        float y = tile.drawy();

        // Draw base bulb housing
        Draw.alpha(1f);
        Draw.rect(name + "-base", x, y);

        // Draw glowing bulb only when faded in
        if(fade > 0.01f){
            Draw.alpha(fade);
            Draw.rect(name + "-light", x, y);
        }

        Draw.reset();
    }

    public class NightBushBuild extends Building {

        @Override
        public void updateTile() {
            float light = 1f;
            if (Vars.state.rules.lighting) {
                light = 1f - Vars.state.rules.ambientLight.a;
            }

            float fade = Mathf.clamp((1f - light) / fadeRng);

            // Dynamically update light properties
            lightRadius = maxLightRadius * fade;
        }
    }
}
