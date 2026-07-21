package minedusty.world.blocks.defense;

import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.*;
import mindustry.content.Weathers;
import mindustry.type.Weather;
import minedusty.content.DustWeathers;
import minedusty.utils.WeatherUtil;

import static arc.Core.*;

/** Wall which oxidizes over time */
public class OxideWall extends DustWall{
    /** How many visual stages will there be */
    public int maxOxideStages = 4;
    /** Minimum Duration of each stage in ticks (60 ticks per second) */
    public float oxideStageDur = 3600 * 3f;
    /** Randomness value. Randomly multiplies with stageDur then added to final time for a proper stage duration. */
    public float durationOxideRandomness = 5f;
    /** How fast stagetime passes by if rainy weather */
    public float rainAccel = 2.5f;

    public TextureRegion[][] stageRegions;
    private static final Weather[] oxidizingWeathers = {Weathers.rain, Weathers.fog, DustWeathers.heavyRain};
    private static final Weather[] rustingWeathers = {DustWeathers.heavyRain};

    public OxideWall(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();

        stageRegions = new TextureRegion[maxOxideStages][Math.max(variants, 1)];

        for(int i = 0; i < maxOxideStages; i++){
            for(int v = 0; v < (variants > 0 ? variants : 1); v++){
                stageRegions[i][v] = atlas.find(variants > 0 ?
                    name + "-stage" + i + "-" + (v + 1) :
                    name + "-stage" + i
                );
            }
        }
    }

    public class OxideWallBuild extends DustWallBuild {
        public int currentStage = 0;
        public float stageTimer = 0f;
        public float currStageDuration;

        public float timeScale = 1f;

        @Override
        public void created(){
            super.created();
            randomStageDur();
        }

        /** Randomize stage duration once created or once a stage ended. */
        public void randomStageDur(){
            currStageDuration = oxideStageDur + Mathf.random(0, oxideStageDur * durationOxideRandomness);
        }

        @Override
        public void updateTile(){
            super.updateTile();
            boolean activeweather = WeatherUtil.activeWeather(oxidizingWeathers);
            timeScale = activeweather ? (WeatherUtil.activeWeather(rustingWeathers) ? rainAccel * 2f : rainAccel) : 1f;

            if(currentStage < maxOxideStages - 1){
                if(frostState.currentFrostStage == frostState.maxFrostStage) return; // Don't advance oxidation if fully frosted duh
                stageTimer += Time.delta * timeScale;
                
                if (stageTimer >= currStageDuration){ 
                    stageTimer = 0f;
                    currentStage++;

                    randomStageDur();
                }
            }
        }

        @Override
        public void display(Table table){
            super.display(table);
            // table.row();
            // table.table(t -> {
            //     t.label(() -> {
            //         return "[lightgray]Stage Duration: " + Mathf.round(currStageDuration / (60f * timeScale), 1f) + "s/stage";
            //     }).left().growX();
            // }).growX();
            if(!settings.getBool("dusty-block-debug")) return;
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "[lightgray]O Stage/Rate: " + currentStage + " | x" + timeScale;
                }).left().growX();
            }).growX();
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "[lightgray]Oxide Duration: " + Mathf.round(stageTimer/currStageDuration * 100f, 1f) + "%";
                }).left().growX();
            }).growX();
        }

        @Override
        public void draw(){
            int variant = Mathf.randomSeed(tile.pos(), 0, Math.max(0, variants - 1));
            int stage = Math.min(currentStage, maxOxideStages - 1);

            Draw.rect(stageRegions[stage][variant], x, y, rotdeg());
            frost.drawFrost(this, frostState, frostVariants);
        }
    }
}