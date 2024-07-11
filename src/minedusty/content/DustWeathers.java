package minedusty.content;

import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.*;
import mindustry.world.meta.*;

public class DustWeathers {
	public static Weather heavyRain, testWeather1, testWeather2;

	public static void load() {

		//heavy rain and we need thunder storm aswell TODO: make heavy rain more heavier and darker
		heavyRain = new RainWeather("heavy-rain"){{
			yspeed = 12f;
			xspeed = 4f;
			density = 2100f;
			stroke = 1.2f;

			attrs.set(Attribute.light, -3f);
			attrs.set(Attribute.water, 0.5f);
			//attrs.set(Attribute., id);)
			status = StatusEffects.wet;
			sound = Sounds.rain;
			//how can I use both sounds without creating a new sfx
			//sound = Sounds.windhowl;
			soundVol = 0.65f;
		}};

		

	}
}
