package minedusty.graphics;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;
import mindustry.world.meta.Attribute;

/** Draws heat based on solar power */
public class DrawSolarHeat extends DrawBlock{
    public Color color = new Color(1f, 0.22f, 0.22f, 0.8f);
    public float pulse = 0.35f, pulseScl = 8f;
    public float layer = Layer.blockAdditive;

    /** Minimum solar energy of sector to show effect */
    public float minSolar = 0.5f;

    public TextureRegion heat;
    public String suffix = "-heat";
    
    public DrawSolarHeat(float minSol){
        this.minSolar = minSol;
    }

    public DrawSolarHeat(String suffix){
        this.suffix = suffix;
    }

    public DrawSolarHeat(){

    }

    @Override
    public void draw(Building build){
        float solarLevel = Mathf.maxZero(Attribute.light.env() +
                (Vars.state.rules.lighting ?
                    1f - Vars.state.rules.ambientLight.a :
                    1f));

        Draw.z(Layer.blockAdditive);
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        Draw.blend(Blending.additive);
        Draw.color(color, Mathf.clamp(solarLevel * (color.a * (1f - pulse + Mathf.absin(pulseScl, pulse)))));
        Draw.rect(heat, build.x, build.y);
        Draw.blend();
        Draw.color();
        Draw.z(z);
    }

    @Override
    public void load(Block block){
        heat = Core.atlas.find(block.name + suffix);
    }
}
