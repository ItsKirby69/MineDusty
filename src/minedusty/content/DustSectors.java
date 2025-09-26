package minedusty.content;

import minedusty.type.DustSectorPreset;

import static minedusty.planets.DustPlanets.theia;

public class DustSectors {
	public static DustSectorPreset verdantSpills, basalticShore;

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

        // TODO finish basaltic shore with waves
        basalticShore = new DustSectorPreset("basalticShore", theia, 50){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 15;
            difficulty = 3;

            // showSectorLandInfo = false;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};
    }
}