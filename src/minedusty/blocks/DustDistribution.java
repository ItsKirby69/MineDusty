package minedusty.blocks;

import static mindustry.content.Items.lead;
import static mindustry.type.ItemStack.with;
import static minedusty.content.DustItems.aquamerium;
import static minedusty.content.DustItems.oxidecopper;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.blocks.distribution.OverflowGate;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.distribution.Sorter;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidJunction;
import mindustry.world.blocks.liquid.LiquidRouter;
import minedusty.content.DustItems;

public class DustDistribution {
	
	public static Block copperConveyor, copperJunction, copperRouter, 
    copperOverflowGate, copperUnderflowGate,
    copperSorter, copperInvertedSorter;

    public static Block aquameriumConduit, aquaLiquidRouter, aquaLiquidJunction;
    
    public static Block aquameriumConveyor;
    public static Block armoredGalenaConveyor; // a heavily armored conveyor. Perhaps instead make it chlorophyte that heals itself?
    public static Block electrumConveyor; // A power requiring stack conveyor?

	public static void loadContent(){
        // region conduits
        aquaLiquidRouter = new LiquidRouter("aqua-liquid-router"){{
            requirements(Category.liquid, with(aquamerium, 2, Items.graphite, 4));
            liquidCapacity = 150f;
            underBullets = true;
            solid = false;
            explosivenessScale = flammabilityScale = 4f/20f;
        }};

        aquameriumConduit = new Conduit("aqua-conduit"){{
            requirements(Category.liquid, with(aquamerium, 1, Items.lead, 1));
            liquidCapacity = 30f;
            health = 60;
            explosivenessScale = flammabilityScale = 8.5f/20f;
            botColor = Color.white;
        }};

        aquaLiquidJunction = new LiquidJunction("aqua-liquid-junction"){{
            requirements(Category.liquid, with(aquamerium, 10, Items.graphite, 6));
            solid = false;

            ((Conduit) aquameriumConduit).junctionReplacement = this;
        }};
        //end region

        // region conveyor
        copperConveyor = new Conveyor("oxide-copper-conveyor"){{
            requirements(Category.distribution, with(DustItems.oxidecopper, 1));
            health = 35;
            speed = 0.04f;
            displayedSpeed = 5.5f;
            buildCostMultiplier = 2f;
            // researchCost = with(DustItems.oxidecopper, 5);
            alwaysUnlocked = true;
        }};

        aquameriumConveyor = new Conveyor("aquamerium-conveyor"){{
            requirements(Category.distribution, with(oxidecopper, 1, lead, 1, aquamerium, 1));
            health = 75;
            speed = 0.08f;
            displayedSpeed = 11f;
        }};

        copperJunction = new Junction("copper-junction"){{
            requirements(Category.distribution, with(oxidecopper, 4));
            speed = 26;
            capacity = 4;
            health = 50;
            buildCostMultiplier = 4.5f;

            ((Conveyor) copperConveyor).junctionReplacement = this;
            ((Conveyor) aquameriumConveyor).junctionReplacement = this;
        }};

        copperRouter = new Router("copper-router"){{
            requirements(Category.distribution, with(oxidecopper, 4));
            buildCostMultiplier = 3f;
            hasItems = true;
        }};

        copperSorter = new Sorter("copper-sorter"){{
            requirements(Category.distribution, with(Items.lead, 2, oxidecopper, 3));
            buildCostMultiplier = 2.5f;
        }};

        copperInvertedSorter = new Sorter("copper-inverted-sorter"){{
            requirements(Category.distribution, with(Items.lead, 2, oxidecopper, 3));
            buildCostMultiplier = 2.5f;
            invert = true;
        }};

        copperOverflowGate = new OverflowGate("copper-overflow-gate"){{
            requirements(Category.distribution, with(Items.lead, 3, oxidecopper, 4));
            buildCostMultiplier = 2.5f;
        }};

        copperUnderflowGate = new OverflowGate("copper-underflow-gate"){{
            requirements(Category.distribution, with(Items.lead, 3, oxidecopper, 4));
            buildCostMultiplier = 2.5f;
            invert = true;
        }};
        // end region
	}
}
