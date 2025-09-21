package minedusty.world.blocks.production;

import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Drill;
import minedusty.world.blocks.environment.TieredOreBlock;

/** Mines tiered ores and normal ores. If tier is sufficient, the drill will mine tiered resource from the ore. */
public class TierDrill extends Drill {
    public TierDrill(String name) {
        super(name);
    }

    /*@Override
    public boolean canMine(Tile tile){
        if(tile == null || tile.block().isStatic()) return false;
        Item drops = tile.drop();
        return drops != null && drops.hardness <= tier && (blockedItems == null || !blockedItems.contains(drops));
    }*/

    @Override
    public Item getDrop(Tile tile){
        if(tile == null) return null;

        if(tile.overlay() instanceof TieredOreBlock){
            TieredOreBlock tiered = (TieredOreBlock)tile.overlay();
            return tiered.getDropFor(this, tile);
        }
        if(tile.floor() instanceof TieredOreBlock){
            TieredOreBlock tiered = (TieredOreBlock)tile.floor();
            return tiered.getDropFor(this, tile);
            
        }
        return tile.drop();
    }
}
