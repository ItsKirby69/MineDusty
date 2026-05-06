package minedusty.content;

import minedusty.blocks.*;
import minedusty.planets.DustPlanets;

import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;
import static minedusty.content.DustSectors.*;

import static minedusty.content.DustUnitTypes.*;
import static minedusty.blocks.DustTurrets.*;
import static minedusty.blocks.DustCrafters.*;
import static minedusty.blocks.DustDefence.*;
import static minedusty.blocks.DustDistribution.*;
import static minedusty.blocks.DustPower.*;
import static minedusty.blocks.DustCore.*;
import static minedusty.blocks.DustThermal.*;
import static minedusty.blocks.DustDrills.*;
import static mindustry.content.Items.*;
import static mindustry.content.Items.sand;
import static mindustry.content.Liquids.*;
import static mindustry.content.Liquids.slag;
import static mindustry.content.Liquids.water;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustItems.salt;
import static minedusty.content.DustLiquids.*;


import arc.struct.Seq;
import mindustry.game.Objectives.*;

public class TheiaTechTree {

    public static void load() {
        DustPlanets.theia.techTree = nodeRoot("Theia", DustCore.coreNest, () -> {
            node(copperConveyor, () -> {
                node(copperJunction, () -> {
                    node(copperRouter, () -> {
                        node(copperSorter, () -> {
                            node(copperInvertedSorter);
                            node(copperOverflowGate, () -> {
                                node(copperUnderflowGate);
                            });
                        });

                        node(buoyDriver, Seq.with(
                            new SectorComplete(verdantSpills)
                        ), () -> {}); // require something?
                        node(aquameriumConveyor, Seq.with(
                            new SectorComplete(verdantSpills)
                        ), () -> {});
                        node(stockpile, () -> {});
                    });
                });
            });

            node(DustDrills.copperDrill, () -> {
                node(solarPump, Seq.with(
                    new SectorComplete(verdantSpills),
                    new OnSector(basalticShore)
                ), () -> {
                    node(lobePump, () -> {});
                    node(aquameriumConduit, () -> {
                        node(aquaLiquidJunction, () -> {
                            node(aquaLiquidRouter);
                        });
                    });
                    node(salinator, Seq.with(
                        new SectorComplete(sandyEminence)
                    ), () -> {
                        node(fluidBed, () -> {});
                    });
                });
                node(DustDrills.chloroDrill, () -> {
                    node(DustDrills.offshoreDrill, () -> {});
                    node(oilTap, () -> {});
                });

                node(crystalBore, Seq.with(
                    new Produce(graphite),
                    new OnSector(basalticShore)
                ), () -> {});
                node(carbonicConcentrator, () -> {
                    node(carbonicPress, () -> {
                        node(silicaForge, Seq.with(
                            new Research(crystalCrusher),
                            new Produce(silicadust)
                        ),() -> {});
                    });
                    node(bioSludgeChamber, () -> {
                        node(chlorophyteCultivator, () -> {});
                    });
                    // TODO until there is a better use
                    // node(carbonicRefinery, Seq.with(
                    //     new SectorComplete(basalticShore)
                    // ), () -> {});
                });


                node(carbonicCombustor, Seq.with(
                    new Produce(coal)
                ), () -> {
                    node(DustPower.solarPanel, () -> {
                        node(DustPower.largeSolarPanel, () -> {});
                        node(geothermalGenerator, () -> {
                            node(largegeothermalGenerator, Seq.with(
                                new OnSector(basalticShore)
                            ), () -> {});
                        });
                    });
                    node(electricFurnace, Seq.with(
                        new SectorComplete(lushCorridors)
                    ), () -> {
                        node(solidFurance, Seq.with(
                            new SectorComplete(frostedFault)
                        ), () -> {});
                    });
                    node(powerPylon, () ->{
                        node(saltBattery, Seq.with(
                            new Research(salinator)
                        ), () -> {
                            node(largesaltBattery, () -> {});
                        });
                        node(powerHub, Seq.with(
                            new SectorComplete(lushCorridors)
                        ), () -> {});
                        node(chloroMender, () -> {});
                        node(lantern, () -> {});
                    });
                });
            });

            node(oxidecopperWall, () -> {
                node(chloroWall, () -> {
                    node(siliconWallLarge, () -> {
                        node(crystalWall, () -> {
                            node(crystalWallLarge, () -> {});
                        });
                    });
                    node(chloroWallLarge);
                    node(aquaWall, () -> {
                        node(aquaWallLarge, () -> {
                            node(aquawallHuge);
                        });
                    });
                });
                node(oxidecopperWallLarge, () -> {
                });
                node(scrapWall, () -> {
                    node(scrapWallLarge, () -> {
                        node(scrapWallHuge, () -> {
                            node(scrapWallGigantic);
                        });
                    });
                });
                // TODO
                // node(door, () -> {
                //     node(doorLarge);
                // });
            });

            // Need to add my custom turrets as well
            node(sandSpitter, () -> {
                node(volt, Seq.with(
                    new Research(powerNode)
                ), () -> {});
                node(sleet, Seq.with(
                    new Research(graphite),
                    new Produce(carbonicWaste)
                ), () -> {
                    node(sandHammer, Seq.with(
                        new SectorComplete(lushCorridors)
                    ), () -> {});
                });
                node(pellucid, Seq.with(
                    new Research(chlorophyte)
                ), () -> {
                    node(pistil, Seq.with(
                        new Research(bioSludgeChamber)
                    ), () -> {});
                    node(cascade, () -> {});
                });
                node(spout);
            });

            node(verdantSpills, () -> {
                node(sandyEminence, Seq.with(
                    new SectorComplete(thicketValley)
                ), () -> {});
                node(thicketValley, Seq.with(
                    new SectorComplete(verdantSpills),
                    new Research(copperJunction),
                    new Research(copperRouter)
                ), () -> {
                    node(grassyPlains, () -> {});
                });
                node(basalticShore, Seq.with(
                    new SectorComplete(thicketValley)
                    ), () -> {
                });
                node(lushCorridors, Seq.with(
                    new SectorComplete(thicketValley),
                    new Research(skyFactory)
                ), () -> {
                    node(tropicalLake, Seq.with(
                        new SectorComplete(basalticShore)
                    ), () -> {});
                    node(frostedFault, Seq.with(
                        new Research(electricFurnace)
                    ), () -> {
                        // node(snowFort, () -> {});
                    });
                });
            });

            node(earthFactory, Seq.with(
                new SectorComplete(thicketValley)
            ), () -> {
                node(bulbus, () -> {});
                node(skyFactory, () -> {
                    node(dazzle);
                });
            });

            nodeProduce(oxidecopper, () -> {
                nodeProduce(water, () -> {
                    nodeProduce(saltWater, () -> {});
                    nodeProduce(bioLiquid, Seq.with(new Research(chlorophyte)), () -> {
                        // nodeProduce(bioFuel, () -> {}); //TODO make bioFuel synthesis chamber
                        nodeProduce(oil, () -> {}); //TODO oil production with bioLiquids?
                    });
                    nodeProduce(slag, () -> {});
                    // nodeProduce(sap, () -> {});
                });

                nodeProduce(lead, () -> {
                    nodeProduce(galena, () -> {
                        nodeProduce(silver, () -> {}); // Silver metal
                    });
                    nodeProduce(chlorophyte, () -> {
                        nodeProduce(aquamerium, () -> {});
                    });
                    nodeProduce(silicadust, () -> {
                            nodeProduce(silicon, () -> {});
                        });
                });

                nodeProduce(sand, () -> {
                    nodeProduce(salt, () -> {});
                    nodeProduce(scrap, () -> {
                    });
                    nodeProduce(coal, () -> {
                        nodeProduce(carbonicWaste, () -> {
                            nodeProduce(graphite, () -> {});
                        });
                        
                    });
                });
            });

        });
    }
}
