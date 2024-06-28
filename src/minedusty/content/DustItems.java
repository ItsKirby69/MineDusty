package minedusty.content;

import minedusty.graphics.*;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

public class DustItems {
	public static Item 
	//from ores
	chlorophyte,
	
	quartz, rosequartz, dustquartz;

	public static final Seq<Item> dustItems = new Seq<>();

	public static void load(){

		//ore items
		// TODO need to check why item sprite not showing up/ why do vanilla call it with item- prefix
		chlorophyte = new Item("item-chlorophyte", Color.valueOf("89ca1e")){{
			hardness = 3;
			cost = 1f;
			//should i add radioactivity or something funny
		}};


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