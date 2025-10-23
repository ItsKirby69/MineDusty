package minedusty.world.blocks.production;

import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Drill;
import minedusty.world.blocks.environment.TieredOreBlock;

/** A normal drill with the capabilities of mining the higher tiered resource from an ore
 * if it has sufficient tier. Galena for example, requires a certain tier to mine galena itself,
 * otherwise it drops lead.
*/
public class TierDrill extends Drill {
    public TierDrill(String name) {
        super(name);
    }

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
