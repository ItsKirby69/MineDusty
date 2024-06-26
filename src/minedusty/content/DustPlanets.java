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
			generator = new ErekirPlanetGenerator();
			alwaysUnlocked = true;
			visible = true;
			bloom = false;
			accessible = true;
			minZoom = 0.9f;
			allowLaunchLoadout = false;
			allowLaunchSchematics = false;
			allowLaunchToNumbered = true;
			allowSectorInvasion = true;
			clearSectorOnLose = true;
			defaultCore = DustBlocks.coreNest;
			orbitRadius = 55;
			orbitOffset = 12f;
			atmosphereRadIn = 0.1f;
			atmosphereRadOut = 0.35f;
			parent = Planets.sun;
			updateLighting = true;
			radius = 1.2f;
			defaultEnv = 5;
			ruleSetter = r -> {

			};
			startSector = 12;
			iconColor = Color.valueOf("6e8b3d");
			atmosphereColor = Color.valueOf("44BA23");
			hiddenItems.addAll(Items.erekirItems).removeAll(Items.serpuloItems);
			meshLoader = () -> new MultiMesh(
				new SunMesh(this,5,1,0.5,2,2.2,3,1f, 
				Color.valueOf("43718e"),
				Color.valueOf("437b8e"),
				Color.valueOf("468f92")),
				new SunMesh(this,5,1,0.5,2,2.2,3,1f, 
				Color.valueOf("43718e"),
				Color.valueOf("437b8e"),
				Color.valueOf("468f92")),
				new SunMesh(this,5,1,0.5,2,2.2,3,1f, 
				Color.valueOf("43718e"),
				Color.valueOf("437b8e"),
				Color.valueOf("468f92")),
				//(P planet, i seed, i divisions, f radius, i octaves, f persistence, f scale, f mag, C color1, C color2, i coct, f cper, f cscl, f cthresh)
				new NoiseMesh(this, 0,6, 0.9f, 3, 1.1f, 1.1f, 3.9f, Color.valueOf("c8cdce"),Color.valueOf("a0babf"), 1, 1.1f, 0.7f, 0.4f)
			);
			cloudMeshLoader = () -> new MultiMesh(
				//(P planet, i seed, f speed, f radius, i divisions, C color, i octaves, f persistence, f scl, f thresh)
				new HexSkyMesh(this, 2, 0.97f, 0.07f, 6, Color.valueOf("c5dbdd"), 2, 0.6f, 1.1f, 0.4f),
				new HexSkyMesh(this, 0, 0.93f, 0.12f, 6, Color.valueOf("9bb4bf"), 2, 0.5f, 1.0f, 0.3f)
			);

			/*
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, new Color().set(Pal.spore).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Pal.spore, 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
            );*/
			//unlockedOnLand = stuff;
		}};

		testd = new Planet("tantros", Planets.sun, 1f, 2){{
            generator = new TantrosPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 4);
            accessible = true;
            visible = true;
			alwaysUnlocked = true;
            atmosphereColor = Color.valueOf("3db899");
            iconColor = Color.valueOf("597be3");
            startSector = 10;
            atmosphereRadIn = -0.01f;
            atmosphereRadOut = 0.3f;
            defaultEnv = Env.underwater | Env.terrestrial;
            ruleSetter = r -> {

            };
        }};

	}

}