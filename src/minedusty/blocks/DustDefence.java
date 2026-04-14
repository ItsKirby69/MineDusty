package minedusty.blocks;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import minedusty.content.DustSounds;
import minedusty.world.blocks.defense.*;

import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;
import static minedusty.content.DustItems.*;

public class DustDefence {
	public static Block oxidecopperWall, oxidecopperWallLarge,
        chloroWall, chloroWallLarge, 
        aquaWall, aquaWallLarge, aquawallHuge, 
        siliconWallLarge, siliconWallHuge,
        crystalWall, crystalWallLarge;

	public static void loadContent() {
		int wallHealthMulti = 4;

        crystalWall = new DustWall("crystal-wall"){{
            requirements(Category.defense, with(amethyst, 8));
            health = 170 * wallHealthMulti;
            chanceDeflect = 25f;
            deflectSound = DustSounds.brittle;
            variants = 2;
            schematicPriority = 10;
        }};
        
        crystalWallLarge = new DustWall("crystal-wall-large"){{
            requirements(Category.defense, ItemStack.mult(crystalWall.requirements, 4));
            health = 170 * 4 * wallHealthMulti;
            chanceDeflect = 35f;
            deflectSound = DustSounds.brittle;
            size = 2;
            variants = 2;
            schematicPriority = 10;
        }};

        siliconWallLarge = new DustWall("silicon-wall-large"){{
            requirements(Category.defense, with(silicon, 5, chlorophyte, 1));
            health = 150 * 4 * wallHealthMulti;
            absorbLasers = true;
            size = 2;
            schematicPriority = 10;
        }};

		chloroWall = new HealWall("chlorophyte-wall") {{
			requirements(Category.defense, with(DustItems.chlorophyte, 6));
			health = 90 * wallHealthMulti;
		}};

        chloroWallLarge = new HealWall("chlorophyte-wall-large"){{
            requirements(Category.defense, ItemStack.mult(chloroWall.requirements, 4));
            health = 90 * 4 * wallHealthMulti;
            size = 2;
        }};

		aquaWall = new DustWall("aquamerium-wall") {{
			requirements(Category.defense, with(DustItems.aquamerium, 6));
			health = 130 * wallHealthMulti;
		}};

        aquaWallLarge = new DustWall("aquamerium-wall-large"){{
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
