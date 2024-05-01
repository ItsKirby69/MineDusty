package minedusty.content;

import minedusty.graphics.*;
import mindustry.type.Item;

public class DustItems {
	public static Item quartz, rosequartz, dustquartz;

	public static void load(){
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