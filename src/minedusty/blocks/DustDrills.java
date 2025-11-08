package minedusty.blocks;

import mindustry.content.*;
import mindustry.type.Category;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.Env;
import minedusty.content.*;
import minedusty.world.blocks.production.*;

import static mindustry.type.ItemStack.*;
import static minedusty.content.DustItems.*;

public class DustDrills {
    public static Block offshoreDrill, copperDrill, chloroDrill, newdrill, lobePump;

    public static Block crystalBore, gyratoryDrill;

    public static void loadContent(){
        lobePump = new Pump("lobe-pump") {{
            requirements(Category.liquid, with(aquamerium, 20, Items.silicon, 50, oxidecopper, 35));
            consumePower(20f/60f);
            researchCost = with(aquamerium, 200, Items.silicon, 150);
            pumpAmount = 8f / 60f;
            liquidCapacity = 60f;
            squareSprite = false;
            size = 2;
            hasPower = true;
        }};

		crystalBore = new BeamDrill("crystal-bore"){{
            requirements(Category.production, with(oxidecopper, 40));
            consumePower(0.15f);

			itemCapacity = 20;
            drillTime = 140f;
            tier = 3;
            size = 2;
            range = 4;

            consumeLiquid(Liquids.water, 0.25f / 60f).boost();
        }};

        copperDrill = new ModifiedDrill("copper-drill"){{
            requirements(Category.production, with(DustItems.oxidecopper, 15));
            alwaysUnlocked = true;
            tier = 2;
            drillTime = 700;
            envEnabled ^= Env.space;

            consumeLiquid(Liquids.water, 0.04f).boost();
        }};

        chloroDrill = new ModifiedDrill("chloro-drill"){{
            requirements(Category.production, with(Items.lead, 12, DustItems.chlorophyte, 10));
            researchCost = with(oxidecopper, 400, chlorophyte, 50);
            tier = 3;
            drillTime = 500;
            drawTopUnder = true;

            consumeLiquid(DustLiquids.bioLiquid, 0.06f).boost();
        }};

        offshoreDrill = new OffshoreDrill("offshore-drill"){{
            requirements(Category.production, with(DustItems.chlorophyte, 30, DustItems.oxidecopper, 25, Items.lead, 30));
            researchCost = with(oxidecopper, 400, chlorophyte, 150);
            tier = 3;
            drillTime = 700;

            liquidBoostIntensity = 1.25f;
            consumeLiquid(DustLiquids.bioLiquid, 0.06f).boost();
        }};
    }
}
