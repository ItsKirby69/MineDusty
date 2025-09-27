package minedusty.blocks;

import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.power.SolarGenerator;
import mindustry.world.blocks.power.ThermalGenerator;
import minedusty.world.blocks.power.SaltBattery;

import static mindustry.type.ItemStack.*;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustLiquids.*;

public class DustPower {
    
    public static Block saltBattery, largesaltBattery;
    public static Block solarPanel, largeSolarPanel;
    public static Block geothermalGenerator;

    public static void loadContent() {
        // Need custom effect
        geothermalGenerator = new ThermalGenerator("geothermal-generator"){{
            requirements(Category.power, with(oxidecopper, 55, Items.lead, 30, Items.silicon, 35, aquamerium, 35));
            powerProduction = 1.75f;
            generateEffect = Fx.redgeneratespark;
            effectChance = 0.015f;
            size = 2;
            floating = true;

            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
        }};

        solarPanel = new SolarGenerator("solar-panel"){{
            requirements(Category.power, with(Items.lead, 30, Items.silicon, 35, salt, 15));
            size = 2;
            powerProduction = 30f/60f;
        }};

        largeSolarPanel = new SolarGenerator("large-solar-panel"){{
            requirements(Category.power, with(Items.lead, 50, Items.silicon, 75, salt, 30, aquamerium, 15));
            size = 3;
            powerProduction = 100f/60f;
        }};

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
