package minedusty.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawHeatOutput;
import mindustry.world.draw.DrawLiquidRegion;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import mindustry.world.meta.*;
import minedusty.world.blocks.environment.*;
import static mindustry.type.ItemStack.with;

//region Variables
public class DustBlocks {
	public static Block 
	testblock,

	scatterSilo, 

	/* Floors/Overlays */

	//liquid tiles
	tropicalWater, deeptropicalWater,
	trophotWater, deeptrophotWater,
	tropmagmaWater, deeptropmagmaWater,
	hotWater, magmaWater,
	sandytropicalWater, dacitetropicalWater, basalttropicalWater, shorestonetropicalWater, soapstonetropicalWater,
	basaltWater, shorestoneWater, soapstoneWater, //TODO: create water variants of these 
	
	algaeWater, deepalgaeWater,
	
	//dry tiles
	taigaGrass, taigaLeaves, blossomGrass, blossomLeaves, elmGrass, elmLeaves, 

	//haven't done yet
	grassyFloor, leafyFloor, leavesLeaves,

	basaltFloor, shoreRock, soapstoneFloor,

	/* Blocks */

	coreNest, grassyWall, quartzSmelter,
	//cool idea(s) // haven't made yet
	oxidizedcopperWall, grassyVent, rockyVent, nitroplastChamber, somethingReactor,
	
	//boulders
	largeBoulder,
	basaltPillar, largebasaltPillar,
	shorestoneBoulder, largeshorestoneBoulder,

	/* Trees */
	burntTree, aliveTree, ashTree, blossomTree, elmTree, deadTree, mossydeadTree, cheeseTree, pineTree,//tf is cheese tree???
	//haven't done yet
	spruceTree, mysticTree, coconutTree, frozenTree, vineTree, glowberryTree,

	/* Vegatation */
	shrub, sandyshrub,
	flower, bush, tallGrass, cactus,

	//water
	lilypad, largelilypad, cattail, aloeVera,  //aloeVera counts as water right? hah no TODO: needs to work well with desert biomes
	
	/* Resources (aka ores) */
	oreChlorophyte, oreAquamarine, oreQuartz;

	//end region

