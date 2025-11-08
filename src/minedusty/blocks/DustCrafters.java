package minedusty.blocks;

import static mindustry.type.ItemStack.with;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustLiquids.*;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import minedusty.DustAttributes;
import minedusty.content.*;
import minedusty.graphics.*;
import minedusty.world.blocks.production.*;

public class DustCrafters {
	// Refineries
	// A sintering machine for high melting point metals?
	public static Block silicaForge, carbonicPress;
	public static Block carbonicConcentrator;

	// Extractors/Mixers
	public static Block salinator;
	public static Block crystalCrusher;
	public static Block bioSludgeChamber;

	// Legacy crafters
	public static Block nitroplastChamber, bioLiquidMixer, bioFuelCombustionChamber, miniCrusher;
	
	public static void loadContent() {
		//region Crafters
		carbonicConcentrator = new GenericCrafter("carbonic-concentrator"){{
			requirements(Category.crafting, with(oxidecopper, 30, Items.lead, 50));
			researchCost = with(oxidecopper, 100);
			craftTime = 175f;
			size = 2;
			health = 340;
			outputItem = new ItemStack(carbonicWaste, 2);

			craftEffect = Fx.none; //DustyEffects.carbonicDust;
			
			consumeItem(Items.coal, 4);
			consumePower(60f/60f);

			// TODO fix the missing texture issue here
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawPistons(){{
					sinMag = 2f;
				}},
				new DrawParticles() {{
					color = DustPalette.carbon;
					alpha = 0.3f;
					particleSize = 2.5f;
					particles = 14;
					particleRad = 6f;
					rotateScl = 3f;
					particleLife = 60f;
				}},
				new DrawDefault()
			);
		}};

        carbonicPress = new GenericCrafter("carbonic-press"){{
            requirements(Category.crafting, with(oxidecopper, 80, Items.lead, 35));
			researchCost = with(oxidecopper, 220, Items.lead, 150);
            craftEffect = Fx.pulverizeMedium;
            outputItem = new ItemStack(Items.graphite, 1);
            craftTime = 75f;
            size = 2;
			health = 260;
			
			drawer = new DrawMulti(
				new DrawDefault(), 
				new DrawPress("-top"){{
				}}
			);
            consumeItem(carbonicWaste, 1);
        }};

        silicaForge = new GenericCrafter("silica-forge"){{
            requirements(Category.crafting, with(oxidecopper, 35, Items.lead, 40));
			researchCost = with(oxidecopper, 600, Items.lead, 200);

            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(Items.silicon, 2);
            craftTime = 60f;
            size = 2;
			health = 400;
            hasPower = true;
            hasLiquids = false;

            drawer = new DrawMulti(
				new DrawDefault(), 
				new DrawFlame(Color.valueOf("#d399ffff")) //ffef99
			); 
            
				ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

            consumeItems(with(carbonicWaste, 1, silicadust, 3));
            consumePower(35f/ 60f);
        }};

        crystalCrusher = new WallCrafter("crystal-crusher"){{
            requirements(Category.production, with(Items.graphite, 15, Items.lead, 30));
            researchCost = with(oxidecopper, 350, Items.graphite, 100);
			consumePower(13 / 60f);

            drillTime = 160f;
            size = 2;
			health = 200;
            attribute = DustAttributes.crystal;
            output = DustItems.silicadust;
            fogRadius = 2;
            ambientSound = Sounds.drill;
            ambientSoundVolume = 0.04f;
        }};

