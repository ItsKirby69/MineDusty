package minedusty.blocks;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import minedusty.content.DustItems;
import minedusty.world.blocks.defense.OxideWall;

import static mindustry.type.ItemStack.*;

import mindustry.content.Items;

public class DustDefence {
	public static Block aquaWall, aquaWallLarge, oxidecopperWall, oxidecopperWallLarge;

	public static void loadContent() {
		int wallHealthMulti = 4;

		aquaWall = new Wall("aquamerium-wall") {{
			requirements(Category.defense, with(DustItems.aquamerium, 6));
			health = 90 * wallHealthMulti;
		}};

        aquaWallLarge = new Wall("aquamerium-wall-large"){{
            requirements(Category.defense, ItemStack.mult(aquaWall.requirements, 4));
            health = 90 * 4 * wallHealthMulti;
            size = 2;
        }};
		
		oxidecopperWall = new OxideWall("oxide-copper-wall"){{
			requirements(Category.defense, with(DustItems.oxidecopper, 5));
			variants = 3;
			health = 75 * wallHealthMulti;
		}};

        oxidecopperWallLarge = new OxideWall("oxide-copper-wall-large"){{
            requirements(Category.defense, ItemStack.mult(oxidecopperWall.requirements, 4));
            health = 75 * 4 * wallHealthMulti;
			variants = 2;
            size = 2;
        }};
	}
}
