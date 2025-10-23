package minedusty.blocks;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import minedusty.content.DustItems;
import minedusty.world.blocks.defense.*;

import static mindustry.content.Items.silicon;
import static mindustry.type.ItemStack.*;

public class DustDefence {
	public static Block chloroWall, chloroWallLarge, aquaWall, aquaWallLarge, aquawallHuge, oxidecopperWall, oxidecopperWallLarge;

	public static void loadContent() {
		int wallHealthMulti = 4;

		chloroWall = new HealWall("chlorophyte-wall") {{
			requirements(Category.defense, with(DustItems.chlorophyte, 6));
			health = 80 * wallHealthMulti;
		}};

        chloroWallLarge = new HealWall("chlorophyte-wall-large"){{
            requirements(Category.defense, ItemStack.mult(chloroWall.requirements, 4));
            health = 80 * 4 * wallHealthMulti;
            size = 2;
        }};

		aquaWall = new Wall("aquamerium-wall") {{
			requirements(Category.defense, with(DustItems.aquamerium, 6));
			health = 130 * wallHealthMulti;
		}};

        aquaWallLarge = new Wall("aquamerium-wall-large"){{
            requirements(Category.defense, ItemStack.mult(aquaWall.requirements, 4));
            health = 130 * 4 * wallHealthMulti;
            size = 2;
        }};

        aquawallHuge = new Wall("aquamerium-wall-huge"){{
            requirements(Category.defense, with(DustItems.aquamerium, 54, silicon, 24));
            health = 150 * 9 * wallHealthMulti;
            size = 3;
            buildCostMultiplier = 4f;
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
