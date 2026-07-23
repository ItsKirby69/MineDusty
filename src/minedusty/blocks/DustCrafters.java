package minedusty.blocks;

import static mindustry.type.ItemStack.with;
import static mindustry.content.Items.*;
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
import mindustry.world.meta.*;
import minedusty.DustAttributes;
import minedusty.content.*;
import minedusty.graphics.*;
import minedusty.world.blocks.production.*;

public class DustCrafters {
	// Refineries
	// A sintering machine for high melting point metals?
	public static Block silicaForge, carbonicPress;
	public static Block carbonicConcentrator, carbonicRefinery;
	public static Block brineElectrolyzer, atmosphericConcenerator;

	// Extractors/Mixers
	public static Block oilTap;
	public static Block salinator, fluidBed;
	public static Block bioSludgeChamber, chlorophyteCultivator;
	public static Block sifter; // TODO for gold

	// Legacy crafters
	public static Block nitroplastChamber, bioLiquidMixer, bioFuelCombustionChamber, miniCrusher;
	
	public static void loadContent(){
		//region Crafters
		oilTap = new Fracker("oil-tap"){{
            requirements(Category.production, with(aquamerium, 30, graphite, 50, lead, 30, oxidecopper, 20));
            researchCost = with(graphite, 300, aquamerium, 200);
			result = Liquids.oil;
			updateEffect = Fx.pulverize;
            pumpAmount = 4f/60f;
            size = 2;
            liquidCapacity = 16f;
            rotateSpeed = 1.4f;
			itemUseTime = 120f;
			baseEfficiency = 0f;
            attribute = Attribute.oil;
			
			consumeItem(sand);
			consumePower(45f/60f);
        }};

		carbonicConcentrator = new GenericCrafter("carbonic-concentrator"){{
			requirements(Category.crafting, with(oxidecopper, 30, Items.lead, 50));
			researchCost = with(oxidecopper, 100);
			craftTime = 180f;
			size = 2;
			health = 340;
			outputItem = new ItemStack(carbonicWaste, 2);

			craftEffect = Fx.none; //DustyEffects.carbonicDust;
			
			consumeItem(Items.coal, 4);
			consumePower(60f/60f);

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
				new DrawFlame(Color.valueOf("#d399ff")) //ffef99
			); 
            
			ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.07f;

            consumeItems(with(carbonicWaste, 1, silicadust, 3));
			// consumeLiquid(sodiumHydroxide, 6f/ 60f).boost();
            consumePower(35f/ 60f);
        }};

