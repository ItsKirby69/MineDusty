package minedusty.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import minedusty.world.blocks.environment.LivingBush;
import minedusty.world.blocks.environment.LivingTreeBlock;

import static mindustry.type.ItemStack.*;

//TODO, MAKE SURE TREE BLOCK IS VARIANT ONCE FIX IS MADE
public class DustBlocks {
	public static Block 
	//Turrets
	scatterSilo, 
	//Walls
	//Drills
	//Productions
	grinder, quartzSmelter,
	//Props
	largeBoulder, aliveTree, shrub, miniBushy, flower,
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

		aliveTree = new LivingTreeBlock("alive-tree"){{
			mapColor = Color.valueOf("74d660");
			//variants = 2;
		}};

		shrub = new LivingBush("shrub"){{
			mapColor = Color.valueOf("74d660");
			Blocks.grass.asFloor().decoration = Blocks.stone.asFloor().decoration = this;
			variants = 2;
			lobesMin = 20;
			magMin = 2;
			magMax = 3;
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

		grinder = new GenericCrafter("grinder"){{
            requirements(Category.crafting, with(Items.copper, 30, Items.lead, 25, Items.silicon, 50));
            outputItem = new ItemStack(DustItems.dustquartz, 1);
            craftEffect = Fx.pulverize;
            craftTime = 40f;
            updateEffect = Fx.pulverizeSmall;
            hasItems = hasPower = true;
            drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-rotator"){{
                spinSprite = true;
                rotateSpeed = 2f;
            }}, new DrawRegion("-top"));
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

            consumeItem(DustItems.quartz, 1);
            consumePower(0.50f);
        }};

        quartzSmelter = new AttributeCrafter("quartz-smelter"){{
            requirements(Category.crafting, with(Items.copper, 100, Items.metaglass, 50, Items.lead, 35, Items.silicon, 60));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(DustItems.rosequartz, 2);
            craftTime = 90f;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            itemCapacity = 20;
            boostScale = 0.15f;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

            consumeItems(with(Items.coal, 1, Items.silicon, 2, Items.thorium, 1));
            consumePower(2.50f);
        }};


		//ores
		oreQuartz = new OreBlock("ore-quartz", DustItems.quartz){{
			oreDefault = false;
            oreThreshold = 0.847f;
            oreScale = 25.580953f;
			variants = 3;
		}};

		
	}
}