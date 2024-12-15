package minedusty.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import minedusty.content.DustUnitTypes;

public class DustCore {
	public static Block coreNest;

	public static void loadContent() {
		coreNest = new CoreBlock("core-nest"){{
			requirements(Category.effect, with(Items.copper, 5, Items.lead, 8));
			alwaysUnlocked = true;
			isFirstTier = true;
			unitType = DustUnitTypes.cricket;
			health = 1600;
			itemCapacity = 2500;
			size = 3;
			unitCapModifier = 10;
			//armor = 5f;
		}};

	}
}
