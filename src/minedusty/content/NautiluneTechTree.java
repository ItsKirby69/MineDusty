package minedusty.content;

import minedusty.blocks.DustCore;
import minedusty.planets.DustPlanets;

import static mindustry.content.TechTree.*;

import mindustry.content.Blocks;
import mindustry.game.Objectives.*;

public class NautiluneTechTree {

	public static void load(){
        DustPlanets.nautilune.techTree = nodeRoot("Nautilune", DustCore.coreNest, () -> {
            node(Blocks.surgeWall, () -> {});
        });
    }
}