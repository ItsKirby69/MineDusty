package minedusty.content;

import minedusty.blocks.*;
import minedusty.planets.DustPlanets;

import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;
import static minedusty.content.DustSectors.*;

import static minedusty.blocks.DustTurret.*;
import static minedusty.blocks.DustCrafters.*;
import static minedusty.blocks.DustDefence.*;
import static minedusty.blocks.DustDistribution.*;
import static minedusty.blocks.DustDrills.lobePump;
import static minedusty.blocks.DustPower.carbonicCombustor;
import static minedusty.blocks.DustPower.geothermalGenerator;
import static minedusty.blocks.DustPower.largesaltBattery;
import static minedusty.blocks.DustPower.powerHub;
import static minedusty.blocks.DustPower.powerPylon;
import static minedusty.blocks.DustPower.saltBattery;

import static mindustry.content.Items.*;
import static mindustry.content.Items.sand;
import static mindustry.content.Liquids.*;
import static mindustry.content.Liquids.slag;
import static mindustry.content.Liquids.water;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustItems.salt;
import static minedusty.content.DustLiquids.*;


import arc.struct.Seq;
import static mindustry.game.Objectives.*;

public class TheiaTechTree {

    public static void load() {
        DustPlanets.theia.techTree = nodeRoot("theia", DustCore.coreNest, () -> {
            node(copperConveyor, () -> {
                node(copperJunction, () -> {
                    node(copperRouter, () -> {
                        node(copperSorter, () -> {
                            node(copperInvertedSorter);
                            node(copperOverflowGate, () -> {
                                node(copperUnderflowGate);
                            });
                        });

                        node(buoyDriver); // require something?
                        node(aquameriumConveyor, Seq.with(
                            new SectorComplete(verdantSpills)
                        ), () -> {});
                    });
                });
            });

            node(DustDrills.copperDrill, () -> {
                node(lobePump, () -> {
                    node(aquameriumConduit, () -> {
                        node(aquaLiquidJunction, () -> {
                            node(aquaLiquidRouter);
                        });
                    });
                });
                node(DustDrills.chloroDrill, () -> {
                    node(DustDrills.offshoreDrill, () -> {});
                });

                node(crystalCrusher, Seq.with(
                    new Produce(graphite),
                    new SectorComplete(basalticShore)
                ), () -> {}); // TODO make it unlock the bore
                node(carbonicConcentrator, Seq.with(
                    new Research(coal)
                ), () -> {});
                node(carbonicPress,  () -> {
                    node(silicaForge, Seq.with(
                        new Research(crystalCrusher)
                    ),() -> {});
                    node(salinator, () -> {});
                });

                node(carbonicCombustor, Seq.with(
                    new Produce(coal)
                ), () -> {
                    node(DustPower.solarPanel, () -> {
                        node(DustPower.largeSolarPanel, () -> {});
                        node(geothermalGenerator, () -> {});
                    });
                    node(powerPylon, () ->{
                        node(saltBattery, Seq.with(
                            new Research(salinator)
                        ), () -> {
                            node(largesaltBattery, () -> {});
                        });
                        node(powerHub, Seq.with(
                            new SectorComplete(lushCorridors)
                        ), () -> {}
                        );
                    });
                });
            });

            node(oxidecopperWall, () -> {
                node(oxidecopperWallLarge, () -> {
                    node(chloroWall, () -> {
                        node(chloroWallLarge);
                    });
                    node(aquaWall, () -> {
                        node(aquaWallLarge, () -> {
                            node(aquawallHuge);
                        });
                    });
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
                node(sleet, Seq.with(
                    new Research(graphite),
                    new Produce(carbonicWaste)
                ), () -> {});
                node(pellucid, Seq.with(
                    new Research(chlorophyte)
                ), () -> {});
                node(spout);
            });

            node(verdantSpills, () -> {
                node(thicketValley);
                node(basalticShore, Seq.with(
                    new SectorComplete(thicketValley),
                    new Research(salinator)
                    ), () -> {
                });
                node(lushCorridors);
            });

            nodeProduce(oxidecopper, () -> {
                nodeProduce(water, () -> {
                    nodeProduce(saltWater, () -> {});
                    nodeProduce(bioLiquid, Seq.with(new Research(chlorophyte)), () -> {
                        nodeProduce(bioFuel, () -> {}); //Seq.with( new Research(bioFuelCombustionChamber) TODO make bioFuel synthesis chamber
                    });
                    nodeProduce(sap, () -> {});
                });

                nodeProduce(lead, () -> {
                    nodeProduce(galena, () -> {
                        nodeProduce(silver, () -> {}); // Silver metal
                    });
                    nodeProduce(chlorophyte, () -> {});
                    nodeProduce(aquamerium, () -> {}); // TODO cryofluid source
                });

                nodeProduce(sand, () -> {
                    nodeProduce(salt, () -> {});
                    nodeProduce(scrap, () -> {
                        nodeProduce(slag, () -> {
                        });
                    });
                    nodeProduce(coal, () -> {
                        nodeProduce(carbonicWaste, () -> {
                            nodeProduce(graphite, () -> {
                                nodeProduce(silicon, () -> {});
                            });
                        });
                        nodeProduce(oil, () -> {
                        });
                    });
                });
            });

        });
    }
}
