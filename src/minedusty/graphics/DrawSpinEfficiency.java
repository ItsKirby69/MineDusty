package minedusty.graphics;

import arc.Core;
import arc.graphics.g2d.*;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.world.Block;
import mindustry.world.blocks.power.PowerGenerator.GeneratorBuild;
import mindustry.world.draw.DrawBlock;

/** Rotates a region based on productionEfficiency of generator. Basically a DrawRegion stripped down with some stuff added ngl. */
public class DrawSpinEfficiency extends DrawBlock{
    public String suffix = "-rotator";
    /** Speed when efficiency is at 100% */
    public float baseSpeed = 1f;
    public float minSpeed = 0.5f;
    public boolean spinSprite = true;
    public boolean drawPlan = true;
    public boolean buildingRotate = false;
    public float rotateSpeed, x, y, rotation;

    public TextureRegion region;

    public float layer = -1;

    public DrawSpinEfficiency(String suffix){
        this.suffix = suffix;
    }

    public DrawSpinEfficiency(){

    }

    @Override
    public void draw(Building build){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        if(build instanceof GeneratorBuild pf && pf.productionEfficiency > 0){
            float spin = (Math.max(minSpeed, pf.productionEfficiency * baseSpeed));
            Drawf.spinSprite(region, build.x + x, build.y + y, build.totalProgress() * spin + rotation + (buildingRotate ? build.rotdeg() : 0));
        }
        Draw.z(z); // Not actualy sure if this does anything
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list){
        if(!drawPlan) return;
        Drawf.spinSprite(region, plan.drawx() + x, plan.drawy() + y, (buildingRotate ? plan.rotation * 90f : 0 + rotation));
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{region};
    }

    @Override
    public void load(Block block){
        region = Core.atlas.find(block.name + suffix);
    }
}
