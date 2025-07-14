package minedusty.content;

import mindustry.type.SectorPreset;
import minedusty.type.DustSectorPreset;

import static minedusty.planets.DustPlanets.terra;

public class DustSectors {
	public static SectorPreset verdantSpill;

    public  static void load(){

        // Terra

        verdantSpill = new DustSectorPreset("verdantSpill", terra, 111){{
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