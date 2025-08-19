package minedusty.blocks;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import minedusty.content.DustItems;

import static mindustry.type.ItemStack.*;

public class DustDefence {
	public static Block aquaWall, aquaWallLarge;

	public static void loadContent() {
		int wallHealthMulti = 4;

		aquaWall = new Wall("aquamerium-wall") {{
			requirements(Category.defense, with(DustItems.aquamerium, 6));
			health = 80 * wallHealthMulti;
		}};

        aquaWallLarge = new Wall("aquamerium-wall-large"){{
            requirements(Category.defense, ItemStack.mult(aquaWall.requirements, 4));
            health = 80 * 4 * wallHealthMulti;
            size = 2;
        }};
	}
}
