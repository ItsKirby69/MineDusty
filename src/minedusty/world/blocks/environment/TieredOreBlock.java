package minedusty.world.blocks.environment;

import arc.Core;
import mindustry.type.*;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.production.Drill;

public class TieredOreBlock extends OreBlock{
    /** Default drop of the ore if drill is under-tiered. */
    public Item defaultDrop;
    /** Tiered ore dropped if drill tier is equal or greater than the required tier. */
    public Item tierDrop;
    public int tier = 3;

    public TieredOreBlock(String name, Item defaultDrop, Item tierDrop, int tier) {
        super(name);
        this.defaultDrop = defaultDrop;
        this.tierDrop = tierDrop;
        this.tier = tier;

        this.itemDrop = defaultDrop;
        this.localizedName = tierDrop.localizedName;
    }

    // For normal ores/non-tiered ores
    public TieredOreBlock(String name, Item defaultDrop) {
        super(name);
        this.defaultDrop = defaultDrop;
        this.itemDrop = defaultDrop;

        this.localizedName = defaultDrop.localizedName;
    }

    // Force localized name to be the tiered one.
    @Override
    public void setup(Item ore){
        Item displayItem = tierDrop != null ? tierDrop : defaultDrop;

        this.localizedName = displayItem.localizedName + (wallOre ? " " + Core.bundle.get("wallore") : "");
        this.mapColor.set(displayItem.color);

        this.itemDrop = defaultDrop;
    }

    public Item getDropFor(Drill drill, Tile tile){
        int drillTier = (drill != null) ? drill.tier : 0;
        if (drillTier >= tier && tierDrop != null) return tierDrop;
        return defaultDrop;
    }
}
