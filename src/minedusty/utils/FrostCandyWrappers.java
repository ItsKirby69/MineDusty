package minedusty.utils;

import arc.Events;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.production.Drill;
import minedusty.blocks.DustBlocks;
import minedusty.blocks.DustDrills;
import minedusty.planets.DustPlanets;
import minedusty.world.meta.FrostModule;

public class FrostCandyWrappers {

    public static void addFrostDrill(Drill drill){
        addFrostDrill(drill, "frost-block");
    }

    public static void addFrostDrill(Drill drill, String name){
        FrostModule frost = new FrostModule();
        Events.on(EventType.ClientLoadEvent.class, e -> {
            frost.init(drill.size, name);
        });

        drill.buildType = () -> drill.new DrillBuild(){
            FrostModule.FrostState frostState;

            @Override
            public void created(){
                super.created();
                frostState = frost.createState(this);
            }

            @Override
            public void updateTile(){
                if(!DustPlanets.frostPlanets.contains(Vars.state.rules.planet.name)) return;
                super.updateTile();
                frost.update(this, frostState);
            }

            @Override
            public void display(Table table) {
                super.display(table);
                frost.display(table, frostState);
            }

            @Override
            public float efficiencyScale(){
                return super.efficiencyScale() * frost.drillEfficiency(frostState);
            }

            @Override
            public void damage(float amount){
                super.damage(amount * frost.damageMulti(frostState));
            }

            @Override
            public void draw(){
                float s = 0.3f;
                float ts = 0.6f;

                Draw.rect(drill.region, x, y);
                frost.drawFrost(this, frostState, 1);
                Draw.z(Layer.blockCracks);
                drawDefaultCracks();

                Draw.z(Layer.blockAfterCracks);
                if(drill.drawRim){
                    Draw.color(drill.heatColor);
                    Draw.alpha(warmup * ts * (1f - s + Mathf.absin(Time.time, 3f, s)));
                    Draw.blend(Blending.additive);
                    Draw.rect(drill.rimRegion, x, y);
                    Draw.blend();
                    Draw.color();
                }

                if(drill.drawSpinSprite){
                    Drawf.spinSprite(drill.rotatorRegion, x, y, timeDrilled * drill.rotateSpeed);
                }else{
                    Draw.rect(drill.rotatorRegion, x, y, timeDrilled * drill.rotateSpeed);
                }

                Draw.rect(drill.topRegion, x, y);

                if(dominantItem != null && drill.drawMineItem){
                    Draw.color(dominantItem.color);
                    Draw.rect(drill.itemRegion, x, y);
                    Draw.color();
                }
            }
        };
    }

    public static void addFrostWall(Wall wall){
        addFrostWall(wall, "frost-block");
    }

    public static void addFrostWall(Wall wall, String name){
        FrostModule frost = new FrostModule();
        Events.on(EventType.ClientLoadEvent.class, e -> {
            frost.init(wall.size, name);
        });

        wall.update = true;
        wall.buildType = () -> wall.new WallBuild(){
            FrostModule.FrostState frostState;

            @Override
            public void created(){
                super.created();
                frostState = frost.createState(this);
            }

            @Override
            public void updateTile(){
                if(!DustPlanets.frostPlanets.contains(Vars.state.rules.planet.name)) return;
                super.updateTile();
                frost.update(this, frostState);
            }

            @Override
            public void display(Table table) {
                super.display(table);
                frost.display(table, frostState);
            }

            @Override
            public void damage(float amount){
                super.damage(amount * frost.damageMulti(frostState));
            }

            @Override
            public void draw(){
                super.draw();
                frost.drawFrost(this, frostState, 1);
            }
        };
    }

    public static void loadFrostedBlocks(){
        // Drills WIP
        /*
        addFrostDrill((Drill)Blocks.mechanicalDrill, "minedusty-drill");
        addFrostDrill((Drill)Blocks.pneumaticDrill, "minedusty-drill");

        // TODO make custom class for this
        addFrostDrill((Drill)DustDrills.copperDrill, "minedusty-drill");
        addFrostDrill((Drill)DustDrills.chloroDrill, "minedusty-drill");
        */

        // Walls
        addFrostWall((Wall)Blocks.copperWall, "minedusty-vanilla");
        addFrostWall((Wall)Blocks.copperWallLarge, "minedusty-vanilla");
        addFrostWall((Wall)Blocks.titaniumWall, "minedusty-vanilla");
        addFrostWall((Wall)Blocks.titaniumWallLarge, "minedusty-vanilla");
    }
}
