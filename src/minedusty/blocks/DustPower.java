package minedusty.blocks;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import minedusty.world.blocks.power.SaltBattery;

import static mindustry.type.ItemStack.*;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustLiquids.saltWater;

public class DustPower {
    
    public static Block saltBattery, largesaltBattery;

    public static void loadContent() {
        saltBattery = new SaltBattery("salt-battery"){{
            requirements(Category.power, with(oxidecopper, 12, Items.lead, 35));
            size = 2;

            consumeLiquid(saltWater, liquidConsume);
            consumePowerBuffered(16000);
            baseExplosiveness = 0.25f;
        }};

        largesaltBattery = new SaltBattery("salt-battery-large"){{
            requirements(Category.power, with(aquamerium, 15, Items.lead, 65, Items.silicon, 20));
            size = 3;
            liquidConsume = 1.5f/60f;
            saltProduction = 0.5f/60f;

            minLiquidLevel = 125f;
            itemCapacity = 50;
            liquidCapacity = 200;

            consumeLiquid(saltWater, liquidConsume);
            consumePowerBuffered(45000f);
            baseExplosiveness = 2f;
        }};
    }
}
