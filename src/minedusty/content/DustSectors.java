package minedusty.content;

import minedusty.type.DustSectorPreset;

import static minedusty.planets.DustPlanets.theia;

public class DustSectors {
	public static DustSectorPreset verdantSpills, basalticShore, lushCorridors;

    public  static void load(){

        // Theia
        verdantSpills = new DustSectorPreset("verdantSpills", theia, 111){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 7;
            difficulty = 1;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};

        basalticShore = new DustSectorPreset("basalticShore", theia, 50){{
            addStartingItems = true;
            captureWave = 15;
            difficulty = 3;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};

        lushCorridors = new DustSectorPreset("lushCorridors", theia, 110){{
            addStartingItems = true;
            captureWave = 15;
            difficulty = 5;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};
    }
}