	public static void load(){

		//region Boulders

		largeBoulder = new Prop("large-boulder"){{
			hasShadow = true;
			mapColor = Color.valueOf("706f74");
			customShadow = true;
			variants = 2;
			buildVisibility = BuildVisibility.sandboxOnly;
		}};

		largeshorestoneBoulder = new Prop("large-shorestone"){{
			customShadow = true;
			variants = 2;
			mapColor = Color.valueOf("706f74");
			buildVisibility = BuildVisibility.sandboxOnly;
		}};
		shorestoneBoulder = new Prop("shorestone-boulder"){{
			variants = 2;
			buildVisibility = BuildVisibility.sandboxOnly;
		}};

		//why did i name it like this
		largebasaltPillar = new Prop("large-basaltder"){{
			mapColor = Color.valueOf("706f74");
			customShadow = true;
			variants = 2;
			buildVisibility = BuildVisibility.sandboxOnly;
		}};
		basaltPillar = new Prop("basalt-boulder"){{
			variants = 3;
			buildVisibility = BuildVisibility.sandboxOnly;
		}};

		//end region
		//region ores and resources

        oreChlorophyte = new OreBlock("ore-chlorophyte", DustItems.chlorophyte){{
            oreDefault = false;
			variants = 3;

			emitLight = true;
            //lightRadius = 70f;
            //lightColor = Color.acid.cpy().a(0.3f);
        }};

		//this should be underwater. aka wavy. Name ideas, kind of like prismarine almost. Phantomite, Phacite, Prismite, Prismate?
		oreAquamarine = new OreBlock("ore-aquamarine", DustItems.aquamarine){{
			oreDefault = false;
			//attributes.set(Attribute.sand, 0.5f);
			variants = 3;

			emitLight = true;
            //lightRadius = 70f;
            //lightColor = Color.cyan.cpy().a(0.3f);
		}};

		//end region
		//region Trees

		aliveTree = new LivingTreeBlock("alive-tree", 2){{
			mapColor = Color.valueOf("74d660");
		}};
		blossomTree = new LivingTreeBlock("blossom-tree", 1){{
			mapColor = Color.valueOf("f3b9c3");
		}};
		elmTree = new LivingTreeBlock("elm-tree", 1){{
			mapColor = Color.valueOf("ECB01E");
		}};
		pineTree = new LivingTreeBlock("pine-tree", 1){{
			mapColor = Color.valueOf("356a41");
		}};
		cheeseTree = new LivingTreeBlock("cheese-tree", 1){{
			mapColor = Color.valueOf("d7d177");
		}};

		//dead/static trees (trees with no layers)
		burntTree = new TreeBlock("burnt-tree"){{
			mapColor = Color.valueOf("172025");
			shadowOffset = -1f;
		}};
		ashTree = new TreeBlock("ash-tree"){{
			mapColor = Color.valueOf("98a3a8");
			shadowOffset = -1f;
		}};
		deadTree = new TreeBlock("dead-tree"){{
			mapColor = Color.valueOf("744700");
			variants = 2;
		}};
		mossydeadTree = new TreeBlock("mossydead-tree"){{
			mapColor = Color.valueOf("744700");
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
		//region Bushes/Shrubs

		shrub = new LivingBush("shrub"){{
			mapColor = Color.valueOf("74d660");
			Blocks.grass.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
			lobesMin = 5;
			lobesMax = 6;
			magMin = 4;
			magMax = 6;
			sclMin = 20f;
			sclMax = 60f;
		}};
		
		sandyshrub = new LivingBush("sandy-shrub"){{
			mapColor = Color.valueOf("f7cba4");
			Blocks.sand.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
			lobesMin = 5;
			lobesMax = 6;
			magMin = 4;
			magMax = 6;
			sclMin = 20f;
			sclMax = 60f;
		}};

		tallGrass = new LivingBush("tallgrass", 2){{
			mapColor = Color.valueOf("87d661");
			lobesMin = 4;
			lobesMax = 8;
			magMin = 4;
			magMax = 9;
			rot = 0;
		}};
		
		grassyWall = new TreeBlock("grassy-wall"){{
			variants = 3;
			mapColor = Color.valueOf("74d660");
			shadowOffset = 0f;
		}};

		//testblock = new StaticWall("mark"){{}};

		//end region
		//region Plants
		//haha nothing yet

		//end region
		//region Water Plants

		lilypad = new LivingProp("lily-pad", 3){{
			mapColor = Color.valueOf("74d660");
		}};
		largelilypad = new LivingProp("large-lily-pad", 3){{
			mapColor = Color.valueOf("74d660");
		}};

		//TODO: do i even need a living Bush class
		cattail = new LivingBush("cattail", 2){{
			mapColor = Color.valueOf("74d660");
			lobesMin = 7;
			lobesMax = 10;
			magMin = 3;
			magMax = 7;
			sclMin = 20f;
			sclMax = 60f;
		}};
		
		
		//end region
		//region Functional Blocks

		//BLOCKS and core
		coreNest = new CoreBlock("core-nest"){{
			requirements(Category.effect, with(Items.copper, 5, Items.lead, 8));
			alwaysUnlocked = true;
			isFirstTier = true;
			unitType = DustUnits.cricket;
			health = 1600;
			itemCapacity = 2500;
			size = 3;
			unitCapModifier = 10;
			//armor = 5f;
		}};

		nitroplastChamber = new GenericCrafter("nitroplast-chamber"){{
            requirements(Category.crafting, with(Items.silicon, 120, Items.graphite, 80, DustItems.aquamarine, 80));
            size = 3;

			hasLiquids = true;
            outputLiquid = new LiquidStack(DustLiquids.juice, 10f/ 60f);
			//ItemStack(Items.oxide, 1);
            //researchCostMultiplier = 1.1f;

			consumePower(1.5f);
            consumeLiquid(Liquids.water, 2f / 60f);
            consumeItem(DustItems.chlorophyte);
			//powerProduction = 1.7f;

            rotateDraw = false;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(), new DrawDefault());
            ambientSound = Sounds.extractLoop;
            ambientSoundVolume = 0.08f;

            regionRotated1 = 2;
            craftTime = 60f * 2f;
            liquidCapacity = 45f;
        }};
		//
		// TILES AND FLOORING
		//
		
				//Snippet
        //    walkSound = Sounds.mud;
        //    walkSoundVolume = 0.08f;
        //    walkSoundPitchMin = 0.4f;
        //    walkSoundPitchMax = 0.5f;

		//end region
		//region Flooring
		
		taigaGrass = new Floor("taiga-grass"){{
			variants = 5;
			attributes.set(Attribute.water, 0.1f);
		}};

		taigaLeaves = new OverlayFloor("taiga-leaves"){{
			variants = 5;
			edge = "taiga-leaves";
			//TODO: needs edge support
		}};
		
		blossomGrass = new Floor("blossom-grass"){{
			variants = 5;
		}};
		
		blossomLeaves = new OverlayFloor("blossom-leaves"){{
			variants = 4;
		}};

		elmGrass = new Floor("elm-grass"){{
			variants = 5;
		}};

		elmLeaves = new OverlayFloor("elm-leaves"){{
			variants = 3;
		}};

		shoreRock = new Floor("shorestone"){{}};

		basaltFloor = new Floor("basalt-floor"){{
			variants = 5;
			attributes.set(Attribute.water, -0.25f);
		}};

		//end region
		//region Underwater Flooring

		trophotWater = new TileEffect("trop-hotrock-water"){{
			speedMultiplier = 0.5f;
			liquidDrop = Liquids.water;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 70f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;

			attributes.set(Attribute.heat, 0.2f);
			emitLight = true;
            lightRadius = 50f;
            lightColor = Color.orange.cpy().a(0.3f);

			soundEffect = DustSounds.bubblePop;
		}};
		
		tropmagmaWater = new TileEffect("trop-magma-water"){{
			speedMultiplier = 0.5f;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = Liquids.water;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.5f;

            attributes.set(Attribute.heat, 0.35f);
			emitLight = true;
			lightRadius = 70f;
			lightColor = Color.orange.cpy().a(0.3f);

			soundEffect = DustSounds.bubblePop;
		}};

		deeptrophotWater = new TileEffect("trop-hotrock-deep-water"){{
			speedMultiplier = 0.5f;
			status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = Liquids.water;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;

            attributes.set(Attribute.heat, 0.35f);
			emitLight = true;
			lightRadius = 30f;
			lightColor = Color.orange.cpy().a(0.3f);

			soundEffect = DustSounds.bubblePop;
		}};

		//trop magma deep water

		hotWater = new TileEffect("hotrock-shallow-water"){{
			speedMultiplier = 0.5f;
			liquidDrop = Liquids.water;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 90f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;

			attributes.set(Attribute.heat, 0.2f);
			emitLight = true;
			lightRadius = 50f;
			lightColor = Color.orange.cpy().a(0.3f);

			soundEffect = DustSounds.bubblePop;
		}};

		magmaWater = new TileEffect("magma-water"){{
			speedMultiplier = 0.5f;
			status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = Liquids.water;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.5f;

            attributes.set(Attribute.heat, 0.35f);
			emitLight = true;
			lightRadius = 70f;
			lightColor = Color.orange.cpy().a(0.3f);

			soundEffect = DustSounds.bubblePop;
		}};

		deeptropicalWater = new Floor("trop-deep-water"){{
			variants = 0;
			speedMultiplier = 0.5f;
			liquidDrop = Liquids.water;
			liquidMultiplier = 1.5f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 120f;
			drownTime = 200f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;
			supportsOverlay = true;
		}};

		tropicalWater = new Floor("trop-shallow-water"){{
			variants = 0;
			speedMultiplier = 0.5f;
			liquidDrop = Liquids.water;
			//liquidMultiplier = 1f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 90f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;
			supportsOverlay = true;
		}};

		//tropical flooring variants

		sandytropicalWater = new Floor("trop-sand-water"){{
			speedMultiplier = 0.8f;
			liquidDrop = Liquids.water;
			//liquidMultiplier = 1f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 50f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;
			supportsOverlay = true;
			shallow = true;
		}};

		dacitetropicalWater = new Floor("trop-dacite-water"){{
			speedMultiplier = 0.8f;
			liquidDrop = Liquids.water;
			//liquidMultiplier = 1f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 50f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;
			supportsOverlay = true;
			shallow = true;
		}};
		
		//misc
		//TODO: make algae waters spew green aroma/fog
		algaeWater = new TileEffect("algae-water"){{
			effectSpacing = 180f;
			chance = 0.1f;
			effect = DustyEffects.marshGas;

			speedMultiplier = 0.3f;
			liquidDrop = Liquids.water;
			liquidMultiplier = .5f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 90f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;
			supportsOverlay = true;
		}};
				
		deepalgaeWater = new TileEffect("deep-algae-water"){{
			effectSpacing = 180f;
			chance = 0.1f;
			effect = DustyEffects.marshGas;

			variants = 0;
			speedMultiplier = 0.15f;
			liquidDrop = Liquids.water;
			liquidMultiplier = .7f;
			drownTime = 220f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 90f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;
			supportsOverlay = true;
			shallow = true;
		}};

		//end region

		//flower = new Prop("flower"){{
		//	breakSound = Sounds.plantBreak;
		//	mapColor = Color.valueOf("74d660");
		//	hasShadow = true;
		//	Blocks.grass.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
		//	variants = 2;
		//}};

		//idea to add quartz walls that deflect lazers or smthn
		//function numbers (speed, damage)
		//turrets
	}
}