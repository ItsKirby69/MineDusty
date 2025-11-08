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
import mindustry.world.blocks.production.GenericCrafter.GenericCrafterBuild;
import mindustry.world.draw.DrawBlock;

/** A DrawHeatRegion which shows the heat region based on the crafter's efficiencyScale() method.. */
public class DrawHeatCrafterEff extends DrawBlock{
    public Color color = new Color(1f, 0.22f, 0.22f, 0.8f);
    public float pulse = 0.3f, pulseScl = 10f;
    public float layer = Layer.blockAdditive;
    /** Minimum efficiency to show effect */
    public float minEfficiency = 0.5f;

    public TextureRegion heat;
    public String suffix = "-heat";
    
    public DrawHeatCrafterEff(float layer){
        this.layer = layer;
    }

    public DrawHeatCrafterEff(String suffix){
        this.suffix = suffix;
    }

    public DrawHeatCrafterEff(){

    }

    @Override
    public void draw(Building build){
        Draw.z(Layer.blockAdditive);
        if(build instanceof GenericCrafterBuild pf && pf.efficiencyScale() > minEfficiency){
            float z = Draw.z();
            if(layer > 0) Draw.z(layer);
            Draw.blend(Blending.additive);
            Draw.color(color, Mathf.clamp(pf.efficiencyScale() * (color.a * (1f - pulse + Mathf.absin(pulseScl, pulse)))));
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
