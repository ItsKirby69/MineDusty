package minedusty.world.blocks.power;

import static minedusty.content.DustItems.*;
import static mindustry.Vars.*;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import mindustry.game.EventType.Trigger;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.Battery;
import mindustry.world.draw.*;
import minedusty.content.DustLiquids;

public class SaltBattery extends Battery {
    public Color coolColor = new Color(1, 1, 1, 0f);
    public Color hotColor = Color.valueOf("#ff9575a3");

    public float liquidConsume = 0.5f/60f;
    public float saltProduction = 0.2f/60f;

    public float heating = 0.2f/60f;

    public float minLiquidLevel = 50f;
    public float powerDrainRate = 0.15f/60f/10f;

    public SaltBattery(String name){
        super(name);
        update = true;
        hasLiquids = true;
        hasItems = true;

        itemCapacity = 15;
        liquidCapacity = 80;

        drawer = new DrawMulti(
            new DrawRegion("-bottom"),
            new DrawLiquidTile(DustLiquids.saltWater),
            new DrawDefault(),
            new DrawPower(){{
                emptyLightColor = SaltBattery.this.emptyLightColor;
                fullLightColor = SaltBattery.this.fullLightColor;
        }}, new DrawRegion("-top"));
    }

    public class SaltBatteryBuild extends BatteryBuild {
        public float heat;
        public float saltAccumulate;

        @Override
        public void updateTile(){
            boolean hasEnoughLiquid = liquids.currentAmount() >= minLiquidLevel;
            
            if(hasEnoughLiquid){
                
                if (items.total() < itemCapacity && power.status > 0){
                    saltAccumulate += saltProduction * delta();

                    if (saltAccumulate >= 1f){
                        int saltToAdd = (int) saltAccumulate;
                        items.add(salt, saltToAdd);
                        saltAccumulate -= saltToAdd;
                    }
                }

                if (items.total() >= itemCapacity) {
                    heat += heating * Math.min(delta(), 4f);
                } else {
                    heat = Math.max(0f, heat - (0.5f * delta()));
                }

            } else {
                power.status = Math.max(0f, power.status - (powerDrainRate * delta()));
                heat = Math.max(0f, heat - (0.5f * delta()));
            }

            heat = Mathf.clamp(heat);

            if (heat >= 1f) {
                Events.fire(Trigger.thoriumReactorOverheat);
                kill();
            }
            
            if(timer(timerDump, dumpTime / timeScale)){
                dump(salt);
            }

            super.updateTile();
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return false;
        }
        
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return liquid == DustLiquids.saltWater;
        }

        @Override
        public void draw(){
            super.draw();

            Draw.color(coolColor, hotColor, heat);
            Fill.rect(x, y, size * tilesize, size * tilesize);
        }
    }

    @Override
    public void setBars(){
        super.setBars();
        
        // remove the liquid bar
        removeBar("liquid-minedusty-liquid-salt-water");

        addBar("required-liquid", (SaltBatteryBuild entity)  ->
            new Bar(
                () -> {
                    //float percentage = (entity.liquids.currentAmount() / minLiquidLevel) * 100f;
                    //String color = percentage >= 100f ? "[accent]" : "[scarlet]";
                    // "Salt Water: " + color + String.format("%.0f", percentage) + "%[]";

                    String color = entity.liquids.currentAmount() >= minLiquidLevel ? "[accent]" : "[scarlet]";
                    return "Salt Water: " + color + (int)entity.liquids.currentAmount() + "[] / " + (int)minLiquidLevel;
                },
                () -> entity.liquids.currentAmount() >= minLiquidLevel ? Pal.lightOrange : Color.scarlet
                // {
                //     if (entity.liquids.currentAmount() < minLiquidLevel) {
                //         return Color.scarlet;
                //     } else { //if (entity.liquids.currentAmount() < minLiquidLevel * 1.5f) 
                //         return Pal.lightOrange;
                //     }
                // }
                ,
                () -> Math.min(entity.liquids.currentAmount() / minLiquidLevel, 1f)
            ));
    }
}
