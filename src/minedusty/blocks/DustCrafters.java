package minedusty.blocks;

import static mindustry.type.ItemStack.with;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.gen.Drawc;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.WallCrafter;
import mindustry.world.consumers.ConsumeItemExplode;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.draw.DrawBubbles;
import mindustry.world.draw.DrawCells;
import mindustry.world.draw.DrawCultivator;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawLiquidRegion;
import mindustry.world.draw.DrawLiquidTile;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import mindustry.world.draw.DrawSoftParticles;
import minedusty.DustAttributes;
import minedusty.content.DustItems;
import minedusty.content.DustLiquids;

public class DustCrafters {
	public static Block nitroplastChamber, bioLiquidMixer, bioFuelBlaster, miniCrusher;
	
	public static void loadContent() {
		// Crafts bioLiquid using water and chlorophyte
		bioLiquidMixer = new GenericCrafter("bioliquid-mixer"){{
			requirements(Category.production, with(Items.titanium, 60, Items.silicon, 70, Items.metaglass, 40));
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

		// Crafts bioFuel using bioLiquid, Oxygen and Nitrogen //TODO might use heat instead of power
		nitroplastChamber = new GenericCrafter("nitroplast-chamber"){{
			requirements(Category.production, with(Items.titanium, 110, Items.silicon, 50, Items.metaglass, 30));
			size = 3;
			warmupSpeed = 0.01f;
			craftTime = 225;
			hasLiquids = true;
			hasPower = true;
			liquidCapacity = 25;
			craftEffect = Fx.smokeCloud;
			consumeLiquids(LiquidStack.with(DustLiquids.bioLiquid, 30/60f, Liquids.nitrogen, 8/60f)); //TODO Oxygen needed
			consumePower(1.5f);
			outputLiquid = new LiquidStack(DustLiquids.bioFuel, 10/60f);
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

		// Generates Power using bioFuel and coal
		bioFuelBlaster = new ConsumeGenerator("biofuel-blaster"){{
			requirements(Category.power, with(DustItems.chlorophyte, 110, Items.titanium, 60, Items.metaglass, 45, Items.silicon, 80));
			size = 2;
			health = 250;
			powerProduction = 280 / 60f;
			itemDuration = 160f;
			hasLiquids = true;
			hasItems = true;
			generateEffect = Fx.generatespark;
			consumeLiquid(DustLiquids.bioFuel, 0.1f);
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
		// Mines chlorophyte from walls
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
	}
}
