package minedusty.blocks;

import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.Env;
import minedusty.content.*;
import minedusty.world.blocks.liquid.SolarPump;
import minedusty.world.blocks.production.*;

import static mindustry.type.ItemStack.*;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustLiquids.*;

import arc.graphics.Color;

import static mindustry.content.Items.*;

public class DustDrills {
    public static Block offshoreDrill, copperDrill, chloroDrill, solarPump, lobePump;

	public static Block crystalCrusher;
    public static Block crystalBore, gyratoryDrill;

    public static void loadContent(){
        solarPump = new SolarPump("solar-pump"){{
            requirements(Category.liquid, with(graphite, 5, oxidecopper, 8));
            pumpAmount = 8f/60f;
            liquidCapacity = 25f;
            squareSprite = false;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidRegion(){{
                    suffix = "-pool";
                }},
                new DrawRegion()
            );
        }};

        lobePump = new Pump("lobe-pump"){{
            requirements(Category.liquid, with(aquamerium, 8, Items.silicon, 20, oxidecopper, 15));
            consumePower(18f/60f);
            researchCost = with(aquamerium, 200, Items.silicon, 150);
            pumpAmount = 10f / 60f;
            liquidCapacity = 85f;
            squareSprite = false;
            size = 2;
            hasPower = true;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidRegion(){{
                    suffix = "-pool";
                }},
                new DrawRegion("-rotator"){{
                    spinSprite = true;
                    rotateSpeed = 1f;
                }},
                new DrawDefault(),
                new DrawPumpLiquid()
            );
        }};

        crystalBore = new TierBeamDrill("crystal-bore"){{
            requirements(Category.production, with(Items.graphite, 15, Items.lead, 30));
            researchCost = with(oxidecopper, 350, Items.graphite, 100);
			consumePower(13 / 60f);
            consumeLiquid(bioLiquid, 0.25f/60).boost();

            drillTime = 160f;
            size = 2;
			health = 200;
            tier = 2;
            tierMap.put(silicadust, amethyst);

            sparkColor = Color.valueOf("#fd818e");
            boostHeatColor = Color.valueOf("#9df572").cpy().mul(0.87f);
            
            fogRadius = 2;
            ambientSound = Sounds.loopDrill;
            ambientSoundVolume = 0.04f;
        }};

        copperDrill = new ModifiedDrill("copper-drill"){{
            requirements(Category.production, with(oxidecopper, 15));
            alwaysUnlocked = true;
            tier = 2;
            drillTime = 700;
            envEnabled ^= Env.space;

            consumeLiquid(Liquids.water, 0.04f).boost();
        }};

        chloroDrill = new ModifiedDrill("chloro-drill"){{
            requirements(Category.production, with(lead, 12, chlorophyte, 6));
            researchCost = with(oxidecopper, 400, chlorophyte, 50);
            tier = 3;
            drillTime = 500;
            drawTopUnder = true;

            consumeLiquid(DustLiquids.bioLiquid, 0.06f).boost();
        }};

        offshoreDrill = new OffshoreDrill("offshore-drill"){{
            requirements(Category.production, with(graphite, 30, oxidecopper, 25, lead, 30));
            researchCost = with(oxidecopper, 400, graphite, 150);
            tier = 3;
            drillTime = 700;

            liquidBoostIntensity = 1.25f;
            consumeLiquid(DustLiquids.bioLiquid, 0.06f).boost();
        }};
    }
}
