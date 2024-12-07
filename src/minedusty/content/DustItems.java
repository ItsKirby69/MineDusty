package minedusty.content;

import minedusty.graphics.*;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

public class DustItems {
	
	//Ores
	public static Item chlorophyte, aquamarine, quartz, rosequartz, dustquartz;

	//Other stuff
	public static Item carbonicWaste;

	public static final Seq<Item> dustItems = new Seq<>();
	
	public static void load(){

		//ore items
		chlorophyte = new Item("item-chlorophyte", Color.valueOf("89ca1e")){{
			frames = 7;
			hardness = 3;
			cost = 1f;
		}};

		aquamarine = new Item("item-aquamarine", Color.valueOf("a4ecfd")){{
			hardness = 3;
			cost = 1f;
		}};
		
		carbonicWaste = new Item("item-carbonicwaste", Color.valueOf("2f241a")){{}};
		
		//quartz can be made using silicon and a machine. idk what yet.
		quartz = new Item("ore-quartz", pal2.quartz){{
			flammability = 0.25f;
			hardness = 2;
		}};
		//quartz needs a machine to create pink quartz.
		rosequartz = new Item("ore-pink-quartz", pal2.rosequartz){{
			flammability = 0.25f;
			hardness = 2;
		}};
		//crushed quartz
		dustquartz = new Item("quartz-dust", pal2.dustquartz){{
		}};

	}
}