		salinator = new AttributeCrafter("salinator"){{
			requirements(Category.crafting, with(oxidecopper, 45, Items.lead, 25, chlorophyte, 50));
			researchCost = with(Items.lead, 350, chlorophyte, 550, oxidecopper, 200);
			outputLiquid = new LiquidStack(saltWater, 12f/60f);
			craftTime = 100f;
			baseEfficiency = 0f;
			size = 2;
			health = 320;
			maxBoost = 1.25f;
			hasItems = true;
			hasLiquids = true;
			liquidCapacity = 60f;
			
			craftEffect = Fx.none;
			attribute = DustAttributes.salt;

			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(Liquids.water),
				new DrawLiquidTile(saltWater),
				new DrawDefault()
			);

			//maxBoost = 2f;

			consumeLiquid(Liquids.water, 12f/60f);
		}};

		// Maybe nitroplast chamber used in other crafts in the future. 
		bioSludgeChamber = new SolarAttributeCrafter("biosludge-chamber"){{
			requirements(Category.crafting, with(Items.silicon, 80, chlorophyte, 60, Items.graphite, 25));
			researchCost = with(chlorophyte, 500, Items.graphite, 200);
			size = 3;
			health = 450;
			warmupSpeed = 0.01f;
			craftTime = 425;
			hasLiquids = true;
			liquidCapacity = 25;
			craftEffect = DustyEffects.steam;
			outputItem = new ItemStack(carbonicWaste, 1);

			minEfficiency = 0.5f;
			minSolar = 0.5f;
			maxBoost = 2f;
			dumpExcess = true;

			consumeLiquids(LiquidStack.with(Liquids.water, 12f/60f));
			consumeItem(chlorophyte, 2);
			outputLiquid = new LiquidStack(DustLiquids.bioLiquid, 12/60f);
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawRegion("-rails"),
				new DrawLiquidTile(DustLiquids.bioLiquid){{
					alpha = 0.5f;
				}},
				new DrawLiquidTile(Liquids.water){{
					alpha = 0.5f;
				}},
				new DrawBubbles(){{
					amount = 25;
					sides = 8;
					spread = 9;
					color = Color.valueOf("#417663ff");
				}},
				new DrawDefault(),
				new DrawHeatCrafterEff(){{
					minEfficiency = 0f;
				}}
			);
		}};

		//endregion
		//region Legacy Crafters
		
		// Crafts bioLiquid using water and chlorophyte
		bioLiquidMixer = new GenericCrafter("bioliquid-mixer"){{
			requirements(Category.crafting, with(Items.titanium, 60, Items.silicon, 70, Items.metaglass, 40));
			size = 3;
			craftTime = 120;
			hasLiquids = true;
			hasItems = true;
			hasPower = true;
			outputsLiquid = true;
			liquidCapacity = 65;
			lightLiquid = DustLiquids.bioLiquid;
			consumeLiquid(Liquids.water, 24f / 60);
			consumePower(1f);
			consumeItem(DustItems.chlorophyte, 2);
			outputLiquid = new LiquidStack(DustLiquids.bioLiquid, 24 / 60f);
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(Liquids.water),
				new DrawLiquidTile(DustLiquids.bioLiquid){{drawLiquidLight = true;}},
				new DrawBubbles(){{
					amount = 35;
					sides = 8;
					spread = 8;
					timeScl = 55f;
					color = Color.valueOf("07b142");
				}},
				new DrawDefault()
			);
		}};

		// Generates Power using bioFuel and coal (might need to change this) also needs a byproduct
		bioFuelCombustionChamber = new ConsumeGenerator("biofuel-combustion-chamber"){{
			requirements(Category.power, with(DustItems.chlorophyte, 110, Items.titanium, 60, Items.metaglass, 45, Items.silicon, 80));
			size = 2;
			health = 250;
			powerProduction = 280 / 60f;
			itemDuration = 160f;
			hasLiquids = true;
			hasItems = true;
			generateEffect = Fx.generatespark;
			consumeLiquid(DustLiquids.bioFuel, 8 / 60f);
			consume(new ConsumeItemFlammable());
			consume(new ConsumeItemExplode());
			
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawRegion("-spein"){{
					spinSprite = true;
					rotateSpeed = 10f;
				}},
				new DrawLiquidTile(DustLiquids.bioFuel){{alpha = 0.4f;}},
				new DrawSoftParticles(){{
					particleLife = 75;
					particleSize = 4.5f;
					particleRad = 9;
					alpha = 0.40f;
					particles = 25;
					color = Color.valueOf("b0ff00");
					color2 = Color.valueOf("ffb000");
				}},
				new DrawDefault()
			);
		}};

		// Mines chlorophyte from walls //TODO laster drill for oxide resource on oxide walls
	    miniCrusher = new WallCrafter("mini-wall-crusher"){{
            requirements(Category.production, with(Items.graphite, 25, Items.copper, 15));
            consumePower(18 / 60f);

            drillTime = 160f;
            size = 1;
            attribute = DustAttributes.chlorophyte;
            output = DustItems.chlorophyte;
            researchCost = with(Items.copper, 100, Items.graphite, 40);
            ambientSound = Sounds.drill;
            ambientSoundVolume = 0.04f;
        }};
		//endregion
	}
}
