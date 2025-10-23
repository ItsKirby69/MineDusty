package minedusty.graphics;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.blocks.power.PowerGenerator.GeneratorBuild;
import mindustry.world.draw.DrawBlock;

/** A DrawHeatRegion which shows the heat region based on productionEfficiency. */
public class DrawHeatEfficiency extends DrawBlock{
    public Color color = new Color(1f, 0.22f, 0.22f, 0.8f);
    public float pulse = 0.3f, pulseScl = 10f;
    public float layer = Layer.blockAdditive;

    public TextureRegion heat;
    public String suffix = "-heat";
    
    public DrawHeatEfficiency(float layer){
        this.layer = layer;
    }

    public DrawHeatEfficiency(String suffix){
        this.suffix = suffix;
    }

    public DrawHeatEfficiency(){

    }

    @Override
    public void draw(Building build){
        Draw.z(Layer.blockAdditive);
        if(build instanceof GeneratorBuild pf && pf.productionEfficiency > 0){

            float z = Draw.z();
            if(layer > 0) Draw.z(layer);
            Draw.blend(Blending.additive);
            Draw.color(color, Mathf.clamp(pf.productionEfficiency * (color.a * (1f - pulse + Mathf.absin(pulseScl, pulse)))));
            Draw.rect(heat, build.x, build.y);
            Draw.blend();
            Draw.color();
            Draw.z(z);
        }
    }

    @Override
    public void load(Block block){
        heat = Core.atlas.find(block.name + suffix);
    }
}
