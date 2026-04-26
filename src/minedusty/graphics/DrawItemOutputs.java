package minedusty.graphics;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import arc.util.Log;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;
import minedusty.world.blocks.production.BetterGenericCrafter;

public class DrawItemOutputs extends DrawBlock{
    public TextureRegion[][] itemOutputRegions;

    @Override
    public void draw(Building build){
        BetterGenericCrafter crafter = (BetterGenericCrafter)build.block;
        if(crafter.outputItems == null) return;

        for(int i = 0; i < crafter.outputItems.length; i++){
            int side = i < crafter.itemOutputDirections.length ? crafter.itemOutputDirections[i] : -1;
            if(side != -1){
                int realRot = (side + build.rotation) % 4;
                Draw.rect(itemOutputRegions[realRot > 1 ? 1 : 0][i], build.x, build.y, realRot * 90);
            }
        }
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list){
        BetterGenericCrafter crafter = (BetterGenericCrafter)block;
        if(crafter.outputItems == null) return;

        for(int i = 0; i < crafter.outputItems.length; i++){
            int side = i < crafter.itemOutputDirections.length ? crafter.itemOutputDirections[i] : -1;
            if(side != -1){
                int realRot = (side + plan.rotation) % 4;
                Draw.rect(itemOutputRegions[realRot > 1 ? 1 : 0][i], plan.drawx(), plan.drawy(), realRot * 90);
            }
        }
    }

    @Override
    public void load(Block block){
        var crafter = expectCrafter(block);

        if(crafter.outputItems == null) return;

        itemOutputRegions = new TextureRegion[2][crafter.outputItems.length];
        for(int i = 0; i < crafter.outputItems.length; i++){
            for(int j = 1; j <= 2; j++){
                itemOutputRegions[j - 1][i] = Core.atlas.find(block.name + "-" + crafter.outputItems[i].item.name + "-output" + j);
            }
        }
    }
}
