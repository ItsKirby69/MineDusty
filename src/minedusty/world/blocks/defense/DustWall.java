package minedusty.world.blocks.defense;

import arc.audio.Sound;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.world.*;
import mindustry.world.blocks.defense.Wall;
import minedusty.DustAttributes;
import minedusty.content.*;
import minedusty.utils.WeatherUtil;
import minedusty.world.blocks.defense.DefrosterBlock.DefrosterBlockBuild;

import static arc.Core.*;
import static mindustry.Vars.*;

/** A wall that may frost up during cold weather. */
public class DustWall extends Wall {
    /** How many visual stages will there be. +1 stage per extra size. */
    public int maxStages = 4;
    /** How long a stage takes to advance to. */
    public float stageDur = 60f * 2f;
    /** Randomness value. Randomly multiplies with stageDur then added to final time. */
    public float durationRandomness = 3f;
    /** How fast to frost the block to next stage. */
    public float frostSpeed = 1f;

    public float brittleInterval = 2f * 60f;
    public float brittlePercent = 0.025f;
    public Sound brittleSound = DustSounds.brittle;

    /** Resistance to freezing based on size. */
    public float resistanceFactor = 0.25f;

    /** How many default frost variants for Walls. */
    private int frostVariants = 1;
    /** Range at checking thermal blocks */
    public int heatRange = 5;
    public int actualStages;
    public TextureRegion[][] frostRegions;

    public Effect meltEffect = DustyEffects.meltSteam;

    public DustWall(String name){
        super(name);
        update = true;
        variants = 0;
    }

    @Override
    public void load(){
        super.load();
        // Stages based on size
        actualStages = maxStages - 1 + size;

        frostRegions = new TextureRegion[actualStages][frostVariants];

        String sizeString = size == 1 ? "" : (size == 2 ? "-large" : "-huge");

        for(int i = 0; i < actualStages; i++){
            for(int v = 0; v < frostVariants; v++){

                //oxide-copper-wall-large-frost-stage1-1 //Custom
                //frost-wall-large-stage1-1 // General
                String customName = name + "-frost" + "-stage" + i + "-" + (v + 1);
                String fallbackName = "minedusty-frost-wall" + sizeString + "-stage" + i + "-" + (v + 1);

                frostRegions[i][v] = atlas.find(customName, fallbackName);
            }
        }
    }

    public class DustWallBuild extends WallBuild {
        public int currentFrostStage = -1;
        public float thermalPower = 0f;
        public float frostProg = 0f;

        /** Actual Stage duration randomized at creation */
        float effectiveStageDur;
        /** Frost resistance of larger blocks. */
        float sizeResist;
        /** Rate in which the block will frost (positive) or melt (negative) */
        float frostRate = 0f;
        float frostAlpha = 0;

        int maxFrostStage = actualStages - 1;

        @Override
        public void created(){
            super.created();
            sizeResist = 1f + (size - 1) * resistanceFactor;
            effectiveStageDur = stageDur + Mathf.random(0, stageDur * durationRandomness);
        }
        
        float heatTimer = 0f;
        float brittleTimer = 0f;

