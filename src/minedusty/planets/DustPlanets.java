package minedusty.planets;

import arc.graphics.Color;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.graphics.g3d.*;
import mindustry.type.Planet;
import mindustry.type.Weather;
import mindustry.world.meta.Env;
import minedusty.content.*;

public class DustPlanets {
	public static Planet
		// remake
		theia,
		// theia's oceanic moon
		nautilune;

	public static void load() {
		theia = new Planet("theia", Planets.sun, 1f, 3) {{
			icon = "minedusty-theia"; // why not working
			generator = new TheiaPlanetGenerator();
			meshLoader = () -> new MultiMesh(
					// new AtmosphereHexMesh(this, 6),
					new HexMesh(this, 6));
			cloudMeshLoader = () -> new MultiMesh(
					new HexSkyMesh(this, 0, 0.85f, 0.13f, 5, 
							Color.white.cpy().lerp(Color.valueOf("#b8d5e0ff"), 0.7f).a(0.5f), 2,
							0.5f, 0.8f, 0.3f),
					new HexSkyMesh(this, 0, 0.8f, 0.16f, 5,
							Color.white.cpy().lerp(Color.valueOf("#89bfcdff"), 0.55f).a(0.5f), 2, 
							0.55f, 0.85f, 0.35f));

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
			atmosphereColor = Color.valueOf("1e3c40"); // 438e99
			iconColor = Color.valueOf("32e341"); // 2cc429
			ruleSetter = r -> {

				r.placeRangeCheck = false;
				r.coreDestroyClear = true;
				r.fog = false;

				// Make it less punishing to get items back from buildings.
				r.deconstructRefundMultiplier = 0.8f;

				// r.weather.add(
				// 	new Weather.WeatherEntry() {{
				// 		weather = Weathers.fog;
				// 	}},
				// 	new Weather.WeatherEntry() {{
				// 		weather = DustWeathers.heavyRain;
				// 	}},
				// 	new Weather.WeatherEntry() {{
				// 		weather = DustWeathers.heavyRain;
				// 	}}
				// );
			};
		}};

		nautilune = new Planet("nautilune", DustPlanets.theia, 0.8f, 1) {{
			generator = new NautilunePlanetGenerator();
			meshLoader = () -> new HexMesh(this, 4);
			accessible = false;
			visible = true;
			alwaysUnlocked = false; // maybe make it unlock after certain sectors (maybe closest to the planet from
									// terra) is unlocked
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