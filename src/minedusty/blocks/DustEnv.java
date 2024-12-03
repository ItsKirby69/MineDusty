package minedusty.blocks;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.type.StatusEffect;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.OverlayFloor;
import mindustry.world.blocks.environment.Prop;
import mindustry.world.blocks.environment.StaticTree;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.environment.TreeBlock;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;
import minedusty.DustAttributes;
import minedusty.content.DustItems;
import minedusty.content.DustSounds;
import minedusty.content.DustyEffects;
import minedusty.world.blocks.environment.OverlayFloorEdged;
import minedusty.world.blocks.environment.TileEffect;

public class DustEnv {

	// Rocks
	public static Block duneRockBoulder, largeBoulder, largeshorestoneBoulder, shorestoneBoulder, largebasaltPillar, basaltPillar, largedaciteBoulder, largesoapstoneBoulder, calciteBoulder;

	// Tiles
	public static Block taigaGrass, taigaLeaves, blossomGrass, blossomLeaves, elmGrass, elmLeaves;
	public static Block shoreRock, basaltFloor, basaltSands, duneSand, calciteFloor, calciteCrags;

	public static Block basaltWater, basaltTropWater, sandyTropWater, daciteTropWater, oilWater, oilSandWater, oilTropWater, daciteWater, sanddeepWater;
	public static Block algaeWater, deepalgaeWater, quickSand;
	public static Block hotWater, magmaWater, trophotWater, tropmagmaWater, tropicalWater, deeptropicalWater, deeptrophotWater;

	// Ores
	public static Block oreChlorophyte, wallChlorophyte, oreAquamarine;
	
	// Walls
	public static Block oxideWall, grassyWall, shorestoneWall, basaltWall, coralWall, soapstoneWall, calciteWall, rhyoliteChlorophyte;


