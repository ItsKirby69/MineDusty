package minedusty.blocks;

import mindustry.content.*;
import mindustry.type.Category;
import mindustry.world.*;
import mindustry.world.meta.Env;
import minedusty.content.*;
import minedusty.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class DustDrills {
    // TODO bore drill.
    public static Block mechanicalDrill, chloroDrill;

    public static void loadContent(){
        mechanicalDrill = new ModifiedDrill("mechanical-drill"){{
            requirements(Category.production, with(DustItems.oxidecopper, 15));
            tier = 2;
            drillTime = 700;
            size = 2;
            envEnabled ^= Env.space;

            consumeLiquid(Liquids.water, 0.05f).boost();
        }};

        chloroDrill = new ModifiedDrill("chloro-drill"){{
            requirements(Category.production, with(DustItems.oxidecopper, 12, DustItems.chlorophyte, 10));
            tier = 3;
            drillTime = 450;
            size = 2;
            drawTopUnder = true;

            consumeLiquid(DustLiquids.bioLiquid, 0.1f).boost();
        }};
    }
}
