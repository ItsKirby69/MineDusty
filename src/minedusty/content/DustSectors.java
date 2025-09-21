package minedusty.content;

import mindustry.type.SectorPreset;
import minedusty.type.DustSectorPreset;

import static minedusty.planets.DustPlanets.theia;

public class DustSectors {
	public static SectorPreset verdantSpill;

    public  static void load(){

        // Theia
        verdantSpill = new DustSectorPreset("verdantSpill", theia, 111){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 12;
            difficulty = 3;
            //showSectorLandInfo = false; //?
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 1.5f;
        }};
    }

}