package minedusty.blocks;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;
import minedusty.DustAttributes;
import minedusty.content.*;
import minedusty.graphics.DustCacheLayers;
import minedusty.world.blocks.environment.*;

public class DustEnv {
	// TODO Chalcedony rock and Agate stone maybe
	// Boulders / Props
	public static Block driftWood, largesandBoulder, largeBoulder, largeshorestoneBoulder, shorestoneBoulder, largebasaltPillar, basaltPillar, largedaciteBoulder, largesoapstoneBoulder, largecalciteBoulder, calciteBoulder;
	public static Block divineSapling;
	
	// Tiles
	public static Block pattedGrass, taigaGrass, taigaLeaves, blossomGrass, blossomLeaves, elmGrass, elmLeaves;
	public static Block carbonPlates, basaltBumpy, yellow, yellowFlats, blueAsh, kaoliniteFloor, calciteFloor, calciteRough, basaltFloor, basaltSmooth, basaltSands;
	public static Block shoreSmooth, shoreRock, duneSand; //Kinda bad ones

	public static Block shorestoneWater, basaltSandsWater, basaltWater, basaltTropWater, sandyTropWater, daciteTropWater, oilWater, oilSandWater, daciteWater, sanddeepWater;
	public static Block algaeWater, deepalgaeWater, quickSand;
	public static Block hotWater, magmaWater, trophotWater, tropmagmaWater, tropicalWater, deeptropicalWater, deeptrophotWater;
	public static Block flowWater;

	// Ores
	public static Block oreOxidecopper, oreChlorophyteChunk, oreChlorophyte, wallChlorophyte, oreAquamerium, wallOxide;
	
	// Walls
	public static Block mossStoneWall, grassyWall, shorestoneWall, basaltWall, coralWall, soapstoneWall, calciteWall, rhyoliteChlorophyte;

	// Misc
	public static Block fallingLeavesEffect, flowingWaterEffect;