        @Override
        public void updateTile(){
            boolean coldWeather = WeatherUtil.activeWeather(Weathers.snow, DustWeathers.snowStorm);

            heatTimer += Time.delta;
            if(heatTimer >= 0.5f){
                heatTimer = 0f;
                updateHeat();
            }
            
            float baseRate = frostSpeed / sizeResist;
            float heatModifier = 0.5f * thermalPower;

            if(coldWeather){
                maxFrostStage = Math.max(-1, actualStages - 1 - Mathf.floor(thermalPower));

                frostRate = baseRate - heatModifier;
                frostRate = Math.max(frostRate, -1.5f * baseRate);
            }else{
                frostRate = -baseRate - heatModifier;
                frostRate = Math.max(frostRate, -2.5f * baseRate);
            }

            // Go back a stage when at maxStage if thermalPower >= 1
            if(currentFrostStage > maxFrostStage){
                frostRate = -Math.abs(baseRate);
            }

            frostProg = Mathf.clamp(frostProg + Time.delta * frostRate, 0, effectiveStageDur);
            
            // Incrememnt or decrement stages
            if(frostRate > 0 && currentFrostStage < maxFrostStage){
                if(frostProg >= effectiveStageDur){
                    frostProg = 0;
                    currentFrostStage = Math.min(currentFrostStage + 1, maxFrostStage);
                    frostAlpha = 1f;
                }
            }else if(frostRate <= 0 && currentFrostStage > -1){
                if(frostProg <= 0){
                    frostProg = effectiveStageDur;
                    currentFrostStage = Math.max(currentFrostStage - 1, -1);
                    meltEffect.at(x, y, Color.gray);
                }
            }

            // Take damage in pulses at max Stage
            if(currentFrostStage >= actualStages - 1){
                brittleTimer += Time.delta;
                if(brittleTimer >= brittleInterval){
                    super.damage(maxHealth * brittlePercent);
                    brittleTimer = 0f;
                    frostAlpha = 1f;
                    // TODO fix brittle sound not playing
                    brittleSound.at(x(), y());
                }
            }else brittleTimer = 0f;
        }

        // TEMP
        @Override
        public void display(Table table) {
            super.display(table);

            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Heat: " + thermalPower;
                }).left().growX();
            }).growX();
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Stage: " + currentFrostStage;
                }).left().growX();
            }).growX();
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Frost Rate: " + frostRate;
                }).left().growX();
            }).growX();
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Dur: " + Mathf.round(frostProg/effectiveStageDur, 0.1f) + "%";
                }).left().growX();
            }).growX();
        }

        // TODO fix issue with counting all blocks in 2x2 sized heaters
        void updateHeat(){
            thermalPower = 0f;

            float cx = tile.x + (size - 1) / 2f;
            float cy = tile.y + (size - 1) / 2f;

            int minX = (int)(cx - heatRange);
            int maxX = (int)(cx + heatRange);
            int minY = (int)(cy - heatRange);
            int maxY = (int)(cy + heatRange);

            for(int x = minX; x <= maxX; x++){
                for(int y = minY; y <= maxY; y++){

                    Tile other = world.tile(x, y);
                    if(other == null) continue;

                    int baseHeat = 0;
                    
                    if(other.build != null && other.build.block instanceof DefrosterBlock){
                        DefrosterBlockBuild def = (DefrosterBlockBuild)other.build;
                        baseHeat = Math.round(def.heat);
                    }else{
                        float blockHeat = other.block().attributes.get(DustAttributes.thermalPower);
                        float floorHeat = other.floor().attributes.get(DustAttributes.thermalPower);
                        baseHeat = Math.round(Math.max(blockHeat, floorHeat));
                    }

                    if(baseHeat <= 0) continue;

                    int dst = (int)Math.max(Math.abs(x - cx), Math.abs(y - cy));
                    if(dst > heatRange) continue;

                    // every 2 tiles reduce 1 power
                    int effective = Math.max(0, baseHeat - dst / 2);

                    thermalPower += effective;
                }
            }
        }

        @Override
        public void damage(float amount){
            if(currentFrostStage >= actualStages - 1){
                amount *= 5f;
            }
            super.damage(amount);
        }

        @Override
        public void draw(){
            super.draw();
            drawFrost();
        }

        public void drawFrost(){
            if(currentFrostStage >= 0){
                int variant = Mathf.randomSeed(tile.pos(), 0, Math.max(0, frostVariants - 1));
                int stage = Math.min(currentFrostStage, actualStages - 1);

                frostAlpha = Mathf.lerpDelta(frostAlpha, 0.5f, 0.08f);
                Draw.color(1f,1f,1f,frostAlpha);
                Draw.rect(frostRegions[stage][variant],x,y,rotdeg());
                Draw.reset();
            }
        }
    }
}