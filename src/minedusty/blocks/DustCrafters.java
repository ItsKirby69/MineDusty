package minedusty.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawLiquidRegion;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import minedusty.content.DustItems;
import minedusty.content.DustLiquids;

public class DustCrafters {
	public static Block nitroplastChamber;
	
	public static void loadContent() {
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
	}
}