	public static void loadContent() {
		
		//region Tiles
		pattedGrass = new Floor("patted-grass", 5){{
			mapColor = Color.valueOf("#4C864C");
		}};
		
		taigaGrass = new Floor("taiga-grass", 5){{
			mapColor = Color.valueOf("#418A5D");
		}};
		
		// TODO rework the overlay leaves (blossom and elms)
		blossomGrass = new Floor("blossom-grass", 5){{
		}};
		
		blossomLeaves = new OverlayFloor("blossom-leaves"){{
			variants = 4;
		}};

		elmGrass = new Floor("elm-grass", 5){{
		}};

		elmLeaves = new OverlayFloor("elm-leaves"){{
			variants = 3;
		}};


		// TODO replace dune sand
		// duneSand = new Floor("dune-sand", 3){{
		// 	itemDrop = Items.sand;
		// 	playerUnmineable = true;
        //     attributes.set(Attribute.oil, 0.7f);
		// 	wall = Blocks.sandWall;
		// }};

		// TODO update textures
		shoreRock = new Floor("shorestone", 3){{
			wall = shorestoneWall;
			attributes.set(Attribute.water, 0.25f);
		}};

		shoreSmooth = new Floor("shorestone-smooth", 6){{
			wall = shorestoneWall;
			attributes.set(Attribute.water, 0.25f);
		}};

		basaltFloor = new Floor("basalt-floor", 5){{
			wall = basaltWall;
		}};

		basaltSmooth = new Floor("basalt-smooth", 5){{
			wall = basaltWall;
		}};

		basaltBumpy = new Floor("basalt-bumpy", 6){{
			wall = basaltWall;
		}};
		
		basaltSands = new Floor("basalt-sands", 5){{
			wall = basaltWall;
			attributes.set(Attribute.oil, 0.8f);
		}};

		calciteFloor = new Floor("calcite-floor", 5){{
			wall = calciteWall;
		}};
		calciteRough = new Floor("calcite-rough", 4){{
			wall = calciteWall;
		}};

		yellow = new Floor("yellow", 1){{
		}};
		yellowFlats = new Floor("yellow-flats", 1){{
		}};

		blueAsh = new Floor("blue-ash", 3){{

		}};

		kaoliniteFloor = new Floor("kaolinite-floor", 3){{

		}};

		carbonPlates = new Floor("carbon-plates", 5){{
			attributes.set(Attribute.water, -1f);
			wall = Blocks.carbonWall;
		}};

		//end region

		// Decorations need to be set below tiles to not crash.

		//region Props and Decorations

		driftWood = new Prop("driftwood"){{
			variants = 1;
			customShadow = true;
			shoreRock.asFloor().decoration = this;
			shoreSmooth.asFloor().decoration = this;
		}};
		
		divineSapling = new BoulderProp("divine-sapling"){{
			customShadow = true;
			variants = 2;
			calciteRough.asFloor().decoration = this;
		}};

		largeBoulder = new BoulderProp("large-boulder"){{
			mapColor = Color.valueOf("706f74");
			customShadow = true;
			shadowOffset = -1.5f;
			variants = 2;
		}};

		largeshorestoneBoulder = new BoulderProp("large-shorestone"){{
			customShadow = true;
			variants = 2;
			mapColor = Color.valueOf("706f74");
		}};
		shorestoneBoulder = new Prop("shorestone-boulder"){{
			variants = 2;
			buildVisibility = BuildVisibility.sandboxOnly;
			shoreRock.asFloor().decoration = this;
			shoreSmooth.asFloor().decoration = this;
		}};

		largebasaltPillar = new BoulderProp("large-basaltder"){{
			mapColor = Color.valueOf("706f74");
			customShadow = true;
			variants = 2;
		}};
		basaltPillar = new Prop("basalt-boulder"){{
			variants = 3;
			buildVisibility = BuildVisibility.sandboxOnly;
			basaltBumpy.asFloor().decoration = this;
			basaltSands.asFloor().decoration = this;
		}};

		largedaciteBoulder = new BoulderProp("large-dacite-boulder"){{
			mapColor = Color.valueOf("706f74");
			customShadow = true;
			variants = 2;
		}};

		largesoapstoneBoulder = new BoulderProp("large-soapstone-boulder"){{
			mapColor = Color.valueOf("706f74");
			customShadow = true;
			variants = 2;
		}};

		calciteBoulder = new Prop("calcite-boulder"){{
			variants = 2;
			buildVisibility = BuildVisibility.sandboxOnly;
			calciteFloor.asFloor().decoration = this;
		}};

		largecalciteBoulder = new BoulderProp("large-calcite-boulder"){{
			variants = 1;
		}};
		
		largesandBoulder = new BoulderProp("large-sand-boulder"){{
			variants = 2;
			shadowOffset = -2f;
		}};

		//end region

		//region Water Tiles
		flowWater = new WaterTileEffect("flow-water", 1){{
			speedMultiplier = 1.3f;
			liquidMultiplier = 0.3f;
			supportsOverlay = false;

			addEffect(DustyEffects.flowWater, 0.35f, 5f);
			addEffect(DustyEffects.mistCloud, 0.4f, 12f);
			//soundEffect = Sounds.splash;
		}};

		tropicalWater = new WaterFloor("trop-shallow-water"){{
			speedMultiplier = 0.6f;
		}};

		deeptropicalWater = new WaterFloor("trop-deep-water"){{
			speedMultiplier = 0.4f;
			liquidMultiplier = 1.5f;
			statusDuration = 120f;
			drownTime = 200f;
		}};

		hotWater = new WaterTileEffect("hotrock-shallow-water", 3){{
			speedMultiplier = 0.5f;
			
			attributes.set(Attribute.heat, 0.2f);
			emitLight = true;
			lightRadius = 50f;
			lightColor = Color.orange.cpy().a(0.3f);

			addEffect(DustyEffects.airBubble, DustSounds.bubblePop);
		}};
		magmaWater = new WaterTileEffect("magma-water", 3){{
			speedMultiplier = 0.5f;
            albedo = 0.5f;

            attributes.set(Attribute.heat, 0.35f);
			emitLight = true;
			lightRadius = 70f;
			lightColor = Color.orange.cpy().a(0.3f);

			addEffect(DustyEffects.airBubble, DustSounds.bubblePop);
		}};
		trophotWater = new WaterTileEffect("trop-hotrock-water", 3){{
			speedMultiplier = 0.6f;

			attributes.set(Attribute.heat, 0.2f);
			emitLight = true;
            lightRadius = 50f;
            lightColor = Color.orange.cpy().a(0.3f);

			addEffect(DustyEffects.airBubble, DustSounds.bubblePop);
		}};
		
		tropmagmaWater = new WaterTileEffect("trop-magma-water", 3){{
			speedMultiplier = 0.6f;
            albedo = 0.5f;

            attributes.set(Attribute.heat, 0.35f);
			emitLight = true;
			lightRadius = 70f;
			lightColor = Color.orange.cpy().a(0.3f);

			addEffect(DustyEffects.airBubble, DustSounds.bubblePop);
		}};
		
		deeptrophotWater = new WaterTileEffect("trop-hotrock-deep-water", 3){{
			speedMultiplier = 0.4f;
            statusDuration = 120f;

            attributes.set(Attribute.heat, 0.2f);
			emitLight = true;
			lightRadius = 30f;
			lightColor = Color.orange.cpy().a(0.3f);

			addEffect(DustyEffects.airBubble, DustSounds.bubblePop);
		}};

		algaeWater = new WaterTileEffect("algae-water", 3){{
			addEffect(DustyEffects.marshGas, 0.02f, 180f);

			speedMultiplier = 0.35f;
			liquidMultiplier = 0.5f;
		}};

		deepalgaeWater = new WaterTileEffect("deep-algae-water"){{
			addEffect(DustyEffects.marshGas, 0.02f, 180f);

			variants = 0;
			speedMultiplier = 0.15f;
			liquidMultiplier = 0.7f;
			drownTime = 220f;
			statusDuration = 120f;
		}};

		daciteWater = new WaterFloor("dacite-water", 3){{
			statusDuration = 50f;
		}};

		sanddeepWater = new WaterFloor("sand-deep-water", 3){{
            speedMultiplier = 0.3f;
            statusDuration = 100f;

			liquidMultiplier = 1.5f;
			drownTime = 200f;
        }};
		
		// Watered tiles

		shorestoneWater = new WaterFloor("shorestone-water", 3){{
			statusDuration = 50f;
		}};

		basaltSandsWater = new WaterFloor("basalt-sands-water", 3){{
			statusDuration = 50f;
		}};

		basaltWater = new WaterFloor("basalt-water", 3){{
			statusDuration = 50f;
		}};

		basaltTropWater = new WaterFloor("trop-basalt-water", 3){{
			speedMultiplier = 0.9f;
			statusDuration = 50f;
		}};

		sandyTropWater = new WaterFloor("trop-sand-water", 3){{
			speedMultiplier = 0.9f;
			statusDuration = 50f;
		}};

		daciteTropWater = new WaterFloor("trop-dacite-water", 3){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
		}};

