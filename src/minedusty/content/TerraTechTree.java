package minedusty.content;

import minedusty.blocks.DustCore;
import minedusty.planets.DustPlanets;

import static mindustry.content.TechTree.*;
import static minedusty.content.DustSectors.*;

public class TerraTechTree {
    public static void load() {
        DustPlanets.terra.techTree = nodeRoot("terra", DustCore.coreNest, () -> {
            node(verdantSpill, () -> {});
        });
    }
}
