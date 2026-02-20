package minedusty.graphics;

import static mindustry.Vars.tilesize;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.world.draw.DrawPower;
import minedusty.world.blocks.power.SaltBattery;
import minedusty.world.blocks.power.SaltBattery.SaltBatteryBuild;

public class DrawSaltPower extends DrawPower{
    @Override
    public void draw(Building build){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);

        // We are sure it's a salt battery right...?
        if(!(build instanceof SaltBatteryBuild b)) return;

        float itemRatio = (float)b.items.total() / b.block.itemCapacity;
        float liquidRatio = b.liquids.currentAmount() / b.block.liquidCapacity;
        float minliquidRatio = ((SaltBattery)b.block).minLiquidLevel / b.block.liquidCapacity;

        float flash = 0f;

        // Flash if 50% item capacity
        if(itemRatio > 0.5f){
            flash = Math.max(flash, Mathf.clamp((itemRatio - 0.5f) / 0.5f));
        }

        // Flash if near min liquid level
        if(liquidRatio < minliquidRatio){
            flash = Mathf.clamp((1f - liquidRatio) / (1f - minliquidRatio));
        }

        float pulse = flash > 0f ? Mathf.absin(Time.time * (1f + 2f * flash), 5f, 1f) : 0f;
        float blend = Mathf.lerp(build.power.status, 1 - build.power.status, pulse * flash);
        // Taken from DrawPower
        if(mixcol){
            Draw.color(emptyLightColor, fullLightColor, blend);
            if(emptyRegion.found()){
                Draw.rect(emptyRegion, build.x, build.y);
            }else{
                Fill.square(build.x, build.y, (tilesize * build.block.size / 2f - 1) * Draw.xscl);
            }
        }else{
            Draw.rect(emptyRegion, build.x, build.y);
            Draw.alpha(blend);
            Draw.rect(fullRegion, build.x, build.y);
        }
        Draw.color();
        Draw.z(z);
    }
}
