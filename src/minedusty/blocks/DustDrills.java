package minedusty.blocks;

import mindustry.content.*;
import mindustry.type.Category;
import mindustry.world.*;
import mindustry.world.meta.Env;
import minedusty.content.DustItems;
import minedusty.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class DustDrills {
    public static Block mechanicalDrill;

    public static void loadContent(){
        mechanicalDrill = new TeamDrill("mechanical-drill"){{
            requirements(Category.production, with(DustItems.oxidecopper, 15));
            tier = 2;
            drillTime = 700;
            size = 2;
            envEnabled ^= Env.space;

            consumeLiquid(Liquids.water, 0.4f).boost();
        }};
    }
}
