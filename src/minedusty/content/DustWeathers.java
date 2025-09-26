package minedusty.content;

import arc.graphics.Color;
import arc.util.Time;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.meta.*;
import minedusty.content.weathers.StormWeather;

public class DustWeathers {
	public static Weather heavyRain, snowStorm, testWeather2;

	public static void load() {
		snowStorm = new ParticleWeather("snow-storm"){{
			color = noiseColor = Color.valueOf("e6feff");
			particleRegion = "particle";
			drawNoise = true;
			useWindVector = true;
			sizeMax = 90f;
			sizeMin = 12f;
			minAlpha = 0.4f;
			maxAlpha = 0.7f;
			density = 2200;
			baseSpeed = 7.0f;
			opacityMultiplier = 0.7f;
			force = 0.5f;
			sound = Sounds.windhowl;
			soundVol = 0.7f;
			soundVolOscMag = 1.5f;
			soundVolOscScl = 1100f;
			duration = 7f * Time.toMinutes;
			attrs.set(Attribute.light, -0.2f);
		}};

		//heavy rain and we need thunder storm aswell TODO: make heavy rain more heavier and darker
		heavyRain = new StormWeather("heavy-rain"){{
			yspeed = 13f;
			xspeed = 10f;
			density = 700f;
			stroke = 1.2f;

			attrs.set(Attribute.light, -1.2f);
			attrs.set(Attribute.water, 0.5f);

			color = Color.valueOf("5e6fa5ff");
			noiseColor = Color.valueOf("2E3752");
			particleRegion = "particle";
			drawNoise = true;
			useWindVector = true;
			sizeMax = 230f;
			sizeMax = 120f;
            minAlpha = 0.1f;
            maxAlpha = 0.25f;
			baseSpeed = 6.5f;
			opacityMultiplier = 0.35f;

			status = StatusEffects.wet;
			sound = Sounds.rain;
			force = 0.4f;
			soundVol = 0.75f;
			duration = 7f * Time.toMinutes;
		}};
	}
}
