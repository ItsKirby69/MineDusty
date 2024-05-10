package minedusty.content;

import org.w3c.dom.Attr;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.CacheLayer;
import mindustry.type.StatusEffect;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;
import minedusty.world.blocks.environment.*;

//TODO, MAKE SURE TREE BLOCK IS VARIANT ONCE FIX IS MADE
public class DustBlocks {
	public static Block 
	testblock,

	//Turrets
	scatterSilo, 
	//Blocks
	//Tiles
	taigaLeaves, taigaGrass,

	tropicalwater, sandytropicalwater,
	deeptropicalwater, dacitetropicalwater,
	algaeWater, deepalgaeWater,
	trophotWater, hotWater, tropdeephotWater, tropmagmaWater, magmaWater,
	grassyFloor, grassyWall,

	basaltFloor, basaltCrack, basaltPillar, shoreRock, soapStone, 
	watersoapStone, tropwatersoapStone,
	watershoreRock, tropwatershoreRock,

	//Drills
	//Productions
	grinder, quartzSmelter,
	//Props
	burntTree, aliveTree, ashTree, coconutTree,
	shrub, minibushy, flower, cattail, bush,
	lilypad, largelilypad,
	largeBoulder, largeshorestoneBoulder, shorestoneboulder,
	//ores
	oreQuartz;
	//add more categories

	public static void load(){
		//props/walls
		largeBoulder = new Prop("large-boulder"){{
			hasShadow = true;
			instantDeconstruct = true;
			breakSound = Sounds.rockBreak;
			breakEffect = Fx.breakProp;
			mapColor = Color.valueOf("706f74");
			customShadow = true;
			variants = 2;
		}};

		largeshorestoneBoulder = new Prop("large-shorestone"){{
			hasShadow = true;
			customShadow = true;
			variants = 2;
			breakEffect = Fx.breakProp;
			mapColor = Color.valueOf("706f74");
		}};

		shorestoneboulder = new Prop("shorestone-boulder"){{
			variants = 2;
		}};

		aliveTree = new LivingTreeBlock("alive-tree", 2){{
			mapColor = Color.valueOf("74d660");
			shadowOffset = -4f;
		}};

		burntTree = new TreeBlock("burnt-tree"){{
			mapColor = Color.valueOf("74d660");
			shadowOffset = -1f;
		}};

		ashTree = new TreeBlock("ash-tree"){{
			mapColor = Color.valueOf("74d660");
			shadowOffset = -1f;
		}};

		//maybe i don't need a coconut tree
		/*coconutTree = new LivingBush("coconut-tree"){{
			mapColor = Color.valueOf("000000");
			Blocks.grass.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
			lobesMin = 3;
			lobesMax = 3;
			magMin = 4;
			magMax = 7;
			sclMin = 30f;
			sclMax = 60f;
			solid = true;
			clipSize = 90f;
		}};*/

		shrub = new SeaBush("shrub"){{
			mapColor = Color.valueOf("74d660");
			Blocks.grass.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
			lobesMin = 5;
			lobesMax = 6;
			magMin = 4;
			magMax = 6;
			sclMin = 20f;
			sclMax = 60f;
		}};

		grassyWall = new TreeBlock("grassy-wall"){{
			variants = 3;
			mapColor = Color.valueOf("74d660");
			shadowOffset = 0f;
		}};

		//testblock = new StaticWall("mark"){{}};

		cattail = new SeaBush("cattail"){{
			mapColor = Color.valueOf("74d660");
			lobesMin = 3;
			lobesMax = 5;
			magMin = 3;
			magMax = 7;
			sclMin = 20f;
			sclMax = 60f;
		}};

		lilypad = new LivingProp("lily-pad", 3){{
			mapColor = Color.valueOf("74d660");
		}};
		
		largelilypad = new LivingProp("large-lily-pad", 3){{
			mapColor = Color.valueOf("74d660");
		}};
		
		//
		// TILES AND FLOORING
		//
		
				//Snippet
        //    walkSound = Sounds.mud;
        //    walkSoundVolume = 0.08f;
        //    walkSoundPitchMin = 0.4f;
        //    walkSoundPitchMax = 0.5f;

		taigaLeaves = new OverlayFloor("taiga-leaves"){{
			variants = 5;
		}};

		taigaGrass = new Floor("taiga-grass"){{
			variants = 5;
			attributes.set(Attribute.water, 0.1f);
		}};

		basaltFloor = new Floor("basalt-floor"){{
			variants = 5;
			attributes.set(Attribute.water, -0.25f);
		}};

		shoreRock = new Floor("shorestone"){{}};

		trophotWater = new Floor("trop-hotrock-water"){{
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
		}};

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
		}};

		tropdeephotWater = new TileEffect("trop-hotrock-deep-water"){{
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
		}};

		deeptropicalwater = new Floor("trop-deep-water"){{
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

		tropicalwater = new Floor("trop-shallow-water"){{
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

		sandytropicalwater = new Floor("trop-sand-water"){{
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

		dacitetropicalwater = new Floor("trop-dacite-water"){{
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
		
		algaeWater = new Floor("algae-water"){{
			speedMultiplier = 0.3f;
			liquidDrop = Liquids.water;
			liquidMultiplier = .5f;
			drownTime = 350f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 50f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;
			supportsOverlay = true;
			shallow = true;
		}};
				
		deepalgaeWater = new Floor("deep-algae-water"){{
			variants = 0;
			speedMultiplier = 0.15f;
			liquidDrop = Liquids.water;
			liquidMultiplier = .7f;
			drownTime = 180f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 50f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;
			supportsOverlay = true;
			shallow = true;
		}};

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