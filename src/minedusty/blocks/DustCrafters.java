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
import minedusty.content.DustItems;
import minedusty.content.DustLiquids;

public class DustCrafters {
	// Refineries
	public static Block silicaFurnace;

	// Extractors/Mixers
	public static Block salinator;

	// I'VE GOT THE POWER!
	public static Block solarPanel, largesolarPanel;


	// Legacy crafters
	public static Block nitroplastChamber, bioLiquidMixer, bioFuelCombustionChamber, miniCrusher;
	
	public static void loadContent() {
		//region Crafters
		//silicaFurnace = new 

		salinator = new AttributeCrafter("salinator"){{
			requirements(Category.production, with(oxidecopper, 45, Items.lead, 50, aquamerium, 25));
			outputLiquid = new LiquidStack(saltWater, 12f/60f);
			craftTime = 100f;
			baseEfficiency = 0f;
			size = 2;
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
		// Crafts bioFuel using bioLiquid, Oxygen and Nitrogen
		nitroplastChamber = new HeatCrafter("nitroplast-chamber"){{
			requirements(Category.crafting, with(Items.titanium, 110, Items.silicon, 50, Items.metaglass, 30));
			size = 3;
			warmupSpeed = 0.01f;
			craftTime = 225;
			hasLiquids = true;
			liquidCapacity = 25;
			craftEffect = Fx.smokeCloud;
			heatRequirement = 4f;
			maxEfficiency = 3.0f;
			consumeLiquids(LiquidStack.with(DustLiquids.bioLiquid, 30/60f, Liquids.nitrogen, 8/60f)); //TODO Oxygen needed
			outputLiquid = new LiquidStack(DustLiquids.bioFuel, 12/60f);
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawRegion("-rails"),
				new DrawLiquidTile(Liquids.nitrogen),
				new DrawLiquidTile(DustLiquids.bioLiquid),
				new DrawCells(){{
					recurrence = 3.5f;
					radius = 2f;
					lifetime = 200;
					range = 9.5f;
					particles = 70;
					color = Color.valueOf("489e0e");
					particleColorFrom = Color.valueOf("92c80b");
					particleColorTo = Color.valueOf("07b142");
				}},
				new DrawBubbles(){{
					amount = 60;
					sides = 8;
					spread = 9;
					color = Color.valueOf("b0ff00");
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
