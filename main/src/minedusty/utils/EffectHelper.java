package minedusty.utils;

import arc.graphics.Color;
import mindustry.entities.Effect;

public class EffectHelper {
	/** Allows passthrough for color on given effects. 
     * Made specifically for destroyEffect for trees which apparently
     * doesn't pass the mapColor to the effect??/./?.
     */
	public static Effect withColor(Effect originalEffect, Color color){
		return new Effect(originalEffect.lifetime, e -> {
			e.color = color;
			originalEffect.renderer.get(e);
		}).layer(originalEffect.layer);
	}
}
