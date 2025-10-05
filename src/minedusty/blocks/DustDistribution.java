package minedusty.blocks;

import static mindustry.content.Items.lead;
import static mindustry.type.ItemStack.with;
import static minedusty.content.DustItems.aquamerium;
import static minedusty.content.DustItems.oxidecopper;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.blocks.distribution.OverflowGate;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.distribution.Sorter;
import minedusty.content.DustItems;

public class DustDistribution {
	
	public static Block copperConveyor, copperJunction, copperRouter, 
    copperOverflowGate, copperUnderflowGate,
    copperSorter, copperInvertedSorter;
    
    public static Block aquameriumConveyor;
    public static Block armoredGalenaConveyor; // a heavily armored conveyor. Perhaps instead make it chlorophyte that heals itself?
    public static Block electrumConveyor; // A power requiring stack conveyor?

	public static void loadContent(){
        copperConveyor = new Conveyor("oxide-copper-conveyor"){{
            requirements(Category.distribution, with(DustItems.oxidecopper, 1));
            health = 35;
            speed = 0.04f;
            displayedSpeed = 5.5f;
            buildCostMultiplier = 2f;
            researchCost = with(DustItems.oxidecopper, 5);
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
	}
}
