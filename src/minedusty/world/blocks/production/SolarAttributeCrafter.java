package minedusty.world.blocks.production;

import arc.Core;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import minedusty.DustAttributes;
import minedusty.world.meta.DustStat;

/** A Solar Crafter with the addition of requiring certain tiles to function.
 * The efficiency is based on the attribute of the tiles. For 100%, all tiles below the
 * block is needs the attribute value to be 1f. Can mix and match, but needs to add up to 100% for the size of the block.
 */
public class SolarAttributeCrafter extends SolarCrafter {

    public Attribute attribute = DustAttributes.turf;
    public float baseEfficiency = 0f;
    public float boostScale = 1f;
    public float maxBoost = 1.5f;
    public float minEfficiency = 0.5f;
    public float displayEfficiencyScale = 1f;
    public boolean displayEfficiency = true;
    /** If the liquid consumption rate should scale based on the efficiency of the block. */
    public boolean scaleLiquidConsumption = true;

    public SolarAttributeCrafter(String name) {
        super(name);
    }
    
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        if(!displayEfficiency) return;

        drawPlaceText(Core.bundle.format("bar.efficiency",
        (int)((baseEfficiency + Math.min(maxBoost, boostScale * sumAttribute(attribute, x, y))) * 100f)), x, y, valid);
    }

    @Override
    public void setBars(){
        super.setBars();

        if(!displayEfficiency) return;

        addBar("efficiency", (AttributeCrafterBuild entity) ->
            new Bar(
            () -> Core.bundle.format("bar.efficiency", (int)(entity.efficiencyMultiplier() * 100 * displayEfficiencyScale)),
            () -> Pal.lightOrange,
            entity::efficiencyMultiplier));
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return baseEfficiency + tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attribute)) >= minEfficiency;
    }
    
    @Override
    public void setStats(){
        super.setStats();

        stats.add(baseEfficiency <= 0.0001f ? Stat.tiles : Stat.affinities, attribute, floating, boostScale * size * size, !displayEfficiency);

        stats.add(DustStat.requiredEfficiency, (int)(minEfficiency * 100f), StatUnit.percent);
        stats.add(Stat.maxEfficiency, (int)(maxBoost * 100f), StatUnit.percent);
    }

    public class AttributeCrafterBuild extends SolarCrafterBuild{
        public float attrsum;

        @Override
        public float getProgressIncrease(float base){
            return super.getProgressIncrease(base) * efficiencyMultiplier();
        }

        public float efficiencyMultiplier(){
            float solarEff = super.efficiencyScale();
            float attrEff = baseEfficiency + Math.min(maxBoost, boostScale * attrsum * solarEff) + attribute.env();
            return attrEff;
        }

        @Override
        public float efficiencyScale(){
            return scaleLiquidConsumption ? efficiencyMultiplier() : super.efficiencyScale();
        }

        @Override
        public void pickedUp(){
            attrsum = 0f;
            warmup = 0f;
        }

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();
            
            attrsum = sumAttribute(attribute, tile.x, tile.y);
        }
    }

}
