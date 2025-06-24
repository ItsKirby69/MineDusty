package minedusty.content;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.*;
import mindustry.world.meta.*;
import minedusty.content.weathers.StormWeather;

public class DustWeathers {
	public static Weather heavyRain, testWeather1, testWeather2;

	public static void load() {

		//heavy rain and we need thunder storm aswell TODO: make heavy rain more heavier and darker
		heavyRain = new StormWeather("heavy-rain"){{
			yspeed = 13f;
			xspeed = 10f;
			density = 700f;
			stroke = 1.2f;

			attrs.set(Attribute.light, -0.7f);
			attrs.set(Attribute.water, 0.5f);
			//attrs.set(Attribute., id);)

			color = Color.valueOf("5e6fa5ff");

			status = StatusEffects.wet;
			sound = Sounds.rain;
			force = 0.6f;

			soundVol = 0.65f;
		}};
	}
}
