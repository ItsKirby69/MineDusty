package minedusty.world.blocks.production;

import static mindustry.Vars.indexer;

import arc.util.Log;
import mindustry.Vars;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Drill;
import minedusty.world.blocks.environment.TieredOreBlock;
import minedusty.world.meta.DustStat;

/** A normal drill with the capabilities of mining the higher tiered resource from an ore
 * if it has sufficient tier. Galena for example, requires a certain tier to mine galena itself,
 * otherwise it drops lead.
 * If boosted, adds boostTier value to it's original tier for tiered drops purposes.
*/
public class TierDrill extends Drill {
    public int boostTier = 1;
    
    public TierDrill(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        // Gotta reorganize this
        var ores = Vars.content.blocks()
            .select(block -> indexer.isBlockPresent(block) && block instanceof TieredOreBlock bloc && bloc.tier <= tier + boostTier && block.itemDrop != null);
        if(ores.any()){
            stats.add(DustStat.boostItems, t -> {
                for(Block resource : ores){
                    TieredOreBlock tiered = (TieredOreBlock)resource;
                    if(tiered.tierDrop != null && tiered.defaultDrop != tiered.tierDrop){
                        DustStat.boostedItems(tiered.defaultDrop, tiered.tierDrop,
                            block -> block instanceof TieredOreBlock ore 
                            && ore.defaultDrop == tiered.defaultDrop
                            && ore.tierDrop == tiered.tierDrop
                        ).display(t);
                        t.row();
                    }
                }
            });
        }
    }

    @Override
    public Item getDrop(Tile tile){
        if(tile == null) return null;

        boolean boosted = tile.build instanceof TierDrillBuild drillBuild && drillBuild.isBoosted();
        int dropTier = tier + (boosted ? boostTier : 0);

        if(tile.overlay() instanceof TieredOreBlock){
            TieredOreBlock tiered = (TieredOreBlock)tile.overlay();
            return tiered.getDrop(dropTier);
        }
        if(tile.floor() instanceof TieredOreBlock){
            TieredOreBlock tiered = (TieredOreBlock)tile.floor();
            return tiered.getDrop(dropTier);
        }
        return tile.drop();
    }

    public class TierDrillBuild extends DrillBuild{
        public boolean wasBoosted = false;

        public boolean isBoosted(){
            return optionalEfficiency > 0f; //mm
        }

        @Override
        public void updateTile(){
            boolean boosted = isBoosted();
            Log.info("build: @", buildType);
            if(boosted != wasBoosted){
                wasBoosted = boosted;
                onProximityUpdate();
            }
            super.updateTile();
        }
    }
}
