package minedusty.blocks;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import minedusty.content.DustItems;

import static mindustry.type.ItemStack.*;

public class DustDefence {
	public static Block aquaWall;

	public static void loadContent() {
		int wallHealthMulti = 4;

		aquaWall = new Wall("aquamirae-wall") {{
			requirements(Category.defense, with(DustItems.aquamarine, 6));
			health = 80 * wallHealthMulti;
		}};
	}
}
