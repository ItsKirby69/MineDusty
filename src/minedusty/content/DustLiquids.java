package minedusty.content;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.type.Liquid;

public class DustLiquids {
	public static Liquid 
	juice, swampjuice, nitroplastSolution, greenGoo;

	//temp
	public static void load(){
		juice = new Liquid("juice", Color.valueOf("0bcc80")){{
            heatCapacity = 0.4f;
            effect = StatusEffects.wet;
            boilPoint = 0.5f;
            gasColor = Color.valueOf("cdfedc");
            alwaysUnlocked = true;
        }};
	}
}