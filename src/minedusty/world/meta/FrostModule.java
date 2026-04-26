package minedusty.world.meta;

import static arc.Core.*;
import static mindustry.Vars.*;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectSet;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.world.Tile;
import mindustry.world.blocks.liquid.LiquidBlock;
import mindustry.world.blocks.production.GenericCrafter.GenericCrafterBuild;
import minedusty.DustAttributes;
import minedusty.content.*;
import minedusty.planets.DustPlanets;
import minedusty.utils.WeatherUtil;
import minedusty.world.blocks.defense.DefrosterBlock.DefrosterBlockBuild;

public class FrostModule {
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
    public int heatRange = 7;
    public Effect meltEffect = DustyEffects.meltSteam;

    public int actualStages;
    public TextureRegion[][] frostRegions;


    public static class FrostState{
        public int currentFrostStage = -1;
        public float thermalPower = 0f;
        public float frostProg = 0f;
        public int maxFrostStage = 0;
        public int variant;

        /** Actual Stage duration randomized at creation */
        public float effectiveStageDur;
        /** Frost resistance of larger blocks. */
        float sizeResist;
        /** Rate in which the block will frost (positive) or melt (negative) */
        public float frostRate = 0f;
        float frostAlpha = 0;

        float heatTimer = 0f;
        float brittleTimer = 0f;
        
        ObjectSet<Building> counted = new ObjectSet<>(); //Copy of checked buildings
    
        public void init(Building build, FrostModule module){
            sizeResist = 1f + (build.block.size - 1) * module.resistanceFactor;
            effectiveStageDur = module.stageDur + Mathf.random(0, module.stageDur * module.durationRandomness);
            maxFrostStage = module.actualStages - 1;
            variant = Mathf.randomSeed(build.tile.pos(), 0, Math.max(0, module.frostVariants - 1));
        }
    }

    public FrostModule(){
    }

    public void init(int size, String name){
        actualStages = maxStages - 1 + size;

        frostRegions = new TextureRegion[actualStages][frostVariants];
        String sizeString = size == 1 ? "" : (size == 2 ? "-large" : "-huge");

        for(int i = 0; i < actualStages; i++){
            for(int v = 0; v < frostVariants; v++){

                //oxide-copper-wall-frost-large-stage1-1 //Custom
                //frost-wall-large-stage1-1 // General
                String customName = name + "-frost" + sizeString + "-stage" + i + "-" + (v + 1);
                String fallbackName = "minedusty-frost-wall" + sizeString + "-stage" + i + "-" + (v + 1);

                frostRegions[i][v] = atlas.find(customName, fallbackName);
            }
        }
        // Log.info("Name: @ and frostregion: @", name, frostRegions);
    }

    public FrostState createState(Building build){
        FrostState state = new FrostState();
        state.init(build, this);
        return state;
    }

    
    public void update(Building build, FrostState state){
        if(!DustPlanets.frostPlanets.contains(Vars.state.rules.planet.name)) return;
        boolean coldWeather = WeatherUtil.activeWeather(DustWeathers.snowStorm, Weathers.snow);

        state.heatTimer += Time.delta;
        if(state.heatTimer >= 0.5f){
            state.heatTimer = 0f;
            updateHeat(build, state);
        }
        
        float baseRate = frostSpeed / state.sizeResist;
        float heatModifier = 0.5f * state.thermalPower;

        if(coldWeather){
            state.maxFrostStage = Math.max(-1, actualStages - 1 - Mathf.floor(state.thermalPower));

            state.frostRate = baseRate * (WeatherUtil.activeWeather(DustWeathers.snowStorm) ? 2.0f : 1f) - heatModifier;
            state.frostRate = Math.max(state.frostRate, -1.5f * baseRate);
        }else{
            state.frostRate = -baseRate - heatModifier;
            state.frostRate = Math.max(state.frostRate, -2.5f * baseRate);
        }

        // Go back a stage when at maxStage if thermalPower >= 1
        if(state.currentFrostStage > state.maxFrostStage){
            state.frostRate = -Math.abs(baseRate);
        }

        state.frostProg = Mathf.clamp(state.frostProg + Time.delta * state.frostRate, 0, state.effectiveStageDur);
        
        // Incrememnt or decrement stages
        if(state.frostRate > 0 && state.currentFrostStage < state.maxFrostStage){
            if(state.frostProg >= state.effectiveStageDur){
                state.frostProg = 0;
                state.currentFrostStage = Math.min(state.currentFrostStage + 1, state.maxFrostStage);
                state.frostAlpha = 1f;
            }
        }else if(state.frostRate <= 0 && state.currentFrostStage > -1){
            if(state.frostProg <= 0){
                state.frostProg = state.effectiveStageDur;
                state.currentFrostStage = Math.max(state.currentFrostStage - 1, -1);
                meltEffect.at(build.x, build.y, Color.gray);
            }
        }

        // Take damage in pulses at max Stage
        if(state.currentFrostStage >= actualStages - 1){
            state.brittleTimer += Time.delta;
            if(state.brittleTimer >= brittleInterval){
                build.damage(build.maxHealth * brittlePercent);
                state.brittleTimer = 0f;
                state.frostAlpha = 1f;
                brittleSound.at(build.x(), build.y(), Mathf.random(0.9f, 1.2f), 0.7f);
            }
        }else state.brittleTimer = 0f;
    }
    
