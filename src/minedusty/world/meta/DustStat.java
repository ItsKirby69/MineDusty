package minedusty.world.meta;

import static arc.Core.bundle;
import static mindustry.Vars.content;
import static mindustry.Vars.indexer;

import arc.func.Boolf;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.*;

public class DustStat{

    public static final Stat
    powerRanged = new Stat("powerRanged", StatCat.power),

    solarRequired = new Stat("solarrequirement", StatCat.crafting),
    requiredEfficiency = new Stat("requiredefficiency", StatCat.crafting),

    heatEfficiency = new Stat("heatefficiency", StatCat.crafting),

    boostItems = new Stat("boostitems", StatCat.optional);

    public static StatValue boostedItems(Item raw, Item boosted, Boolf<Block> matches){
        return table -> {
            table.row();
            table.table(c -> {
                c.table(Styles.grayPanel, b -> {
                    var blocks = Vars.content.blocks()
                        .select(block -> indexer.isBlockPresent(block) 
                        && !(block instanceof Floor f && f.isDeep())
                        && matches.get(block));
                    
                    b.table(info -> {
                        if(blocks.any()){
                            for(Block block : blocks){
                                info.image(block.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit).with(i -> StatValues.withTooltip(i, block, false));
                            }
                        }else{
                            // TODO center this
                            info.add("@none.inmap").size(40).pad(10f).left().scaling(Scaling.fit);
                        }
                    }).growX();

                    if(blocks.any()){
                        b.label(() -> bundle.get("stat.itemwhenboost")).pad(10f).padRight(40f);
                        b.table(right -> {
                            b.image(boosted.uiIcon).size(40).pad(10f).right().scaling(Scaling.fit).with(i -> StatValues.withTooltip(i, boosted, false));
                            
                            b.table(info -> {
                                info.add(boosted.localizedName).left().row();
                            });
                            
                        }).growX().right();
                    }
                }).growX().pad(5).row();
            }).growX().colspan(table.getColumns());
            table.row();
        };
    }

    public static StatValue boostedItems(Item raw, Item boosted){
        return boostedItems(raw, boosted, block -> block.itemDrop == raw);
    }
}
