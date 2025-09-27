package minedusty.content;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.type.CellLiquid;
import mindustry.type.Liquid;

public class DustLiquids {
	public static Liquid bioLiquid, bioFuel, sap, saltWater; 
	//TODO oxygen. Thinking about having oxygen compatible with other mod's oxygen? Or maybe just use ozone for recipies

	//temp
	public static void load(){
		bioLiquid = new Liquid("bio-liquid", Color.valueOf("#05dd91")){{
			effect = StatusEffects.wet;
			boilPoint = 0.5f;
			lightColor = Color.valueOf("#02ddac").a(0.45f);
            gasColor = Color.valueOf("#a9f2e4");
        }};
		bioFuel = new Liquid("bio-fuel", Color.valueOf("#92c80b")){{
			flammability = 0.5f;
			lightColor = Color.valueOf("#60ae1f").a(0.3f);
			gasColor = Color.valueOf("#d8ff8e");
			viscosity = 0.60f;
		}};
		sap = new CellLiquid("liquid-sap", Color.valueOf("#ff9100")){{
			effect = StatusEffects.sapped;
			colorFrom = Color.valueOf("#873f05");
            colorTo = Color.valueOf("#e8b30f");
			viscosity = 0.75f;
            flammability = 0.75f;
		}};

		saltWater = new Liquid("liquid-salt-water", Color.valueOf("#a2c9e4ff")){{}};
	}
}