package minedusty.content;

import minedusty.graphics.*;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.type.Item;

public class DustItems {
	// Annealed Copper?
	//Ores
	public static Item galena, oxidecopper, salt, amethyst, bioplastic, electrum, gold, silver, silicadust;
	public static Item chlorophyte, aquamerium, divinityMatter, quartz, rosequartz, dustquartz;

	//Other stuff
	public static Item carbonicWaste;

	public static final Seq<Item> theiaItems = new Seq<>();
	
	public static void load(){

		//ore items
		galena = new Item("item-galena", Color.valueOf("#9492a7")){{
			hardness = 1;
		}};
		
		amethyst = new Item("item-amethyst", Color.valueOf("#caa4ff")){{
			hardness = 1;
		}};
		
		salt = new Item("item-salt", Color.valueOf("e2f0f1")){{
			cost = 0.5f;
		}};

		oxidecopper = new Item("item-oxidecopper", DustPalette.oxidecopper){{
			hardness = 1;
			cost = 0.5f;
			alwaysUnlocked = true;
		}};

		chlorophyte = new Item("item-chlorophyte", DustPalette.chlorophyte){{
			//frames = 7;
			hardness = 6;
		}};

		aquamerium = new Item("item-aquamerium", DustPalette.aquamerium){{
			hardness = 3;
			cost = 1f;
		}};

		// products
		silicadust = new Item("item-silicadust", Color.valueOf("#918e96")){{}};
		gold = new Item("item-gold", Color.valueOf("#ffd641")){{}};
		silver = new Item("item-silver", Color.valueOf("#bfcadd")){{
			cost = 1.5f;
		}};
		electrum = new Item("item-electrum", Color.valueOf("#bea558")){{}};
		bioplastic = new Item("item-bioplastic", Color.valueOf("#ffffff")){{
		}};

		divinityMatter = new Item("item-divinity-matter", DustPalette.divinityMatter){{
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

		theiaItems.addAll(
			chlorophyte, aquamerium, divinityMatter, carbonicWaste,
			quartz, rosequartz, dustquartz, 
			Items.scrap, Items.copper, Items.lead, Items.graphite, Items.coal, Items.silicon, Items.sand, Items.metaglass, 
			Items.oxide //contemplating between adding or not adding carbide for future plans.
		);
	}
}
