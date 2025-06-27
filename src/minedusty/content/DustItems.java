package minedusty.content;

import minedusty.graphics.*;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.type.Item;

public class DustItems {
	
	//Ores
	public static Item chlorophyte, aquamarine, divinityMatter, quartz, rosequartz, dustquartz;

	//Other stuff
	public static Item carbonicWaste;

	public static final Seq<Item> terraItems = new Seq<>();
	
	public static void load(){

		//ore items
		chlorophyte = new Item("item-chlorophyte", DustPalette.chlorophyte){{
			//frames = 7;
			hardness = 6;
			cost = 1f;
		}};

		aquamarine = new Item("item-aquamarine", DustPalette.aquamarine){{
			hardness = 3;
			cost = 1f;
		}};

		divinityMatter = new Item("item-divinity-matter", DustPalette.divinityMatter){{
			cost = 1.3f;
			explosiveness = 0.15f;
			flammability = 0.7f;
		}};
		
		carbonicWaste = new Item("item-carbonicwaste", DustPalette.carbonicWaste){{}};
		
		//quartz can be made using silicon and a machine. idk what yet.
		quartz = new Item("ore-quartz", DustPalette.quartz){{
			flammability = 0.25f;
			hardness = 2;
		}};
		//quartz needs a machine to create pink quartz.
		rosequartz = new Item("ore-pink-quartz", DustPalette.rosequartz){{
			flammability = 0.25f;
			hardness = 2;
		}};
		//crushed quartz
		dustquartz = new Item("quartz-dust", DustPalette.dustquartz){{
		}};

		terraItems.addAll(
			chlorophyte, aquamarine, divinityMatter, carbonicWaste,
			quartz, rosequartz, dustquartz, 
			//serpulo + erkir stuff aswell that are present
			Items.scrap, Items.copper, Items.lead, Items.graphite, Items.coal, Items.silicon, Items.sand, Items.metaglass, 
			Items.oxide //contemplating between adding or not adding carbide for future plans.
		);
	}
}