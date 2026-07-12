package minedusty.blocks;


import static mindustry.type.ItemStack.with;
import static mindustry.content.Items.*;
import static minedusty.content.DustItems.*;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidJunction;
import mindustry.world.blocks.liquid.LiquidRouter;
import minedusty.world.blocks.liquid.HotConduit;
import minedusty.world.blocks.liquid.HotLiquidRouter;

public class DustDistribution {
	
	public static Block copperConveyor, copperJunction, copperRouter, 
    copperOverflowGate, copperUnderflowGate,
    copperSorter, copperInvertedSorter,
    copperBridge;
    public static Block aquameriumConveyor;

    public static Block siliconPipe, siliconValve;
    public static Block aquameriumConduit, aquaLiquidRouter, aquaLiquidDistributor, aquaLiquidJunction;
    
    public static Block armoredGalenaConveyor; // a heavily armored conveyor. Perhaps instead make it chlorophyte that heals itself?
    public static Block electrumConveyor; // A power requiring stack conveyor?

	public static void loadContent(){
        // region conduits
        siliconValve = new HotLiquidRouter("silicon-valve"){{
            requirements(Category.liquid, with(silicon, 2, Items.graphite, 4));
            liquidCapacity = 60f;
            underBullets = true;
            solid = false;
            squareSprite = false;
            liquidPadding = 1f;
            explosivenessScale = flammabilityScale = 6f/20f;
        }};

        siliconPipe = new HotConduit("silicon-pipe"){{
            requirements(Category.liquid, with(silicon, 2));
            liquidCapacity = 15f;
            health = 35;
            explosivenessScale = flammabilityScale = 10f/20f;
            botColor = Color.white;
        }};

        aquaLiquidRouter = new LiquidRouter("aqua-liquid-router"){{
            requirements(Category.liquid, with(aquamerium, 2, Items.graphite, 4));
            liquidCapacity = 150f;
            underBullets = true;
            solid = false;
            squareSprite = false;
            liquidPadding = 1f;
            explosivenessScale = flammabilityScale = 4f/20f;
        }};

        aquaLiquidDistributor = new LiquidRouter("aqua-liquid-distributor"){{
            requirements(Category.liquid, with(aquamerium, 4, Items.graphite, 6));
            liquidCapacity = 170f * 4;
            underBullets = true;
            solid = false;
            size = 2;
            squareSprite = false;
            liquidPadding = 1f;
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
            squareSprite = true;

            ((Conduit) aquameriumConduit).junctionReplacement = this;
        }};
        //end region

        // region conveyor
        copperConveyor = new Conveyor("oxide-copper-conveyor"){{
            requirements(Category.distribution, with(oxidecopper, 1));
            health = 35;
            speed = 0.04f;
            displayedSpeed = 5.5f;
            buildCostMultiplier = 2f;
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

        copperBridge = new BufferedItemBridge("copper-bridge"){{
            requirements(Category.distribution, with(Items.lead, 6, oxidecopper, 12));
            fadeIn = moveArrows = false;
            range = 4;
            speed = 100f; //74f
            arrowSpacing = 6f;
            bufferCapacity = 14;
            crushFragile = true;
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
