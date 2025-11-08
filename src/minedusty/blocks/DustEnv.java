package minedusty.blocks;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
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
	public static Block clayBall;
	public static Block pearlBoulder, largesandBoulder, largeBoulder, largeshorestoneBoulder, shorestoneBoulder, largebasaltPillar, basaltPillar, largedaciteBoulder, largesoapstoneBoulder, largecalciteBoulder, calciteBoulder;
	public static Block divineSapling, driftWood;
	
	// Tiles
	public static Block pattedGrass, taigaGrass, taigaLeaves, blossomGrass, blossomLeaves, elmGrass, elmLeaves;
	public static Block dacitePlates, carbonPlates, basaltBumpy, yellow, yellowFlats, blueAsh, kaoliniteFloor, calciteFloor, calciteRough, basaltFloor, basaltSmooth, basaltSands;
	public static Block shoreSmooth, shoreRock, duneSand; //Kinda bad ones
	public static Block prismate, prismite, silkSand, saltLumps, clayFloor;

	public static Block silkSandTropWater, silkSandWater, pattedGrassWater, calciteWater, calciteTropWater, shorestoneWater, basaltSandsWater, basaltWater, basaltTropWater, sandyTropWater, daciteTropWater, oilWater, oilSandWater, daciteWater, sanddeepWater;
	public static Block algaeWater, deepalgaeWater, quickSand;
	public static Block hotWater, magmaWater, trophotWater, tropmagmaWater, tropicalWater, deeptropicalWater, deeptrophotWater;
	public static Block stoneWater, magmaBasalt, hotRockBasalt;
	public static Block divineGrass;

	// Ores
	public static Block oreGalena, oreOxidecopper, oreChlorophyteChunk, oreChlorophyte, wallChlorophyte, oreAquamerium, wallOxide;
	
	// Walls
	public static Block prismiteBlock, mossStoneWall, grassyWall, shorestoneWall, basaltWall, coralWall, soapstoneWall, calciteWall, rhyoliteChlorophyte;
	public static Block amethystCrystals, hardenedClayWall, amethystClusteredClayWall;

	// Misc
	public static Block smallbasaltChimney, basaltChimney;
	public static Block waterSmokeEffect, fallingLeavesEffect, flowingWaterEffect, logoBlock, titleBlock;

	public static void loadContent() {
		
		//region Tiles
		pattedGrass = new Floor("patted-grass", 5){{
			mapColor = Color.valueOf("#4C864C");
			attributes.set(DustAttributes.turf, 0.3f);
			attributes.set(DustAttributes.salt, -0.15f);
		}};
		
		taigaGrass = new Floor("taiga-grass", 5){{
			mapColor = Color.valueOf("#418A5D");
			attributes.set(DustAttributes.turf, 0.15f);
		}};
		
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
		
		Blocks.dirtWall.attributes.set(DustAttributes.chlorophyte, 0.1f);

		Blocks.salt.attributes.set(DustAttributes.salt, 0.35f);
		Blocks.sand.attributes.set(DustAttributes.salt, 0.2f);
		Blocks.dacite.attributes.set(DustAttributes.salt, 0.25f);
		Blocks.stone.attributes.set(DustAttributes.salt, 0.1f);
		Blocks.grass.attributes.set(DustAttributes.salt, -0.1f);

		Blocks.grass.attributes.set(DustAttributes.turf, 0.167f);
		Blocks.moss.attributes.set(DustAttributes.turf, 0.167f);
		Blocks.dirt.attributes.set(DustAttributes.turf, 0.08333f);
		Blocks.mud.attributes.set(DustAttributes.turf, 0.08333f);

		saltLumps = new Floor("salt-lumps", 1){{
			attributes.set(DustAttributes.salt, 0.3f);
		}};

		clayFloor = new Floor("clay-floor", 3){{
			attributes.set(DustAttributes.salt, 0.15f);
		}};
		
		prismite = new Floor("prismite", 1){{
			cacheLayer = DustCacheLayers.prismite;
		}};
		prismate = new Floor("prismate", 1){{
			cacheLayer = DustCacheLayers.prismite;
		}};
		// Supposed to be a pristine version of sand
		silkSand = new TileEffect("silksand"){{
			attributes.set(Attribute.oil, -0.4f);
			//cacheLayer = DustCacheLayers.silksand;
			//itemDrop = Items.sand;
			//playerUnmineable = true;
			addEffect(DustyEffects.sparkles, 0.05f, 300f, Color.valueOf("#fff2d9"));
		}};

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
			itemDrop = Items.sand;
			playerUnmineable = true;
			attributes.set(Attribute.oil, 0.8f);
		}};

		calciteFloor = new Floor("calcite-floor", 5){{
			wall = calciteWall;
		}};
		calciteRough = new Floor("calcite-rough", 4){{
			wall = calciteWall;
		}};

		divineGrass = new Floor("divine-grass", 3){{
			mapColor = Color.valueOf("#c63f48");
			attributes.set(DustAttributes.turf, 0.167f);
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

		dacitePlates = new Floor("dacite-plates", 4){{}};

		magmaBasalt = new Floor("magma-basalt", 3){{
            attributes.set(Attribute.heat, 0.75f);
            attributes.set(Attribute.water, -0.75f);
            blendGroup = basaltFloor;

            emitLight = true;
            lightRadius = 50f;
            lightColor = Color.orange.cpy().a(0.3f);
		}};

        hotRockBasalt = new Floor("hotrock-basalt", 3){{
            attributes.set(Attribute.heat, 0.5f);
            attributes.set(Attribute.water, -0.5f);
            blendGroup = basaltFloor;

            emitLight = true;
            lightRadius = 30f;
            lightColor = Color.orange.cpy().a(0.15f);
        }};

		//end region

		// Decorations need to be set below tiles to not crash.

		//region Props and Decorations

		driftWood = new Prop("driftwood"){{
			variants = 2;
			layer = Layer.blockProp + 0.75f;
			customShadow = true;
			shoreRock.asFloor().decoration = this;
			shoreSmooth.asFloor().decoration = this;
		}};
		
		divineSapling = new BoulderProp("divine-sapling"){{
			calciteRough.asFloor().decoration = this;
		}};

		pearlBoulder = new BoulderProp("pearl-boulder"){{
			mapColor = Color.valueOf("#f3ddb3");
			shadowAlpha = 0.5f;
			//silkSand.asFloor().decoration = this;
		}};

		largeBoulder = new BoulderProp("large-boulder"){{
			mapColor = Color.valueOf("706f74");
			shadowOffset = -1.5f;
		}};

		largeshorestoneBoulder = new BoulderProp("large-shorestone"){{
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
		}};
		basaltPillar = new Prop("basalt-boulder"){{
			variants = 3;
			buildVisibility = BuildVisibility.sandboxOnly;
			basaltBumpy.asFloor().decoration = this;
			basaltSands.asFloor().decoration = this;
		}};

		largedaciteBoulder = new BoulderProp("large-dacite-boulder"){{
			mapColor = Color.valueOf("706f74");
		}};

		largesoapstoneBoulder = new BoulderProp("large-soapstone-boulder"){{
			mapColor = Color.valueOf("706f74");
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
			shadowOffset = -2f;
		}};

		clayBall = new BoulderProp("clay-ball"){{
			variants = 2;
		}};

		//end region

		//region Water Tiles
		// TODO replace with just an effect block with direction arro

		tropicalWater = new WaterFloor("trop-shallow-water"){{
			statusDuration = 90f;
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
			addEffect(DustyEffects.marshGas, 0.5f/60f, 180f);

			speedMultiplier = 0.35f;
			liquidMultiplier = 0.5f;
		}};

		deepalgaeWater = new WaterTileEffect("deep-algae-water"){{
			addEffect(DustyEffects.marshGas, 0.5f/60f, 180f);

			variants = 0;
			speedMultiplier = 0.15f;
			liquidMultiplier = 0.7f;
			drownTime = 220f;
			statusDuration = 120f;
		}};

		sanddeepWater = new WaterFloor("sand-deep-water", 3){{
            speedMultiplier = 0.3f;
            statusDuration = 100f;

			liquidMultiplier = 1.5f;
			drownTime = 200f;
        }};
		
		// Watered tiles
		stoneWater = new WaterFloor("stone-water", 3){{
		}};
		silkSandWater = new WaterTileEffect("silksand-water", 3){{
			addEffect(DustyEffects.sparkles, 0.05f,300f, Color.valueOf("#d9fcff"));
		}};
		silkSandTropWater = new WaterTileEffect("trop-silksand-water", 3){{
			addEffect(DustyEffects.sparkles, 0.05f,300f, Color.valueOf("#d9fcff"));
		}};

		shorestoneWater = new WaterFloor("shorestone-water", 3){{
		}};

		basaltSandsWater = new WaterFloor("basalt-sands-water", 3){{
		}};

		basaltWater = new WaterFloor("basalt-water", 3){{
		}};

		basaltTropWater = new WaterFloor("trop-basalt-water", 3){{
			speedMultiplier = 0.9f;
		}};

		sandyTropWater = new WaterFloor("trop-sand-water", 3){{
			speedMultiplier = 0.9f;
		}};

		daciteWater = new WaterFloor("dacite-water", 3){{
		}};

		daciteTropWater = new WaterFloor("trop-dacite-water", 3){{
			speedMultiplier = 0.9f;
		}};

		calciteWater = new WaterFloor("calcite-water", 3){{
		}};

		calciteTropWater = new WaterFloor("trop-calcite-water", 3){{
			speedMultiplier = 0.9f;
		}};

		pattedGrassWater = new WaterFloor("patted-grass-water", 3){{}};

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
		amethystCrystals = new TallBlock("amethyst-crystals"){{
			variants = 2;
			clipSize = 140f;
			itemDrop = DustItems.amethyst;
			attributes.set(DustAttributes.crystal, 1f);
		}};

		hardenedClayWall = new StaticWall("hardened-clay-wall"){{
			variants = 3;
		}};

		amethystClusteredClayWall = new StaticWall("amethyst-clustered-clay-wall"){{
			variants = 2;
			itemDrop = DustItems.amethyst;
			attributes.set(DustAttributes.crystal, 0.8f);
		}};

		prismiteBlock = new StaticWall("prismite-block"){{
			variants = 1;
		}};

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
        oreChlorophyte = new TieredOreBlock("ore-chlorophyte", DustItems.chlorophyte){{
            oreDefault = false;
			variants = 3;

			emitLight = true;
            lightRadius = 70f;
            lightColor = Color.acid.cpy().a(0.3f);
        }};

        oreChlorophyteChunk = new TieredOreBlock("ore-chlorophyte-chunk", DustItems.chlorophyte){{
			variants = 3;

			emitLight = true;
            lightRadius = 70f;
            lightColor = Color.acid.cpy().a(0.3f);
        }};

		wallChlorophyte = new TieredOreBlock("wall-chlorophyte", DustItems.chlorophyte){{
			wallOre = true;
			variants = 3;

			emitLight = true;
			lightRadius = 60f;
			lightColor = Color.valueOf("a7db32").cpy().a(0.6f);
		}};

		oreAquamerium = new TieredOreBlock("ore-aquamerium", DustItems.aquamerium){{
			variants = 5;

			emitLight = true;
            lightRadius = 70f;
            lightColor = Color.cyan.cpy().a(0.3f);
		}};

		oreOxidecopper = new NoiseOreBlock("ore-oxidecopper", DustItems.oxidecopper){{
			variants = 3;
			oreDefault = true;
		}};

		oreGalena = new TieredOreBlock("ore-galena", Items.lead, DustItems.galena, 4){{
		}};

		wallOxide = new TieredOreBlock("wall-oxide", Items.oxide){{
			wallOre = true;
			variants = 3;

			emitLight = true;
			lightRadius = 40f;
			lightColor = Color.yellow.cpy().a(0.6f);
		}};
		//end region

		//region Misc
		// These two aren't working out
		basaltChimney = new ChimneyVent("basalt-chimney"){{
			parent = Blocks.basalt;
		}};

		smallbasaltChimney = new ChimneyVent("small-basalt-chimney"){{
			parent = Blocks.basalt;
			totalHeight = 0.0045f;
			pillarCount = 3;
			size = 2;
		}};

		fallingLeavesEffect = new TileEffect("falling-leaves"){{
			addEffect(DustyEffects.fallingLeaves, Color.valueOf("c32121"));
		}};

		flowingWaterEffect = new FlowWaterTile("flow-water"){{
			//addEffect(DustyEffects.flowWater, 1.0f, 8f, true);
		}};

		waterSmokeEffect = new WaterEffectTile("water-smoke"){{
			addEffect(DustyEffects.mistCloud, 1.0f, 20f);
		}};

		logoBlock = new BoulderProp("icon-block"){{
			customShadow = true;
			buildVisibility = BuildVisibility.sandboxOnly;
			variants = 0;
		}};

		titleBlock = new BoulderProp("title-block"){{
			customShadow = true;
			buildVisibility = BuildVisibility.sandboxOnly;
			variants = 0;
		}};

		//end region

	}
}