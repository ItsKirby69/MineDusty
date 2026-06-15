package minedusty.content;

import minedusty.graphics.*;
import arc.graphics.Color;
import mindustry.type.Item;

public class DustItems {
	// NOTE TO SELF: It seems that item sprites require to have little to no transparent margins.
	// The game doesn't automatically resizes the sprites so textures with margins make the item appear
	// Much smaller than you'd expect.
	// Annealed Copper? Gangue? 

	// Theia
	public static Item oxidecopper, salt, silicadust, carbonicWaste, aquamerium, chlorophyte, amethyst;
	public static Item bioplastic, galena, gold, silver, electrum, divinityMatter;
	
	// Nautilune
	public static Item aluminum, silumin;
	
	//Potential items
	public static Item sediment;
	
	public static void load(){
		sediment = new Item("item-sediment", Color.valueOf("#dbbe94")){{
		}};

		silumin = new Item("item-silumin", Color.valueOf("#8ba1a1")){{
			cost = 1.1f;
		}};
		aluminum = new Item("item-aluminum", Color.valueOf("#909fc9")){{
			cost = 1.1f;
			hardness = 3;
		}};

		galena = new Item("item-galena", Color.valueOf("#9492a7")){{
			hardness = 1;
		}};
		
		amethyst = new Item("item-amethyst", Color.valueOf("#caa4ff")){{
			hardness = 1;
		}};
		
		salt = new Item("item-salt", Color.valueOf("#c4e7e9")){{
			cost = 0.5f;
		}};

		oxidecopper = new Item("item-oxidecopper", DustPalette.oxidecopper){{
			hardness = 1;
			cost = 0.5f;
			alwaysUnlocked = true;
		}};

		chlorophyte = new Item("item-chlorophyte", DustPalette.chlorophyte){{
			cost = 0.5f;
			hardness = 2;
			//frames = 7;
		}};

		aquamerium = new Item("item-aquamerium", DustPalette.aquamerium){{
			cost = 2f;
			hardness = 3;
		}};

		silicadust = new Item("item-silicadust", Color.valueOf("#918e96")){{
			explosiveness = 0.7f; // lol
		}};

		gold = new Item("item-gold", Color.valueOf("#ffd641")){{}};
		silver = new Item("item-silver", Color.valueOf("#bfcadd")){{
			cost = 1.5f;
		}};
		electrum = new Item("item-electrum", Color.valueOf("#bea558")){{}};
		bioplastic = new Item("item-bioplastic", Color.valueOf("#ffffff")){{
		}};

		divinityMatter = new Item("item-divinity-matter", DustPalette.divinityMatter){{
		}};
		
		carbonicWaste = new Item("item-carbonicwaste", DustPalette.carbonicWaste){{
			flammability = 1.5f;
			explosiveness = 0.4f;
		}};
	}
}
