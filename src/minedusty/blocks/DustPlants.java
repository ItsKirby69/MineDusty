package minedusty.blocks;

import static arc.graphics.g2d.Draw.color;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;

import minedusty.world.blocks.environment.*;
import minedusty.content.DustItems;
import minedusty.content.DustyEffects;
import minedusty.utils.EffectHelper;

public class DustPlants {
	// Trees
	public static Block largebogTree, worldTree, divineTree, aliveTree, blossomTree, elmTree, pineTree, bogTree, cheeseTree, burntTree, ashTree, deadTree, mossydeadTree;
	//TODO mahogany spruceTree, mysticTree, frozenTree, glowberryTree, coconutTree?

	//TODO night plants that bloom light in nighttime.
	// Shrubs and small Plants (props)
	public static Block nightBush;
	public static Block grassBunch, shrub, sandyshrub, dustyshrub, tallGrass, fernBush, bogRoots;
	public static Block cactus, cattail, lilypad, largelilypad, marshlilypad, largemarshlilypad;

	public static void loadContent() {
		//region Trees
		/* Disabled for now since atlas is messed up
		worldTree = new LivingTreeBlock("world-tree", 1){{
			mapColor = Color.valueOf("c32121");
			breakEffect = DustyEffects.treeBreakWhite;
			destroyEffect = DustyEffects.withColor(DustyEffects.treeBreak, mapColor);
			size = 12;
			shadowOffset = -30f;
			tallTree = true;
			clipSize = 1536; //512*3 | 1536
			fadeStart = 230f;
			fadeEnd = 100f;
			effectRange = 18f;
		}};*/

		divineTree = new LivingTreeBlock("divine-tree", 2){{
			itemDrop = DustItems.divinityMatter; //for extraction in future
			mapColor = Color.valueOf("c32121");
			breakEffect = DustyEffects.treeBreakWhite;
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreakWhite, mapColor);
		}};
		aliveTree = new LivingTreeBlock("alive-tree", 2){{
			mapColor = Color.valueOf("74d660");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			size = 3;
		}};
		blossomTree = new LivingTreeBlock("blossom-tree", 1){{
			mapColor = Color.valueOf("f3b9c3");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			size = 3;
		}};
		elmTree = new LivingTreeBlock("elm-tree", 1){{
			mapColor = Color.valueOf("ECB01E");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			size = 3;
		}};
		pineTree = new LivingTreeBlock("pine-tree", 1){{
			mapColor = Color.valueOf("356a41");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			rotateShadow = false;
			size = 3;
		}};
		bogTree = new LivingTreeBlock("bog-tree", 1){{
			mapColor = Color.valueOf("667113");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			size = 3;
		}};
		largebogTree = new LivingTreeBlock("large-bog-tree", 1) {{
			mapColor = Color.valueOf("667113");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			fadeEnd = 30f;
			baseLayer = Layer.legUnit + 3.5f;
			size = 5;
		}};
		cheeseTree = new LivingTreeBlock("cheese-tree", 1){{
			mapColor = Color.valueOf("d7d177");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
		}};

		//dead/static trees (trees with no layers)
		burntTree = new TreeBlock("burnt-tree"){{
			mapColor = Color.valueOf("172025");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			buildVisibility = BuildVisibility.sandboxOnly;
			shadowOffset = -1f;
		}};
		ashTree = new TreeBlock("ash-tree"){{
			mapColor = Color.valueOf("98a3a8");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			buildVisibility = BuildVisibility.sandboxOnly;
			shadowOffset = -1f;
		}};
		deadTree = new TreeBlock("dead-tree"){{
			mapColor = Color.valueOf("744700");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			buildVisibility = BuildVisibility.sandboxOnly;
			variants = 2;
		}};
		mossydeadTree = new TreeBlock("mossydead-tree"){{
			mapColor = Color.valueOf("744700");
			destroyEffect = EffectHelper.withColor(DustyEffects.treeBreak, mapColor);
			buildVisibility = BuildVisibility.sandboxOnly;
			variants = 2;
		}};

		//maybe i don't need a coconut tree
		/*coconutTree = new LivingBush("coconut-tree"){{
			mapColor = Color.valueOf("000000");
			lobesMin = 3;
			lobesMax = 3;
			magMin = 4;
			magMax = 7;
			sclMin = 30f;
			sclMax = 60f;
			solid = true;
			clipSize = 90f;
		}};*/

		//end region

		//region Props
		//TODO need to work more with the decoration feature maybe?

		shrub = new LivingBush("shrub"){{
			mapColor = Color.valueOf("74d660");
			Blocks.grass.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
		}};
		
		sandyshrub = new LivingBush("sandy-shrub"){{
			mapColor = Color.valueOf("f7cba4");
			Blocks.sand.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
		}};

		dustyshrub = new LivingBush("dusty-shrub"){{
			mapColor = Color.valueOf("f7cba4");
			Blocks.stone.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
		}};

		//WIP. These suck so bad o_o
		/* 
		tallGrass = new LivingBush("tallgrass", 2){{
			mapColor = Color.valueOf("87d661");
			lobesMin = 4;
			lobesMax = 8;
			magMin = 4;
			magMax = 9;
			rot = 0;
		}};
		grassBunch = new LivingBush("grass-bunch"){{
			variants = 1;
			mapColor = Color.valueOf("74d660");
			Blocks.grass.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
			lobesMin = 8;
			lobesMax = 12;
			magMin = 2;
			magMax = 10;
		}};
		*/
		nightBush = new NightBush("night-bush"){{
			
		}};
		fernBush = new LivingBush("fern-bush", 1){{
			dualCircleMode = true;
			mapColor = Color.valueOf("356a41");
			rot = 0;
			lobesMin = 6;
			lobesMax = 8;
			magMin = 2;
			magMax = 4;
		}};
		
		bogRoots = new Block("bog-roots"){{
			variants = 3;
			rotate = true;
			update = true;
			mapColor = Color.valueOf("667113");
			buildVisibility = BuildVisibility.sandboxOnly;
			destructible = true;
			targetable = false;
			hasShadow = false;
			underBullets = true;

			breakSound = Sounds.plantBreak;
			breakEffect = Fx.breakProp;
			instantDeconstruct = true;			
		}};

		cactus = new LivingProp("cactus"){{
			mapColor = Color.valueOf("d7d177");
			variants = 2;
			rotate = false;
			breakable = true;
			rareChance = 0.2f;
		}};

		cattail = new LivingBush("cattail", 2){{
			rare = true;
			mapColor = Color.valueOf("74d660");
			lobesMin = 7;
			lobesMax = 10;
			magMin = 3;
			magMax = 7;
			sclMin = 20f;
			sclMax = 60f;
		}};

		lilypad = new LivingProp("lily-pad", 3){{
			mapColor = Color.valueOf("97c51e");
			shadowOffset = -4f;
		}};
		largelilypad = new LivingProp("large-lily-pad", 3){{
			mapColor = Color.valueOf("97c51e");
			shadowOffset = -3f;
			propOffset = 4f;
			size = 2;
		}};

		marshlilypad = new LivingProp("marsh-lily-pad", 2){{
			mapColor = Color.valueOf("7b990b");
			shadowOffset = -3f;
			propOffset = 4f;
			size = 2;
		}};

		largemarshlilypad = new LivingProp("large-marsh-lily-pad", 1){{
			mapColor = Color.valueOf("7b990b");
			shadowOffset = -4f;
			size = 3;
		}};
		//end region
	}
}
