package minedusty.world.blocks.power;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.type.LiquidStack;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

/** A thermalGenerator that takes size of machine out of the equation. */
public class GeothermalGenerator extends PowerGenerator{
   public Effect generateEffect = Fx.redgeneratespark;;
   public float effectChance = 0.05F;;
   public float minEfficiency = 0.0F;
   public float displayEfficiencyScale = 1.0F;
   public boolean displayEfficiency = true;
   @Nullable
   public LiquidStack outputLiquid;
   public Attribute attribute = Attribute.heat;

   public GeothermalGenerator(String name) {
        super(name);
        noUpdateDisabled = true;
        floating = true;
        ambientSound = Sounds.hum;
        ambientSoundVolume = 0.03f * size;
        effectChance = 0.008f * size;
    }

    public float getDisplayedPowerProduction() {
        return powerProduction / displayEfficiencyScale;
    }

    public void init() {
        if (outputLiquid != null) {
            outputsLiquid = true;
            hasLiquids = true;
        }

        emitLight = true;
        super.init();
        lightClipSize = Math.max(lightClipSize, 45.0F * (float)size * 2.0F * 2.0F);
    }

    public void setStats() {
        super.setStats();
        // 4 multi to match with vanilla's 
        stats.add(Stat.tiles, attribute, floating, 4f * displayEfficiencyScale, !displayEfficiency);
        stats.remove(generationType);
        stats.add(generationType, powerProduction * 60.0F / displayEfficiencyScale, StatUnit.powerSecond);
        if (outputLiquid != null) {
            stats.add(Stat.output, StatValues.liquid(outputLiquid.liquid, outputLiquid.amount * 60.0F, true));
        }
    }

    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        if (displayEfficiency) {
            drawPlaceText(Core.bundle.formatFloat("bar.efficiency", (sumAttribute(attribute, x, y) * 100.0F) , 1), x, y, valid);
        }
    }

    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return tile.getLinkedTilesAs(this, tempTiles).sumf((other) -> {
            return other.floor().attributes.get(attribute);
        }) > minEfficiency;
    }

    public class GeothermalGeneratorBuild extends GeneratorBuild{
        public float sum;

        @Override
        public void updateTile(){
            productionEfficiency = sum + attribute.env();

            if(productionEfficiency > 0.1f && Mathf.chanceDelta(effectChance)){
                generateEffect.at(x + Mathf.range(3f), y + Mathf.range(3f));
            }

            if(outputLiquid != null){
                float added = Math.min(productionEfficiency * delta() * outputLiquid.amount, liquidCapacity - liquids.get(outputLiquid.liquid));
                liquids.add(outputLiquid.liquid, added);
                dumpLiquid(outputLiquid.liquid);
            }
        }

        @Override
        public void afterPickedUp(){
            super.afterPickedUp();
            sum = 0f;
        }

        @Override
        public float totalProgress(){
            return enabled && sum > 0 ? super.totalProgress() : 0f;
        }

        @Override
        public void drawLight(){
            Drawf.light(x, y, (40f + Mathf.absin(10f, 5f)) * Math.min(productionEfficiency, 2f) * size, Color.scarlet, 0.4f);
        }

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();

            sum = sumAttribute(attribute, tile.x, tile.y);
        }
    }
}
