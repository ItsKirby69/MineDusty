package minedusty.content;

import minedusty.blocks.DustCore;
import minedusty.planets.DustPlanets;

import static mindustry.content.Blocks.*;
import static arc.struct.Seq.*;
import static mindustry.content.TechTree.*;

import static minedusty.content.DustSectors.*;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.ItemStack;


public class TheiaTechTree {
    
    public static void load() {
        DustPlanets.theia.techTree = nodeRoot("theia", DustCore.coreNest, () -> {
            node(conveyor, () -> {
                node(junction, () -> {
                    node(router, () -> {
                        node(distributor);
                        node(sorter, () -> {
                            node(invertedSorter);
                        });
                        node(itemBridge);
                    });
                });
            });

            node(mechanicalDrill, () -> {

                node(graphitePress, ItemStack.with(Items.copper, 120, Items.lead, 50), () -> {
                    node(siliconSmelter, () ->{
                        node(kiln);
                        node(illuminator, () -> {});
                    });
                });

                node(combustionGenerator, () -> {
                    // Custom power nodes? Like one smaller with low connections but connects far, while another that just connects to buildings a lot?
                    node(powerNode, () -> {
                        node(battery, () -> {
                            node(batteryLarge);
                        });
                        node(mender, () -> {
                            node(mendProjector);
                        });
                        // Smaller thermal Generator that only works on (either magma or hotrocks only?)
                        node(thermalGenerator, () ->{
                            node(steamGenerator);
                        });
                        // Custom Solar panel
                    });
                });
            });

            node(copperWall, () -> {
                node(copperWallLarge, () -> {
                    node(titaniumWall);
                });
                node(scrapWall, () -> {
                    node(scrapWallLarge, () -> {
                        node(scrapWallHuge, () -> {
                            node(scrapWallGigantic);
                        });
                    });
                });
                node(door, () -> {
                    node(doorLarge);
                });

            });

            // Need to add my custom turrets as well
            node(duo, () -> {
                node(scorch, () -> {
                    node(arc);
                });
                node(scatter, () -> {
                    node(hail, () -> {
                        node(salvo);
                    });
                });
            });

            node(verdantSpill, () -> {});

            nodeProduce(Items.copper, () -> {
                nodeProduce(Liquids.water, () -> {});

                nodeProduce(Items.lead, () -> {
                    nodeProduce(Items.titanium, () -> {
                        nodeProduce(Liquids.cryofluid, () -> {});
                    });
                    nodeProduce(Items.metaglass, () -> {});
                });

                nodeProduce(Items.sand, () -> {
                    nodeProduce(Items.scrap, () -> {
                        nodeProduce(Liquids.slag, () -> {});
                    });
                    nodeProduce(Items.coal, () -> {
                        nodeProduce(Liquids.oil,  () -> {});
                    });
                });
            });

        });
    }
}