	public static void loadContent() {

		//region Boulders
		largeBoulder = new Prop("large-boulder"){{
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

		largedaciteBoulder = new Prop("large-dacite-boulder"){{
			mapColor = Color.valueOf("706f74");
			customShadow = true;
			variants = 2;
			buildVisibility = BuildVisibility.sandboxOnly;
		}};

		largesoapstoneBoulder = new Prop("large-soapstone-boulder"){{
			mapColor = Color.valueOf("706f74");
			customShadow = true;
			variants = 2;
			buildVisibility = BuildVisibility.sandboxOnly;
		}};

		calciteBoulder = new Prop("calcite-boulder"){{
			variants = 2;
			buildVisibility = BuildVisibility.sandboxOnly;
		}};
		
		duneRockBoulder = new Prop("dune-rock"){{
			variants = 2;
			buildVisibility = BuildVisibility.sandboxOnly;
		}};


		//end region

		//region Tiles
		taigaGrass = new Floor("taiga-grass"){{
			variants = 5;
		}};

		taigaLeaves = new OverlayFloorEdged("taiga-leaves"){{
			variants = 5;
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

		shoreRock = new Floor("shorestone"){{
			variants = 3;
			attributes.set(Attribute.water, 0.25f);
			decoration = shorestoneBoulder;
		}};

		basaltFloor = new Floor("basalt-floor"){{
			variants = 5;
			decoration = basaltPillar;
		}};
		
		basaltSands = new Floor("basalt-sands"){{
			variants = 5;
			attributes.set(Attribute.oil, 0.8f);
			decoration = basaltPillar;
		}};

		duneSand = new Floor("dune-sand"){{
			itemDrop = Items.sand;
			playerUnmineable = true;
            attributes.set(Attribute.oil, 0.7f);
			variants = 3;
		}};

		calciteFloor = new Floor("calcite-floor"){{
			variants = 3;
		}};
		calciteCrags = new Floor("calcite-crags"){{
			variants = 3;
		}};
		//end region

		//region Water Tiles
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

		algaeWater = new TileEffect("algae-water"){{
			effectSpacing = 180f;
			chance = 0.02f;
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
			chance = 0.03f;
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
		}};
		daciteWater = new Floor("dacite-water"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;

			status = StatusEffects.wet;
			cacheLayer = CacheLayer.water;
			isLiquid = true;
			liquidDrop = Liquids.water;
		}};
		sanddeepWater = new Floor("sand-deep-water"){{
            speedMultiplier = 0.3f;
            statusDuration = 100f;
            albedo = 0.9f;
            supportsOverlay = true;

			liquidMultiplier = 1.5f;
			drownTime = 200f;
			status = StatusEffects.wet;
			cacheLayer = CacheLayer.water;
			isLiquid = true;
			liquidDrop = Liquids.water;
			variants = 3;
        }};
		



		basaltWater = new Floor("basalt-water"){{
			variants = 3;
			speedMultiplier = 0.8f;
			statusDuration =50f;
			albedo = 0.9f;
			supportsOverlay = true;
			status = StatusEffects.wet;
			liquidDrop = Liquids.water;
			isLiquid = true;
			cacheLayer = CacheLayer.water;
		}};

		basaltTropWater = new Floor("trop-basalt-water"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;

			status = StatusEffects.wet;
			cacheLayer = CacheLayer.water;
			isLiquid = true;
			liquidDrop = Liquids.water;
			variants = 3;
		}};

		sandyTropWater = new Floor("trop-sand-water"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;

			status = StatusEffects.wet;
			cacheLayer = CacheLayer.water;
			isLiquid = true;
			liquidDrop = Liquids.water;
		}};

		daciteTropWater = new Floor("trop-dacite-water"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;

			status = StatusEffects.wet;
			cacheLayer = CacheLayer.water;
			isLiquid = true;
			liquidDrop = Liquids.water;
		}};

		oilWater = new TileEffect("oil-water"){{
			variants = 3;
			speedMultiplier = 0.5f;
			liquidDrop = Liquids.oil;
			liquidMultiplier = 0.8f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 90f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;

			effect = Fx.ventSteam;
			effectColor = Color.valueOf("111316");
		}};

		oilSandWater = new TileEffect("oil-sand-water"){{
			speedMultiplier = 0.8f;
			liquidDrop = Liquids.oil;
			attributes.set(Attribute.oil, 0.8f);
			liquidMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;

			status = StatusEffects.wet;
			cacheLayer = CacheLayer.water;
			isLiquid = true;
			variants = 3;
			effect = Fx.ventSteam;
			effectColor = Color.valueOf("111316");
		}};

		oilTropWater = new TileEffect("trop-oil-water"){{
			variants = 3;
			speedMultiplier = 0.5f;
			liquidDrop = Liquids.oil;
			liquidMultiplier = 0.8f;
			isLiquid = true;
			status = StatusEffects.wet;
			statusDuration = 90f;
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;

			effect = Fx.ventSteam;
			effectColor = Color.valueOf("111316");
		}};

		quickSand = new Floor("quick-sand"){{
			drownTime = 240f;
			speedMultiplier = 0.5f;
			variants = 3;
			//status = StatusEffects.slow; // does this even work (it just makes speedmulti 0.4)
			//statusDuration = 30f; // need some custom Vfx for being sandy
			attributes.set(Attribute.water, 0.8f);
			cacheLayer = CacheLayer.mud; //TODO custom shader for quick sands
            walkSound = Sounds.mud; // need some sfx for these
            walkSoundVolume = 0.08f;
            walkSoundPitchMin = 0.4f;
            walkSoundPitchMax = 0.5f;
		}};

		//end region

		//region Walls
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

		// TODO turn this to an wall ore instead
		oxideWall = new StaticWall("oxide-wall"){{
			variants = 3;
			itemDrop = Items.oxide;
		}};

		//end region

		//region ores and resources
        oreChlorophyte = new OreBlock("ore-chlorophyte", DustItems.chlorophyte){{
            oreDefault = false;
			variants = 3;

			emitLight = true;
            lightRadius = 70f;
            lightColor = Color.acid.cpy().a(0.3f);
        }};

		wallChlorophyte = new OreBlock("wall-chlorophyte", DustItems.chlorophyte){{
			wallOre = true;
			variants = 3;
		}};

		oreAquamarine = new OreBlock("ore-aquamarine", DustItems.aquamarine){{
			oreDefault = false;
			variants = 3;

			emitLight = true;
            lightRadius = 70f;
            lightColor = Color.cyan.cpy().a(0.3f);
		}};
		//end region

	}

}