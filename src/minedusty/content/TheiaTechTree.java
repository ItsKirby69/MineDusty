package minedusty.content;

import minedusty.blocks.DustCore;
import minedusty.planets.DustPlanets;

import static mindustry.content.TechTree.*;
import static minedusty.content.DustSectors.*;

public class TheiaTechTree {
    public static void load() {
        DustPlanets.theia.techTree = nodeRoot("theia", DustCore.coreNest, () -> {
            node(verdantSpill, () -> {});
        });
    }
}