		fluidBed = new SolarCrafter("fluid-bed"){{
			requirements(Category.crafting, with(Items.lead, 45, Items.graphite, 40));
			researchCost = with(Items.graphite, 400);
			outputItem = new ItemStack(salt, 2);
			craftEffect = DustyEffects.meltSteam;
			craftSound = DustSounds.steam;
			size = 2;
			health = 250;
			minSolar = 0.8f;
			craftTime = 360f;

            drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(saltWater){{
					drawLiquidLight = true;
				}},
				new DrawDefault(), //ffef99
				new DrawSolarHeat(0f)
			);

			consumeLiquid(saltWater, 6f/ 60f);
		}};
		
		salinator = new AttributeCrafter("salinator"){{
			requirements(Category.crafting, with(oxidecopper, 45, Items.lead, 65));
			researchCost = with(Items.lead, 150, oxidecopper, 200);
			outputLiquid = new LiquidStack(saltWater, 12f/60f);
			craftTime = 120f;
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
				new DrawLiquidTile(saltWater){{
					drawLiquidLight = true;
				}},
				new DrawDefault()
			);

			consumeLiquid(Liquids.water, 12f/60f);
		}};

		carbonicRefinery = new GenericCrafter("carbonic-refinery"){{
			requirements(Category.crafting, with(oxidecopper, 130, chlorophyte, 50, Items.graphite, 75, Items.silicon, 120, Items.lead, 110));
			researchCost = with(Items.graphite, 400, Items.silicon, 300, chlorophyte, 1000, oxidecopper, 500);
			craftTime = 250f;
			size = 3;
			health = 850;
			outputItem = new ItemStack(carbonicWaste, 10);

			craftEffect = Fx.none; // WIP
			ambientSound = Sounds.loopHum;

			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(Liquids.oil){{
					alpha = 0.7f;
				}},
				new DrawSoftParticles(){{
					alpha = 0.3f;
					particleRad = 12f;
					particleSize = 9f;
					particleLife = 120f;
					particles = 27;
					color = Color.valueOf("#986fe3");
					color2 = Color.valueOf("#74688b");
				}},
				new DrawParticles() {{
					color = DustPalette.carbon;
					alpha = 0.5f;
					particleSize = 3.5f;
					particles = 17;
					particleRad = 6f;
					rotateScl = 3f;
					particleLife = 75f;
				}},
				new DrawDefault(),
				new DrawGlowRegion(){{
					alpha = 0.25f;
				}}
			);
			consumePower(90f/60f);
			consumeItem(lead, 5);
			consumeLiquids(LiquidStack.with(Liquids.oil, 12f/60f, Liquids.hydrogen, 6f/60f));
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
			ambientSound = Sounds.loopCultivator;

			minEfficiency = 0.5f;
			minSolar = 0.5f;
			maxBoost = 2f;
			dumpExcess = true;

			consumeLiquids(LiquidStack.with(Liquids.water, 14f/60f));
			outputLiquid = new LiquidStack(DustLiquids.bioLiquid, 12/60f);
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawHeatCrafterEff(0f),
				new DrawLiquidTile(DustLiquids.bioLiquid){{
					alpha = 0.5f;
				}},
				new DrawLiquidTile(Liquids.water){{
					alpha = 0.25f;
				}},
				new DrawAlphaBubbles(){{
					amount = 25;
					sides = 8;
					spread = 9;
					color = Color.valueOf("#3dcfb0");
				}},
				new DrawDefault()
			);
		}};

		chlorophyteCultivator = new SolarAttributeCrafter("chlorophyte-cultivator"){{
			requirements(Category.crafting, with(Items.silicon, 40, graphite, 55, oxidecopper, 75, amethyst, 40));
			researchCost = with(graphite, 500, amethyst, 500);
			craftEffect = DustyEffects.meltSteam;
			size = 3;
			health = 550;
			outputItem = new ItemStack(chlorophyte, 3);
			craftTime = 60f * 8;

			minSolar = 0.5f;
			baseEfficiency = 0.5f;
			minEfficiency = 0f;
			scaleLiquidConsumption = false;
			attribute = DustAttributes.turf;

			consumeItems(with(carbonicWaste, 3, amethyst, 3));
			consumeLiquid(bioLiquid, 16f/60);

			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawHeatCrafterEff(0f),
				new DrawLiquidTile(DustLiquids.bioLiquid, 2f){{
					alpha = 0.5f;
				}},
				new DrawAlphaBubbles(){{
					amount = 25;
					sides = 8;
					spread = 9;
					color = Color.valueOf("#3dcfb0");
				}},
				new DrawRegion()
			);
		}};
		
		brineElectrolyzer = new GenericCrafter("brine-electrolyzer"){{
            requirements(Category.crafting, with(Items.silicon, 50, Items.graphite, 70, oxidecopper, 130, amethyst, 60));
            size = 3;

            researchCostMultiplier = 1.2f;
            craftTime = 10f;
            rotate = true;
            invertFlip = true;
            group = BlockGroup.liquids;
            itemCapacity = 0;

            liquidCapacity = 60f;

            consumeLiquid(DustLiquids.saltWater, 12f / 60f);
            consumePower(120f/60f);

			outputLiquids = new LiquidStack[]{
				new LiquidStack(Liquids.hydrogen, 6f/60f),
				new LiquidStack(DustLiquids.chlorine, 6f/60f),
				new LiquidStack(DustLiquids.sodiumHydroxide, 12f/60f)
			};

            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(DustLiquids.saltWater, 2f),
                new DrawBubbles(Color.valueOf("#bac7eb")){{
                    sides = 10;
                    recurrence = 3f;
                    spread = 6;
                    radius = 1.5f;
                    amount = 20;
                }},
                new DrawRegion(),
				new DrawGlowRegion(){{
                    alpha = 0.7f;
                    color = Color.valueOf("#c4bdf3");
                    glowIntensity = 0.3f;
                    glowScale = 6f;
                }},
                new DrawLiquidOutputs()
            );

            ambientSound = Sounds.loopElectricHum;
            ambientSoundVolume = 0.08f;

            regionRotated1 = 3;
            liquidOutputDirections = new int[]{1, 2, 3};
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
					color = Color.valueOf("#b0ff00");
					color2 = Color.valueOf("#ffb000");
				}},
				new DrawDefault()
			);
		}};
		//endregion
	}
}
