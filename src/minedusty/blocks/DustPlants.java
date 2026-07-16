package minedusty.blocks;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;

import minedusty.world.blocks.environment.*;
import minedusty.DustAttributes;
import minedusty.content.*;
import minedusty.utils.EffectHelper;

import static minedusty.utils.EffectHelper.*;

public class DustPlants {
	// Trees
	public static Block largealiveTree, largebogTree, worldTree, divineTree, aliveTree, blossomTree, elmTree, pineTree, bogTree, cheeseTree, burntTree, ashTree, deadTree, mossydeadTree;
	//TODO mahogany spruceTree, mysticTree, frozenTree, glowberryTree, coconutTree?

	//TODO night plants that bloom light in nighttime.
	// Shrubs and small Plants (props)
	public static Block nightBush;
	public static Block aloeVera, deadShrub, clayshrub, grassBunch, shrub, sandyshrub, dustyshrub, tallGrass, fernBush, bogRoots;
	public static Block monstera, fireLily;
	public static Block cactus, cattail, lilypad, largelilypad, marshlilypad, largemarshlilypad;

	public static void loadContent() {
		//region Trees
		/* Disabled for now since atlas is messed up
		worldTree = new LivingTreeBlock("world-tree", 1, "#c32121"){{
			treeBreakEffect = colorEffect(treeBreakEffect(200f, 150, 5, "minedusty-tree-prop-white", 2.2f, 60f, Layer.debris), mapColor);
			size = 12;
			shadowOffset = -30f;
			tallTree = true;
			clipSize = 1536; //512*3 | 1536
			fadeStart = 230f;
			fadeEnd = 100f;
			effectRange = 18f;
		}};*/

		divineTree = new LivingTreeBlock("divine-tree", 2, "#c32121"){{
			itemDrop = DustItems.divinityMatter; //for extraction in future
			treeBreakEffect = colorEffect(treeBreakEffect(120f, 45, 4, "minedusty-white-prop", 3.2f, 23f, Layer.debris), mapColor);
			stumpBreakEffect = stumpBreakEffect(90f, 15, 2, "minedusty-white-bark", 3f, 7f);
		}};
		aliveTree = new LivingTreeBlock("alive-tree", 2, "#74d660");
		largealiveTree = new LivingTreeBlock("large-alive-tree", 1, "#74d660"){{
			treeBreakEffect = colorEffect(treeBreakEffect(150f, 100, 5, "minedusty-tree-prop", 2.6f, 39f, Layer.blockOver), mapColor);
			stumpBreakEffect = stumpBreakEffect(110f, 30, 2, "minedusty-tree-bark", 3f, 12f);
			fadeStart *= 2;
			fadeEnd *= 2;
			baseLayer = Layer.legUnit + 3.5f;
			size = 5;
		}};
		blossomTree = new LivingTreeBlock("blossom-tree", 1, "#df7a9c"){{
			stumpBreakEffect = stumpBreakEffect(90f, 15, 2, "minedusty-blossom-bark", 3f, 7f);
		}};
		elmTree = new LivingTreeBlock("elm-tree", 1, "#ECB01E"){{
			stumpBreakEffect = stumpBreakEffect(90f, 15, 2, "minedusty-elm-bark", 3f, 7f);
		}};
		pineTree = new LivingTreeBlock("pine-tree", 1, "#398654"){{
			effect = EffectHelper.fallingLeaves("pine-prop3");
			treeBreakEffect = colorEffect(treeBreakEffect(120f, 45, 5, "minedusty-pine-prop", 3.2f, 23f, Layer.blockOver), mapColor);
			stumpBreakEffect = stumpBreakEffect(110f, 15, 2, "minedusty-pine-bark", 3f, 9f);
			rotateShadow = false;
		}};
		bogTree = new LivingTreeBlock("bog-tree", 1, "#6d922b"){{
			requiresWater = true;
			treeBreakEffect = colorEffect(treeBreakEffect(150f, 80, 5, "minedusty-tree-prop", 3f, 26f, Layer.blockOver), mapColor);
			stumpBreakEffect = stumpBreakEffect(100f, 15, 2, "minedusty-bog-bark", 3f, 8f);
		}};
		largebogTree = new LivingTreeBlock("large-bog-tree", 1, "#6d922b") {{
			requiresWater = true;
			treeBreakEffect = colorEffect(treeBreakEffect(150f, 100, 5, "minedusty-tree-prop", 2.6f, 39f, Layer.blockOver), mapColor);
			stumpBreakEffect = stumpBreakEffect(100f, 30, 2, "minedusty-bog-bark", 3f, 11f);
			fadeStart *= 2;
			fadeEnd *= 2;
			baseLayer = Layer.legUnit + 3.5f;
			size = 5;
		}};
		cheeseTree = new LivingTreeBlock("cheese-tree", 1, "#d7d177"){{
			treeBreakEffect = colorEffect(treeBreakEffect(120f, 45, 4, "minedusty-tree-prop", 3.2f, 23f, Layer.blockOver), mapColor);
			stumpBreakEffect = Fx.none;
		}};

		//dead/static trees (trees with no layers)
		burntTree = new TreeBlockEffect("burnt-tree"){{
			mapColor = Color.valueOf("#172025");
			effect = DustyEffects.fallingEmbers;
			lightRadius = 50f;
			lightColor = Color.valueOf("#ffd754dd");
		}};
		ashTree = new TreeBlockEffect("ash-tree"){{
			mapColor = Color.valueOf("#98a3a8");
			effect = DustyEffects.fallingEmbers;
			lightRadius = 50f;
			lightColor = Color.valueOf("#ffd754dd");
		}};
		deadTree = new TreeBlock("dead-tree"){{
			mapColor = Color.valueOf("#744700");
			buildVisibility = BuildVisibility.sandboxOnly;
			variants = 2;
		}};
		mossydeadTree = new TreeBlock("mossydead-tree"){{
			mapColor = Color.valueOf("#744700");
			buildVisibility = BuildVisibility.sandboxOnly;
			variants = 2;
		}};

		//maybe i don't need a coconut tree
		/*coconutTree = new LivingBush("coconut-tree"){{
			mapColor = Color.valueOf("#000000");
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

		shrub = new LivingBush("shrub"){{
			mapColor = Color.valueOf("#74d660");
			Blocks.grass.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
		}};
		
		sandyshrub = new LivingBush("sandy-shrub"){{
			mapColor = Color.valueOf("#f7cba4");
			Blocks.sand.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
			lobesMin = 3;
			lobesMax = 5;
		}};

		dustyshrub = new LivingBush("dusty-shrub"){{
			mapColor = Color.valueOf("#f7cba4");
			DustEnv.basaltSands.asFloor().decoration = this;
		}};

		clayshrub = new LivingBush("clay-shrub"){{
			mapColor = Color.valueOf("#925e46");
			DustEnv.clayFloor.asFloor().decoration = this;
			lobesMin = 3;
			lobesMax = 5;
			variants = 1;
		}};

		deadShrub = new LivingBush("dead-shrub", 1){{
			mapColor = Color.valueOf("#796961");
			DustEnv.clayFloor.asFloor().decoration = this;
			lobesMax = 6;
		}};

		aloeVera = new LivingBush("aloevera", 1){{
			mapColor = Color.valueOf("#308e51");
			lobesMax = 7;
		}};

		//WIP. These suck so bad o_o
		/* 
		tallGrass = new LivingBush("tallgrass", 2){{
			mapColor = Color.valueOf("#87d661");
			lobesMin = 4;
			lobesMax = 8;
			magMin = 4;
			magMax = 9;
			rot = 0;
		}};
		grassBunch = new LivingBush("grass-bunch"){{
			variants = 1;
			mapColor = Color.valueOf("#74d660");
			Blocks.grass.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
			lobesMin = 8;
			lobesMax = 12;
			magMin = 2;
			magMax = 10;
		}};
		*/

		monstera = new LivingBush("monstera", 1){{
			layer = Layer.blockProp + 2f;
			clipSize = 20;
			dualCircleMode = true;
			mapColor = Color.valueOf("#4c864c");
			lobesMin = 5;
			lobesMax = 7;
			oddlobes = true;
			magMin = 3;
			magMax = 7;
			sclMin = 30f;
			sclMax = 50f;
		}};

		nightBush = new NightBush("night-bush"){{
			
		}};
		fernBush = new LivingBush("fern-bush", 1){{
			dualCircleMode = true;
			shadowAlpha = 0.7f;
			mapColor = Color.valueOf("#356a41");
			rot = 0;
			lobesMin = 3;
			lobesMax = 7;
			magMin = 2;
			magMax = 4;
		}};
	
		fireLily = new LivingBush("fire-lily", 1){{
			dualCircleMode = true;
			shadowAlpha = 0.7f;
			mapColor = Color.valueOf("#d44e31");
			rot = 0;
			lobesMin = 2;
			lobesMax = 3;
			magMin = 2;
			magMax = 4;
			attributes.set(DustAttributes.thermalPower, 2);
			emitLight = true;
			lightRadius = 25f;
			lightColor = Color.valueOf("#ffd7547c");
		}};
		
		bogRoots = new Block("bog-roots"){{
			variants = 3;
			rotate = true;
			update = true;
			mapColor = Color.valueOf("#667113");
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
			mapColor = Color.valueOf("#d7d177");
			variants = 2;
			rotate = false;
			breakable = true;
			rareChance = 0.2f;
		}};

		cattail = new LivingBush("cattail", 2){{
			placeableLiquid = true;
			rare = true;
			mapColor = Color.valueOf("#74d660");
			lobesMin = 7;
			lobesMax = 10;
			magMin = 3;
			magMax = 7;
			sclMin = 20f;
			sclMax = 60f;
		}};

		lilypad = new LivingProp("lily-pad", 3){{
			placeableLiquid = true;
			mapColor = Color.valueOf("#97c51e");
			shadowOffset = -4f;
		}};
		largelilypad = new LivingProp("large-lily-pad", 3){{
			placeableLiquid = true;
			mapColor = Color.valueOf("#97c51e");
			shadowOffset = -1f;
			propOffset = 4f;
			size = 2;
		}};

		marshlilypad = new LivingProp("marsh-lily-pad", 3){{
			placeableLiquid = true;
			mapColor = Color.valueOf("#7b990b");
			shadowOffset = -1f;
			propOffset = 4f;
			size = 2;
		}};

		largemarshlilypad = new LivingProp("large-marsh-lily-pad", 1){{
			placeableLiquid = true;
			mapColor = Color.valueOf("#7b990b");
			shadowOffset = -4f;
			size = 3;
			rareChance = 0.4f;
		}};
		//end region
	}
}
