package minedusty.content;

import arc.graphics.Color;
import arc.graphics.Colors;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.graphics.g3d.NoiseMesh;
import mindustry.graphics.g3d.SunMesh;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.maps.planet.TantrosPlanetGenerator;
import mindustry.type.Planet;
import mindustry.world.meta.Env;

public class DustPlanets {
	public static Planet 
	gaia, testd;

	public static void load(){
		gaia = new Planet("gaia", Planets.sun, 1.2f, 3){{
			generator = new SerpuloPlanetGenerator();
			alwaysUnlocked = true;
			accessible = true;
			minZoom = 0.75f;
			allowLaunchLoadout = false;
			allowLaunchSchematics = false;
			allowLaunchToNumbered = true;
			allowSectorInvasion = true;
			clearSectorOnLose = true;
			defaultCore = DustBlocks.coreNest;
			orbitRadius = 49f;
			//orbitOffset = 1f;
			atmosphereRadOut = 0.2f;
			parent = Planets.sun;
			updateLighting = true;
			defaultEnv = 5;
			ruleSetter = r -> {

			};
			startSector = 12;
			iconColor = Color.valueOf("6e8b3d");
			atmosphereColor = Color.valueOf("0ad00a");
			hiddenItems.addAll(Items.erekirItems).removeAll(Items.serpuloItems);
			meshLoader = () -> new MultiMesh(
				//(P planet, i divisions, d octaves, d persistence, d scl, d pow, d mag, f colorScale, C... colors)
				new SunMesh(this,5,1,0.5,2,2.2,3,1f,
				Color.valueOf("43838e"),
				Color.valueOf("438d8e"),
				Color.valueOf("44a29f")),
				//(P planet, i seed, i divisions, f radius, i octaves, f persistence, f scale, f mag, C color1, C color2, i coct, f cper, f cscl, f cthresh)
				new NoiseMesh(this, 1, 6, 1.15f, 3, 1f, 1.8f, 1.1f, Color.valueOf("b6d7a8"), Color.valueOf("7cba61"), 2, 1.2f, 1f, 0.5f),
				new NoiseMesh(this, 1, 6, 1.1f, 3, 0.8f, 2.1f, 1f, Color.valueOf("88834C"), Color.valueOf("96814C"), 1, 1.2f, 2.1f, 0.2f),
				//mountains
				new NoiseMesh(this, 0, 6, 0.9f, 3, 1.5f, 1.5f, 1.4f, Color.valueOf("c8cdce"),Color.valueOf("a0babf"), 1, 1.9f, 1.7f, 0.4f)
			);
			cloudMeshLoader = () -> new MultiMesh(
				//(P planet, i seed, f speed, f radius, i divisions, C color, i octaves, f persistence, f scl, f thresh)
				new HexSkyMesh(this, 0, 0.87f, 0.13f, 6, new Color().set(Color.valueOf("B8CFC0")).mul(0.9f).a(0.8f), 2, 0.5f, 0.8f, 0.3f),
				new HexSkyMesh(this, 0, 0.83f, 0.16f, 6, Color.white.cpy().lerp(Color.valueOf("80A499"), 0.55f).a(0.75f), 2, 0.55f, 0.85f, 0.35f)
			);
			/*cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, new Color().set(Pal.spore).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Pal.spore, 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
            );*/
			unlockedOnLand.addAll(DustBlocks.coreNest);
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
            //atmosphereRadIn = -0.01f;
            atmosphereRadOut = 0.3f;
			minZoom = 0.75f;
            defaultEnv = Env.underwater | Env.terrestrial;
            ruleSetter = r -> {
            };
        }};

	}

}