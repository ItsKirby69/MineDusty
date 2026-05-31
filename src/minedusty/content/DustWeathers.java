package minedusty.content;

import arc.graphics.Color;
import arc.util.Time;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.meta.*;
import minedusty.type.weather.BetterParticleWeather;
import minedusty.type.weather.CloudyWeather;
import minedusty.type.weather.SnowStormWeather;
import minedusty.type.weather.StormWeather;

public class DustWeathers {
	public static Weather heavyRain, heatWave;
	public static Weather snowStorm, snowFog;

	// Fun weathers
	public static Weather pollenStorm, clouds;

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
            color = noiseColor = Color.valueOf("#faefa7");
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
		snowStorm = new SnowStormWeather("snow-storm"){{
			particleRegion = "minedusty-snow";
			faceMoveDirection = true;
			color = noiseColor = Color.valueOf("#e6feff");
			darkColor = Color.valueOf("#b5e2e4");
			// particleRegion = "particle";
			drawNoise = true;
			useWindVector = true;
			sizeMax = 90f;
			sizeMin = 22f;
			minAlpha = 0.4f;
			maxAlpha = 0.7f;
			density = 700;
			baseSpeed = 7.0f;
			opacityMultiplier = 0.7f;
			force = 0.5f;
			sound = DustSounds.snowStorm;
			soundVol = 0.7f;
			soundVolOscMag = 1.5f;
			soundVolOscScl = 1100f;
			duration = 7f * Time.toMinutes;
			attrs.set(Attribute.light, -0.35f);
		}};

        snowFog = new ParticleWeather("snow-fog"){{
			particleRegion = "minedusty-snow";
            color = Color.valueOf("#e6feff");
            noiseColor = Color.valueOf("#b5e2e4");
			drawNoise = true;
            noisePath = "fog";
            noiseLayers = 4;
            noiseLayerAlphaM = 0.65f;
            noiseLayerSpeedM = 0.5f;
            noiseLayerSclM = 0.6f;
            noiseScale = 1200f;
            xspeed = 0.8f;
            yspeed = -0.04f;
            opacityMultiplier = 0.4f;
			
            useWindVector = true;
            sizeMax = 22f;
            sizeMin = 12f;
            minAlpha = 0.2f;
            maxAlpha = 0.7f;
            density = 12000f;
            baseSpeed = 3.2f;
            sound = Sounds.wind2;
            soundVol = 0f;
            soundVolOscMag = 1.5f;
            soundVolOscScl = 1100f;
            soundVolMin = 0.02f;
            duration = 8 * Time.toMinutes;
			attrs.set(Attribute.light, -0.15f);
        }};

		pollenStorm = new BetterParticleWeather("pollen-storm"){{
			noiseColor = color = Color.valueOf("#fffed5");
			particleRegion = "minedusty-dandelion";
			drawNoise = false;
			useWindVector = false;
			swayParticles = true;
			density = 10000;
			sizeMin = 12f;
			sizeMax = 18f;
			baseSpeed = 3.0f;
			opacityMultiplier = 0.7f;
			force = 0;
			sound = Sounds.none;
			duration = 1.5f * Time.toMinutes;
			yspeed = 0.25f;
			xspeed = -0.75f;
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
