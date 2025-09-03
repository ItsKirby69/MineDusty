package minedusty.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.content.*;

public class DustCore {
	public static Block coreNest;

	public static void loadContent() {
		coreNest = new CoreBlock("core-nest"){{
			requirements(Category.effect, with(Items.copper, 1300, Items.lead, 1000));
			alwaysUnlocked = true;

			isFirstTier = true;
			unitType = UnitTypes.alpha;//DustUnitTypes.cricket; temporarily alpha
			health = 1600;
			itemCapacity = 5500;
			size = 3;
			buildCostMultiplier = 1.5f;

			unitCapModifier = 10;
			//armor = 5f;
		}};

	}
}
