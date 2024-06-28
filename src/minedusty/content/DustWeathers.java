package minedusty.content;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.*;
import mindustry.world.meta.*;

public class DustWeathers {
	public static Weather heavyRain, testWeather1, testWeather2;

	public static void load() {

		//heavy rain and we need thunder storm aswell
		heavyRain = new RainWeather("heavy-rain"){{
			yspeed = 8f;
			xspeed = 3f;
			density = 3000f;
			stroke = 1f;

			attrs.set(Attribute.light, -1f);
			attrs.set(Attribute.water, 0.5f);
			//attrs.set(Attribute., id);)
			status = StatusEffects.wet;
			sound = Sounds.rain;
			//how can I use both sounds without creating a new sfx
			//sound = Sounds.windhowl;
			soundVol = 0.55f;

		}};

		/*testWeather1 = new ParticleWeather("test-weather1"){{
            color = Color.valueOf("9bbbcc");
			noiseColor = Color.valueOf("305062");
            particleRegion = "particle";
            drawNoise = true;
            useWindVector = true;
            sizeMax = 5;
            sizeMin = 1;
            density = 1500f;
            baseSpeed = 8f;
            opacityMultiplier = 0.5f;
            force = 3f;
            soundVol = 0.65f;
		}};

		testWeather2 = new RainWeather("test-weather2"){{
            color = Color.valueOf("9bbbcc");
			xspeed = 0.2f;
			yspeed = 0.3f;
			density = 2000f;
			padding = 20f;
			sizeMin = 2f;
			sizeMax = 5f;
			splashTimeScale = 120f;
			status = StatusEffects.slow;
			attrs.set(Attribute.light, -0.4f);
		}};*/

	}
}