    void updateHeat(Building build, FrostState state){
        state.thermalPower = 0f;

        float cx = build.tile.x + (build.block.size - 1) / 2f;
        float cy = build.tile.y + (build.block.size - 1) / 2f;

        int minX = (int)(cx - heatRange);
        int maxX = (int)(cx + heatRange);
        int minY = (int)(cy - heatRange);
        int maxY = (int)(cy + heatRange);

        state.counted.clear();

        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){

                Tile other = world.tile(x, y);
                if(other == null) continue;

                // Buildings
                if(other.build != null){
                    Building b = other.build;
                    boolean isDefroster = b instanceof DefrosterBlockBuild;

                    float sizeCalc = (b.block.size - 1) / 2f;
                    // Calculate from center
                    float bx = b.tile.x + sizeCalc;
                    float by = b.tile.y + sizeCalc;

                    float dx = Math.abs(bx - cx);
                    float dy = Math.abs(by - cy);

                    int dst = Mathf.floor(Math.max(dx, dy));
                    if(dst <= heatRange && state.counted.add(b)){
                        int baseHeat = 0;
                        // Check for running machines
                        if(!isDefroster && b instanceof GenericCrafterBuild g && g.efficiency > 0f){
                            baseHeat += 1;
                        }
                        // Check for defrosters
                        if(isDefroster){
                            baseHeat += Math.round(((DefrosterBlockBuild)b).heat);
                        }
                        // Check for slag filled pipes
                        if(b.block instanceof LiquidBlock){
                            if(b.liquids != null && b.liquids.get(Liquids.slag) > 1f){
                                baseHeat += 1;
                            }
                        }
                        // Check for env tiles
                        baseHeat += Math.round(b.block.attributes.get(DustAttributes.thermalPower));

                        if(baseHeat > 0){
                            // every 2 tiles reduce 1 power
                            int effective = Math.max(0, baseHeat - Mathf.ceil(dst / 2));
                            state.thermalPower += effective;
                        };
                    };
                }

                float floorHeat = other.floor().attributes.get(DustAttributes.thermalPower);
                if(floorHeat > 0f){
                    int dst = (int)Math.max(Math.abs(x - cx), Math.abs(y - cy));
                    if(dst <= heatRange){
                        int effective = Math.max(0, Math.round(floorHeat) - dst / 2);
                        state.thermalPower += effective;
                    }
                }
            }
        }
    }

    public void display(Table table, FrostState frostState){
        if(!DustPlanets.frostPlanets.contains(Vars.state.rules.planet.name)){
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Outside planets";
                }).left().growX();
            }).growX();
            return;
        };
        if(settings.getBool("@setting.dusty-block-debug")){
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Heat: " + frostState.thermalPower;
                }).left().growX();
            }).growX();
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Stage: " + frostState.currentFrostStage;
                }).left().growX();
            }).growX();
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Frost Rate: " + frostState.frostRate;
                }).left().growX();
            }).growX();
            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Dur: " + Mathf.round(frostState.frostProg/frostState.effectiveStageDur, 0.1f) * 100 + "%";
                }).left().growX();
            }).growX();
        }
    }

    public void drawFrost(Building build, FrostState state, int frostVariants){
        if(state.currentFrostStage >= 0 && frostRegions != null){
            int stage = Math.min(state.currentFrostStage, actualStages - 1);

            state.frostAlpha = Mathf.lerpDelta(state.frostAlpha, 0.5f, 0.08f);
            Draw.color(1f,1f,1f,state.frostAlpha);
            Draw.rect(frostRegions[stage][state.variant],build.x,build.y,build.rotdeg());
            Draw.reset();
        }
    }

    public float drillEfficiency(FrostState state){
        if(state.currentFrostStage <= 1) return 1f;
        float effLoss = 0.25f * (state.currentFrostStage - 1);
        return Math.max(0f, 1f - effLoss);
    }

    public float damageMulti(FrostState state){
        return state.currentFrostStage >= actualStages - 1 ? 5f : 1f;
    }
}
