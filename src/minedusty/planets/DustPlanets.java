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
	public static Planet //TODO add clutter asteroids and stuff near terra's surroundings (similar to how asteroids exist)
	// test planet
	gaia,
	// remake
	terra, //for now this is the name of the planet...
	// terra's oceanic moon
	nautilune;

	public static void load(){
		//my dudes look how clean this is compared to "gaia"
		terra = new Planet("terra", Planets.sun, 1f, 3){{
			generator = new TerraPlanetGenerator();
			meshLoader = () -> new MultiMesh(
				//new AtmosphereHexMesh(this, 6),
				new HexMesh(this, 6)
			);
			cloudMeshLoader = () -> new MultiMesh(
				new HexSkyMesh(this, 0, 0.85f, 0.13f, 5, new Color().set(Color.valueOf("748c8c")).a(0.7f), 2, 0.5f, 0.8f, 0.3f),
				new HexSkyMesh(this, 0, 0.8f, 0.16f, 5, Color.white.cpy().lerp(Color.valueOf("58818c"), 0.55f).a(0.75f), 2, 0.55f, 0.85f, 0.35f)
			);

			startSector = 20; //Probably will remove in favour of custom preset already unlocked
			
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

		nautilune = new Planet("nautilune", DustPlanets.terra, 0.8f, 1){{
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
		
		gaia = new Planet("gaia", Planets.sun, 1.2f, 3){{
			generator = new GaiaPlanetGenerator();
			alwaysUnlocked = true;
			accessible = false;
			minZoom = 0.75f;

			allowLaunchLoadout = false;
			allowLaunchSchematics = false;
			allowLaunchToNumbered = true;
			//gotta watch out when starting sector has no safe numbered sectors around
			allowSectorInvasion = true;
			clearSectorOnLose = true;
			allowWaves = true;
			allowWaveSimulation = true;

			defaultCore = DustCore.coreNest;
			orbitRadius = 29f;
			atmosphereRadIn = -0.1f;
			//atmosphereRadOut = 0.2f;
			parent = Planets.sun;
			updateLighting = true;
			defaultEnv = 1;
			//TODO properly expand this, also ADD more weathers and different configurations //Actually, this might not be the best place to add weathers
			ruleSetter = r -> {
				r.waveTeam = Team.green;
				r.weather.add(
					new Weather.WeatherEntry(){{ weather = Weathers.fog;}},
					new Weather.WeatherEntry(){{ weather = DustWeathers.heavyRain;}}
				);
			};
			startSector = 1;
			iconColor = Color.valueOf("6e8b3d");
			atmosphereColor = Color.valueOf("84eb5f");
			
			meshLoader = () -> new HexMesh(this,6); //TODO: This is big, fix the planet mesh stuff. Maybe use self hex mesh but modify GaiaPlanetGenerator then.

			cloudMeshLoader = () -> new MultiMesh(
				//(P planet, i seed, f speed, f radius, i divisions, C color, i octaves, f persistence, f scl, f thresh)
				new HexSkyMesh(this, 0, 0.87f, 0.13f, 6, new Color().set(Color.valueOf("caf5f0")).mul(0.9f).a(0.8f), 2, 0.5f, 0.8f, 0.3f),
				new HexSkyMesh(this, 0, 0.83f, 0.16f, 6, Color.white.cpy().lerp(Color.valueOf("9ddac8"), 0.55f).a(0.75f), 2, 0.55f, 0.85f, 0.35f)
			);
			unlockedOnLand.addAll(DustCore.coreNest);
		}};

	}

}