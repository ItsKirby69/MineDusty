package minedusty.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;
import minedusty.content.DustUnitTypes;
import minedusty.world.blocks.defense.ChloroMenderProjector;
import minedusty.world.blocks.logic.PetroglyphBlock;
import minedusty.world.blocks.power.LanternBlock;
import mindustry.content.Items;
import mindustry.graphics.*;

import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustLiquids.*;

import arc.graphics.Color;

public class DustCore {
	public static Block coreNest;
	public static Block stockpile;
	public static Block chloroMender;
	public static Block lantern;

	// Units (maybe different class?)
	public static Block skyFactory, earthFactory;
	
	// Logic
	public static Block petroglyphBlock;

	public static void loadContent() {

		petroglyphBlock = new PetroglyphBlock("petroglyph-block"){{
			requirements(Category.logic, with(silicadust, 12));
			size = 1;
		}};

		((UnitFactory)groundFactory).plans.add(new UnitFactory.UnitPlan(DustUnitTypes.cleave, 60f * 20, with(silicon, 15, lead, 20)));
		((UnitFactory)navalFactory).plans.add(new UnitFactory.UnitPlan(DustUnitTypes.minnow, 60f * 32, with(silicon, 15, metaglass, 18)));
		((Reconstructor)additiveReconstructor).addUpgrade(DustUnitTypes.minnow, DustUnitTypes.sturgeon);

		skyFactory = new UnitFactory("sky-factory"){{
            requirements(Category.units, with(oxidecopper, 70, lead, 150, silicon, 80));
			researchCost = with(oxidecopper, 1200, lead, 1000, silicon, 1000);
			plans.add(new UnitPlan(DustUnitTypes.dazzle, 60f * 17, with(silicon, 12, graphite, 5)));
            size = 3;
			configurable = false;
            consumePower(1.2f);
            researchCostMultiplier = 0.5f;
		}};

		earthFactory = new UnitFactory("earth-factory"){{
            requirements(Category.units, with(oxidecopper, 60, lead, 50, silicon, 60));
			researchCost = with(oxidecopper, 1200, lead, 1000, silicon, 1000);
			plans.add(new UnitPlan(DustUnitTypes.bulbus, 60f * 30, with(silicon, 25, chlorophyte, 30, lead, 15)));
            size = 3;
			configurable = false;
            consumePower(1.2f);
            researchCostMultiplier = 0.5f;
		}};

		stockpile = new StorageBlock("stockpile"){{
			requirements(Category.effect, with(chlorophyte, 50, silicon, 250));
			researchCost = with(chlorophyte, 500, silicon, 450);
			size = 3;
			itemCapacity = 2500;
			scaledHealth = 75;
		}};

        chloroMender = new ChloroMenderProjector("chloromender"){{
            requirements(Category.effect, with(chlorophyte, 30, graphite, 15));
			researchCost = with(chlorophyte, 200, oxidecopper, 1200, graphite, 250);
			consumePower(20f / 60f);
            consumeLiquid(bioLiquid, 4.5f / 60f).boost();
            size = 1;
            range = 6;
            healPercent = 2.5f / 60f;
            optionalMultiplier = 2f;
            health = 100;

			baseColor = Color.valueOf("#c5ff98");
            Color col = Color.valueOf("#a3d72b");

            drawer = new DrawMulti(new DrawDefault(), new DrawPulseShape(false){{
                layer = Layer.effect;
                color = col;
            }});
        }};

        lantern = new LanternBlock("lantern"){{
            requirements(Category.effect, BuildVisibility.lightingOnly, with(Items.graphite, 10, Items.lead, 12));
			researchCost = with(graphite, 200, lead, 50);
        }};

		coreNest = new CoreBlock("core-nest"){{
			requirements(Category.effect, with(oxidecopper, 1300, lead, 1000));
			alwaysUnlocked = true;

			isFirstTier = true;
			unitType = DustUnitTypes.cricket;
			health = 2500;
			itemCapacity = 5500;
			size = 3;
			buildCostMultiplier = 1.5f;

			unitCapModifier = 10;
			//armor = 5f;
		}};

	}
}
