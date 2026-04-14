package minedusty.blocks;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.meta.BuildVisibility;
import minedusty.DustAttributes;
import minedusty.world.blocks.defense.*;

import static minedusty.content.DustItems.*;
import static mindustry.type.ItemStack.*;

/** For all thermal stuff for the Frost mechanic */
public class DustThermal {

    public static Block electricFurnace, solidFurance;

    // For the crux
    public static Block radiator;

    public static void loadContent(){
        // Hot Tiles
        Blocks.heatSource.attributes.set(DustAttributes.thermalPower, 3); //debug purposes
        Blocks.hotrock.attributes.set(DustAttributes.thermalPower, 1);
        Blocks.magmarock.attributes.set(DustAttributes.thermalPower, 1.5f);
        Blocks.slag.attributes.set(DustAttributes.thermalPower, 2);

        electricFurnace = new ElectricDefroster("electric-furnace", 2){{
            requirements(Category.crafting, with(oxidecopper, 16, Items.lead, 20));
            researchCost = with(oxidecopper, 1600, carbonicWaste, 300, aquamerium, 400);
            consumePower(90f / 60f);
        }};

        // Crux only
        radiator = new ElectricDefroster("radiator", 3){{
            requirements(Category.crafting, BuildVisibility.sandboxOnly, with());
            consumePower(120f / 60f);
        }};

        solidFurance = new SolidDefroster("solid-furnace", 4){{
            requirements(Category.crafting, with(oxidecopper, 25, chlorophyte, 30, Items.silicon, 25));
            researchCost = with(Items.silicon, 450, carbonicWaste, 350);
            size = 2;
            craftTime = 60f * 5f;
            consume(new ConsumeItemFlammable(1f));
        }};
    }
}
