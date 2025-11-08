package minedusty.world.blocks.production;

import arc.Core;
import arc.graphics.Color;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.meta.*;
import minedusty.world.meta.DustStat;

public class OffshoreDrill extends ModifiedDrill{
    /** How the efficiency scales. */
    public float waterEfficiencyScale = 1f;
    /** The minimum efficiency the drill would have. */
    public float baseWaterEfficiency = 0f;
    /** Uses average liquid multiplier of the tiles. */
    public boolean useAverage = true;

    public OffshoreDrill(String name) {
        super(name);
        floating = true;
        requiresWater = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(DustStat.maxEfficiency, 100f * waterEfficiencyScale, StatUnit.percent);
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("efficiency", (OffshoreDrillBuild entity) ->
            new Bar(
            () -> Core.bundle.format("bar.efficiency", (int)(entity.efficiencyMultiplier() * 100)),
            () -> Color.valueOf("#596ab8"),
            entity::efficiencyMultiplier));
    }
    
    public class OffshoreDrillBuild extends ModifiedDrillBuild{

        public float efficiencyMultiplier(){
            float total = 0f;
            int count = 0;
            for(Tile other : tile.getLinkedTiles(tempTiles)){
                if(other.floor().liquidDrop != null){
                    total += other.floor().liquidMultiplier;
                    count++;
                }
            }

            if(useAverage && count > 0) total /= count;

            return baseWaterEfficiency + total * waterEfficiencyScale;
        }
        
        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();
        }

        @Override
        public void updateTile(){
            efficiency = efficiencyMultiplier();
            super.updateTile();
        }
    }
}
