package minedusty.content;

import minedusty.type.DustSectorPreset;

import static minedusty.planets.DustPlanets.theia;

public class DustSectors {
	public static DustSectorPreset verdantSpills, thicketValley, basalticShore, lushCorridors;

    public  static void load(){

        // Theia
        verdantSpills = new DustSectorPreset("verdantSpills", theia, 111){{
            alwaysUnlocked = true;
            unlocked = true;
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

        thicketValley = new DustSectorPreset("thicketValley", theia, 3){{
            addStartingItems = true;
            captureWave = 12;
            difficulty = 2;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};
    }
}