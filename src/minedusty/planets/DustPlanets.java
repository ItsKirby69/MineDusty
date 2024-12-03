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
import minedusty.content.DustItems;
import minedusty.content.DustWeathers;

public class DustPlanets {
	public static Planet
	gaia, terra, testd;

	public static void load(){
		terra = new Planet("terra", Planets.sun, 1.2f, 2){{
			rotateTime = 3f;
			generator = new TerraPlanetGenerator();
			meshLoader = () -> new HexMesh(this, 5);
			accessible = true;
			alwaysUnlocked = true;
			updateLighting = true;
			startSector = 2;
			orbitRadius = 29f;
			atmosphereRadOut = 0.2f;
			
			atmosphereColor = Color.valueOf("a3cdc9"); //3edfcd
			iconColor = Color.valueOf("2cc429");
			cloudMeshLoader = () -> new MultiMesh(
				//(P planet, i seed, f speed, f radius, i divisions, C color, i octaves, f persistence, f scl, f thresh)
				new HexSkyMesh(this, 0, 0.87f, 0.13f, 5, new Color().set(Color.valueOf("96f9ff")).mul(0.9f).a(0.8f), 2, 0.5f, 0.8f, 0.3f),
				new HexSkyMesh(this, 0, 0.83f, 0.16f, 5, Color.white.cpy().lerp(Color.valueOf("d3ffff"), 0.55f).a(0.75f), 2, 0.55f, 0.85f, 0.35f)
			);
			ruleSetter = r -> {
				r.waveTeam = Team.green;
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

			//itemWhitelist = DustItems.dustItems;
			//TODO: Might change this whitelist to seperate technologies
			hiddenItems.addAll(Items.erekirItems).addAll(Items.serpuloItems).removeAll(DustItems.dustItems);
			
			meshLoader = () -> new HexMesh(this,6); //TODO: This is big, fix the planet mesh stuff. Maybe use self hex mesh but modify GaiaPlanetGenerator then.
			/*meshLoader = () -> new MultiMesh(
				// water
				new SunMesh(this,5,2,0.5,1.7,2.2,3,1f,
					Color.valueOf("43838e"),
					Color.valueOf("438d8e"),
					Color.valueOf("44a29f")),
					
				//(P planet, i seed, i divisions, f radius, i octaves, f persistence, f scale, f mag, C color1, C color2, i coct, f cper, f cscl, f cthresh)
				// land
				new NoiseMesh(this, 2, 6, 1.087f, 4, 1.2f, 0.8f, 1.15f, Color.valueOf("66d86f"), Color.valueOf("6ac841"), 4, 1.1f, 1.2f, 1f),
				// sandy
				new NoiseMesh(this, 2, 6, 1.076f, 3, 1f, 0.79f, 1.15f, Color.valueOf("f7e7c6"), Color.valueOf("ffeabb"), 2, 1.1f, 1.2f, 1f),
				// basalt
				new NoiseMesh(this, 3, 6, 0.96f, 3, 0.9f, 0.6f, 1f, Color.valueOf("8a8da7"), Color.valueOf("6d7382"), 3, 1f, 1.1f, 0.6f),
				
				// extra
				//new NoiseMesh(this, 3, 6, 0.98f, 3, 0.85f, 0.3f, 1.05f, Color.valueOf("99dd3e"), Color.valueOf("9a6814"), 3, 0.8f, 0.9f, 1f),
				
				// mountains
				new NoiseMesh(this, 0, 6, 1.0f, 4, 1.1f, 1.25f, 1.77f, Color.valueOf("cdd8da"), Color.valueOf("a5b8bc"), 4, 2f, 1f, 1f)
			);*/

			cloudMeshLoader = () -> new MultiMesh(
				//(P planet, i seed, f speed, f radius, i divisions, C color, i octaves, f persistence, f scl, f thresh)
				new HexSkyMesh(this, 0, 0.87f, 0.13f, 6, new Color().set(Color.valueOf("caf5f0")).mul(0.9f).a(0.8f), 2, 0.5f, 0.8f, 0.3f),
				new HexSkyMesh(this, 0, 0.83f, 0.16f, 6, Color.white.cpy().lerp(Color.valueOf("9ddac8"), 0.55f).a(0.75f), 2, 0.55f, 0.85f, 0.35f)
			);
			unlockedOnLand.addAll(DustCore.coreNest);
		}};

		testd = new Planet("blue-thing", DustPlanets.gaia, 0.8f, 1){{
            generator = new TantrosPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 4);
            accessible = true;
            visible = true;
			alwaysUnlocked = true;
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