package minedusty.content;

import arc.graphics.Color;
import arc.util.Time;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.meta.*;
import minedusty.content.weathers.StormWeather;
import minedusty.type.weather.CloudyWeather;

public class DustWeathers {
	public static Weather heavyRain, snowStorm, heatWave, clouds;

	public static void load() {
		// WIP
		clouds = new CloudyWeather("clouds"){{
			baseColor = Color.valueOf("#d3f5ff");
		}};

        heatWave = new ParticleWeather("heatwave"){{
            duration = 15f * Time.toMinutes;
            noiseLayers = 4;
            noiseLayerSclM = 0.8f;
            noiseLayerAlphaM = 0.6f;
            noiseLayerSpeedM = 1.7f;
            noiseLayerSclM = 0.6f;
            baseSpeed = 0.05f;
            color = noiseColor = Color.valueOf("#faf3a7");
            noiseScale = 3000f;
            noisePath = "fog";
            drawParticles = false;
            drawNoise = true;
            useWindVector = false;
            xspeed = 1f;
            yspeed = 0.04f;
            attrs.set(Attribute.light, 0.2f);
            opacityMultiplier = 0.27f;
        }};
		// WIP
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
			attrs.set(Attribute.light, -0.15f);
		}};

		//heavy rain and we need thunder storm aswell TODO: make heavy rain more heavier and darker
		heavyRain = new StormWeather("heavy-rain"){{
			yspeed = 13f;
			xspeed = 10f;
			density = 700f;
			stroke = 1.4f;

			attrs.set(Attribute.light, -0.75f);
			attrs.set(Attribute.water, 0.5f);

			noiseColor = Color.valueOf("#2E3752");
			particleRegion = "particle";
			drawNoise = true;
			useWindVector = true;
			sizeMax = 80f;
            minAlpha = 0.1f;
            maxAlpha = 0.25f;
			baseSpeed = 6.5f;
			opacityMultiplier = 0.35f;
			splashTimeScale = 12f;

			status = DustStatusEffects.drenched;
			sound = Sounds.rain;
			force = 0.7f;
			soundVol = 0.8f;
			duration = 7f * Time.toMinutes;
		}};
	}
}
