package minedusty.planets;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.game.Team;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.TantrosPlanetGenerator;
import mindustry.type.Planet;
import mindustry.type.Weather;
import mindustry.world.meta.Env;
import minedusty.blocks.DustCore;
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
		terra = new Planet("terra", Planets.sun, 1.2f, 2){{
			rotateTime = 3f;
			generator = new TerraPlanetGenerator();
			meshLoader = () -> new HexMesh(this, 6);
			accessible = true;
			alwaysUnlocked = true;
			updateLighting = true;
			startSector = 2;
			orbitRadius = 29f;
			atmosphereRadOut = 0.2f;
			allowLaunchSchematics = false;
			allowLaunchToNumbered = false;
			
			atmosphereColor = Color.valueOf("a3cdc9"); //3edfcd
			iconColor = Color.valueOf("2cc429");
			cloudMeshLoader = () -> new MultiMesh(
				//(P planet, i seed, f speed, f radius, i divisions, C color, i octaves, f persistence, f scl, f thresh)
				new HexSkyMesh(this, 0, 0.87f, 0.13f, 5, new Color().set(Color.valueOf("96f9ff")).mul(0.9f).a(0.8f), 2, 0.5f, 0.8f, 0.3f),
				new HexSkyMesh(this, 0, 0.83f, 0.16f, 5, Color.white.cpy().lerp(Color.valueOf("d3ffff"), 0.55f).a(0.75f), 2, 0.55f, 0.85f, 0.35f)
			);
			ruleSetter = r -> {
				r.waveTeam = Team.green; //temp
				r.weather.add(
					new Weather.WeatherEntry(){{ weather = Weathers.fog;}},
					new Weather.WeatherEntry(){{ weather = DustWeathers.heavyRain;}}
				);
			};
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

		nautilune = new Planet("nautilune", DustPlanets.terra, 0.8f, 1){{
            generator = new TantrosPlanetGenerator();
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
            ruleSetter = r -> {
            };
        }};

	}

}