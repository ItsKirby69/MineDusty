package minedusty.planets;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.game.Team;
import mindustry.graphics.g3d.*;
import mindustry.type.Planet;
import mindustry.type.Weather;
import mindustry.world.meta.Env;
import minedusty.blocks.*;
import minedusty.content.*;

public class DustPlanets {
	public static Planet //TODO add clutter asteroids and stuff near theia's surroundings (similar to how asteroids exist)
	// test planet
	gaia,
	// remake
	theia,
	// theia's oceanic moon
	nautilune;

	public static void load(){
		//my dudes look how clean this is compared to "gaia"
		theia = new Planet("theia", Planets.sun, 1f, 3){{
			generator = new TheiaPlanetGenerator();
			meshLoader = () -> new MultiMesh(
				//new AtmosphereHexMesh(this, 6),
				new HexMesh(this, 6)
			);
			cloudMeshLoader = () -> new MultiMesh(
				new HexSkyMesh(this, 0, 0.85f, 0.13f, 5, new Color().set(Color.valueOf("748c8c")).a(0.7f), 2, 0.5f, 0.8f, 0.3f),
				new HexSkyMesh(this, 0, 0.8f, 0.16f, 5, Color.white.cpy().lerp(Color.valueOf("58818c"), 0.55f).a(0.75f), 2, 0.55f, 0.85f, 0.35f)
			);

			startSector = 111;
			
			rotateTime = 24f * 60f; // unsure if changing is nessesary
			
			orbitRadius = 40f;
			orbitOffset = 210f;
			atmosphereRadOut = 0.3f;
			
			
			alwaysUnlocked = true;
			updateLighting = true;
			accessible = true;
			allowLaunchSchematics = false;
			allowLaunchToNumbered = false;
			allowLaunchLoadout = false;

			defaultEnv = Env.terrestrial & ~Env.spores;
			atmosphereColor = Color.valueOf("1e3c40"); //438e99
			iconColor = Color.valueOf("32e341"); //2cc429
			ruleSetter = r -> {
				r.waveTeam = Team.green; //temp
				r.placeRangeCheck = false; // might set this to true?
				r.showSpawns = false; // not exactly sure what this does
                r.coreDestroyClear = true;
				r.fog = false;
                
                //r.deconstructRefundMultiplier = 0.6f;

				r.weather.add(
					new Weather.WeatherEntry(){{ weather = Weathers.fog;}},
					new Weather.WeatherEntry(){{ weather = DustWeathers.heavyRain;}}
				);
			};
		}};

		nautilune = new Planet("nautilune", DustPlanets.theia, 0.8f, 1){{
            generator = new NautilunePlanetGenerator();
            meshLoader = () -> new HexMesh(this, 4);
            accessible = false;
            visible = true;
			alwaysUnlocked = false; //maybe make it unlock after certain sectors (maybe closest to the planet from terra) is unlocked
            atmosphereColor = Color.valueOf("3db899");
            iconColor = Color.valueOf("597be3");
            startSector = 10;
            atmosphereRadOut = 0.3f;
			minZoom = 0.75f;
            defaultEnv = Env.underwater | Env.terrestrial;
            ruleSetter = r -> {};
        }};

	}

}