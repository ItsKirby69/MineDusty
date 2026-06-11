package minedusty.content;

import minedusty.type.DustSectorPreset;

import static minedusty.planets.DustPlanets.theia;

public class DustSectors {
    // Lush
	public static DustSectorPreset verdantSpills, thicketValley, basalticShore, lushCorridors, tropicalLake, grassyPlains;

    // Desert
    public static DustSectorPreset sandyEminence;

    // Icey
    public static DustSectorPreset frostedFault, snowFort;
    
    public static void load(){

        // Theia
        tropicalLake = new DustSectorPreset("tropicalLake", theia, 49){{
            addStartingItems = true;
            captureWave = 21;
            difficulty = 6;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};

        verdantSpills = new DustSectorPreset("verdantSpills", theia, 111){{
            alwaysUnlocked = true;
            unlocked = true;
            addStartingItems = true;
            captureWave = 10;
            difficulty = 1;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};

        basalticShore = new DustSectorPreset("basalticShore", theia, 50){{
            addStartingItems = true;
            captureWave = 15;
            difficulty = 4;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};

        lushCorridors = new DustSectorPreset("lushCorridors", theia, 110){{
            addStartingItems = true;
            captureWave = 16;
            difficulty = 5;
            attackAfterWaves = true;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 2f;
        }};

        thicketValley = new DustSectorPreset("thicketValley", theia, 3){{
            addStartingItems = true;
            captureWave = 13;
            difficulty = 2;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};

        grassyPlains = new DustSectorPreset("grassyPlains", theia, 109){{
            addStartingItems = true;
            allowLaunchLoadout = true;
            captureWave = 13;
            difficulty = 4;
            startWaveTimeMultiplier = 1.5f;
        }};

        sandyEminence = new DustSectorPreset("sandyEminence", theia, 51){{
            addStartingItems = true;
            captureWave = 20;
            difficulty = 3;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};

        frostedFault = new DustSectorPreset("frostedFault", theia, 265){{
            addStartingItems = true;
            captureWave = 21;
            difficulty = 5;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};

        // snowFort = new DustSectorPreset("snowFort", theia, 264){{
        //     addStartingItems = true;
        //     captureWave = 20;
        //     difficulty = 5;
        //     overrideLaunchDefaults = true;
        //     startWaveTimeMultiplier = 1.5f;
        // }};
    }
}