		oilWater = new WaterTileEffect("oil-water", 3){{
			speedMultiplier = 0.5f;
			liquidDrop = Liquids.oil;
			liquidMultiplier = 0.8f;

			addEffect(Fx.ventSteam, Color.valueOf("111316"));
		}};

		oilSandWater = new WaterTileEffect("oil-sand-water", 3){{
			speedMultiplier = 0.8f;
			liquidDrop = Liquids.oil;
			attributes.set(Attribute.oil, 0.8f);
			liquidMultiplier = 0.8f;
			statusDuration = 50f;

			addEffect(Fx.ventSteam, Color.valueOf("111316"));
		}};
		
		quickSand = new WaterFloor("quick-sand", 3){{
			drownTime = 120f;
			isLiquid = true;
			
			speedMultiplier = 0.55f;
			supportsOverlay = true;
			attributes.set(Attribute.water, 0.8f);
			cacheLayer = DustCacheLayers.quicksand;//CacheLayer.mud;
            
			walkSound = Sounds.mud; // need some custom sfx for these maybe
            walkSoundVolume = 0.08f;
            walkSoundPitchMin = 0.4f;
            walkSoundPitchMax = 0.5f;
		}};

		//end region

		//region Walls
		mossStoneWall = new StaticWall("moss-stone-wall"){{
		}};

		grassyWall = new TreeBlock("grassy-wall"){{
			variants = 3;
			mapColor = Color.valueOf("74d660");
			shadowOffset = 0f;
			buildVisibility = BuildVisibility.sandboxOnly;
		}};

		shorestoneWall = new StaticWall("shorestone-wall"){{}};
		basaltWall = new StaticWall("basalt-wall"){{}};
		calciteWall = new StaticWall("calcite-wall"){{
			variants = 3;
		}};
		soapstoneWall = new StaticWall("soapstone-wall"){{
			variants = 4;
		}};
		coralWall = new StaticTree("red-coral-wall"){{
			clipSize = 120f;
		}};

		rhyoliteChlorophyte = new StaticWall("rhyolite-chlorophyte"){{
			variants = 3;
			itemDrop = DustItems.chlorophyte;
			attributes.set(DustAttributes.chlorophyte, 1f);
		}};

		//end region

		//region Ores & Resources
        oreChlorophyte = new OreBlock("ore-chlorophyte", DustItems.chlorophyte){{
            oreDefault = false;
			variants = 3;

			emitLight = true;
            lightRadius = 70f;
            lightColor = Color.acid.cpy().a(0.3f);
        }};

        oreChlorophyteChunk = new OreBlock("ore-chlorophyte-chunk", DustItems.chlorophyte){{
            oreDefault = false;
			variants = 3;

			emitLight = true;
            lightRadius = 70f;
            lightColor = Color.acid.cpy().a(0.3f);
        }};

		wallChlorophyte = new OreBlock("wall-chlorophyte", DustItems.chlorophyte){{
			oreDefault = false;
			wallOre = true;
			variants = 3;

			emitLight = true;
			lightRadius = 60f;
			lightColor = Color.valueOf("a7db32").cpy().a(0.6f);
		}};

		oreAquamerium = new OreBlock("ore-aquamerium", DustItems.aquamerium){{
			oreDefault = false;
			variants = 5;

			emitLight = true;
            lightRadius = 70f;
            lightColor = Color.cyan.cpy().a(0.3f);
		}};

		oreOxidecopper = new OreBlock("ore-oxidecopper", DustItems.oxidecopper){{
			oreDefault = true;
		}};

		

		wallOxide = new OreBlock("wall-oxide", Items.oxide){{
			oreDefault = false;
			wallOre = true;
			variants = 3;

			emitLight = true;
			lightRadius = 40f;
			lightColor = Color.yellow.cpy().a(0.6f);
		}};
		//end region

		//region Misc
		fallingLeavesEffect = new TileEffect("falling-leaves"){{
			addEffect(DustyEffects.fallingLeaves, Color.valueOf("c32121"));
		}};

		flowingWaterEffect = new TileEffect("flow-water-effect"){{
			addEffect(DustyEffects.flowWater);
		}};

		//end region

	}
}