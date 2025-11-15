package minedusty.blocks;

import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import minedusty.graphics.*;
import minedusty.world.blocks.power.*;

import static mindustry.type.ItemStack.*;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustLiquids.*;

public class DustPower {
     // I'VE GOT THE POWER!
    public static Block saltBattery, largesaltBattery;
    public static Block solarPanel, largeSolarPanel;
    public static Block geothermalGenerator, largegeothermalGenerator;
    public static Block carbonicCombustor;

    // Power related blocks
    public static Block powerPylon, powerTower, powerHub;

    public static void loadContent() {
        powerPylon = new BuoyPylon("power-pylon", 3){{
            requirements(Category.power, with(oxidecopper, 3, Items.lead, 5));
            researchCost = with(oxidecopper, 20, Items.lead, 50);
            laserScale = 0.2f;
            underBullets = true;
        }};

        powerHub = new PowerPylon("power-hub", 12){{
            requirements(Category.power, with(oxidecopper, 50, Items.silicon, 100, aquamerium, 50));
            researchCost = with(oxidecopper, 150, Items.silicon, 200, salt, 150);
            buildTime = 5f * 60f;
            laserScale = 0.2f;
            laserRange = 12f;
            size = 2;
            consumesPower = true;
            outputsPower = true;
            consumePowerBuffered(6000);
            baseExplosiveness = 0.25f;
        }};

        // Need custom effect
        geothermalGenerator = new GeothermalGenerator("geothermal-generator"){{
            requirements(Category.power, with(oxidecopper, 55, Items.lead, 30, Items.silicon, 35, chlorophyte, 35));
            researchCost = with(oxidecopper, 400, Items.silicon, 600, chlorophyte, 200);
            powerProduction = 90f / 60f;
            size = 2;
            maxEfficiency = 2f;

            outputLiquid = new LiquidStack(Liquids.slag, 8f / 60f);

            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawHeatEfficiency("-heat"),
                new DrawSpinEfficiency("-rotator"){{
                    baseSpeed = 3.5f;
                }}
            );
        }};

        largegeothermalGenerator = new GeothermalGenerator("large-geothermal-generator"){{
            requirements(Category.power, with(aquamerium, 70, Items.lead, 70, Items.silicon, 50, chlorophyte, 35));
            researchCost = with(Items.lead, 800, Items.silicon, 1000, aquamerium, 500);
            powerProduction = 230f / 60f;
            maxEfficiency = 2.5f;
            size = 3;

            outputLiquid = new LiquidStack(Liquids.slag, 24f / 60f);

            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawSpinEfficiency("-rotator"){{
                    baseSpeed = 2.5f;
                }},
                new DrawRegion("-top"),
                new DrawHeatEfficiency("-heat"){{
                    pulse = 0.4f;
                }}
            );
        }};

        carbonicCombustor = new ConsumeGenerator("carbonic-combustor"){{
            requirements(Category.power, with(oxidecopper, 55, Items.lead, 15));
            researchCost = with(Items.lead, 400, oxidecopper, 150);
            powerProduction = 75/60f;
            itemDuration = 155f;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.03f;
            generateEffect = Fx.generatespark;

            consume(new ConsumeItemFlammable(1.0f));

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
        }};

        solarPanel = new SolarGenerator("solar-panel"){{
            requirements(Category.power, with(Items.lead, 30, Items.silicon, 45));
            researchCost = with(Items.lead, 200, Items.silicon, 100);
            size = 2;
            powerProduction = 30f/60f;
        }};

        largeSolarPanel = new SolarGenerator("large-solar-panel"){{
            requirements(Category.power, with(Items.lead, 30, Items.silicon, 100, salt, 20, aquamerium, 35));
            researchCost = with(Items.lead, 2300, Items.silicon, 800, salt, 200);
            size = 3;
            powerProduction = 110f/60f;
        }};

        saltBattery = new SaltBattery("salt-battery"){{
            requirements(Category.power, with(oxidecopper, 12, Items.lead, 35));
            researchCost = with(Items.lead, 300, oxidecopper, 200);
            size = 2;

            consumeLiquid(saltWater, liquidConsume);
            consumePowerBuffered(16000);
            baseExplosiveness = 0.25f;
        }};

        largesaltBattery = new SaltBattery("salt-battery-large"){{
            requirements(Category.power, with(aquamerium, 15, Items.lead, 65, Items.silicon, 20, salt, 45));
            researchCost = with(Items.lead, 1200, salt, 200, aquamerium, 150);
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
