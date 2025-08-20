package minedusty.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.Conveyor;
import minedusty.content.DustItems;

public class DustDistribution {
	
	public static Block copperConveyor;

	public static void loadContent(){
        copperConveyor = new Conveyor("oxide-copper-conveyor"){{
            requirements(Category.distribution, with(DustItems.oxidecopper, 1));
            health = 35;
            speed = 0.035f;
            displayedSpeed = 4.5f;
            buildCostMultiplier = 2f;
            researchCost = with(DustItems.oxidecopper, 5);
        }};
	}
}
