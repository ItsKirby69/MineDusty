package minedusty.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;

import mindustry.*;
import mindustry.content.*;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static minedusty.MineDusty.*;
import static mindustry.type.ItemStack.*;

public class DustBlocks {
	public static Block 
	//Turrets
	scatterSilo, 
	//Walls
	//Drills
	//Productions
	grinder,
	//Props
	largeBoulder, aliveTree,
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

        quartzSmelter = new GenericCrafter("quartz-smelter"){{
            requirements(Category.crafting, with(Items.copper, 30, Items.lead, 25, Items.silicon, 50));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(DustItems.rosequartz, 1);
            craftTime = 40f;
            size = 2;
            hasPower = true;
            hasLiquids = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

            consumeItems(with(Items.coal, 1, Items.silicon, 2, Items.thorium, 1));
            consumePower(0.50f);
        }};
	

		//deco blocks
		aliveTree = new TreeBlock("alive-tree");

		//ores
		oreQuartz = new OreBlock("ore-quartz", DustItems.quartz){{
			oreDefault = false;
            oreThreshold = 0.847f;
            oreScale = 25.580953f;
			variants = 3;
		}};

		
	}
}