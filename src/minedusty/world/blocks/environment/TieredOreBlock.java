package minedusty.world.blocks.environment;

import mindustry.type.*;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.production.Drill;

public class TieredOreBlock extends OreBlock{
    public Item defaultDrop;
    public Item tierOre;
    public int tier = 3;

    public TieredOreBlock(String name, Item defaultDrop, Item tierOre, int tier) {
        super(name);
        this.defaultDrop = defaultDrop;
        this.tierOre = tierOre;
        this.tier = tier;

        this.itemDrop = defaultDrop;
    }

    public TieredOreBlock(String name, Item defaultDrop, Item tierOre) {
        super(name);
        this.defaultDrop = defaultDrop;
        this.tierOre = tierOre;

        this.itemDrop = defaultDrop;
    }

    public Item getDropFor(Drill drill, Tile tile){
        int drillTier = (drill != null) ? drill.tier : 0;
        return drillTier >= tier ? tierOre : defaultDrop;
    }

}
