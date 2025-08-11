package minedusty.blocks;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.game.Team;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.Env;
import minedusty.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class DustDrills {
    public static Block mechanicalDrill;

    public static void loadContent(){
        mechanicalDrill = new TeamDrill("mechanical-drill"){{
            requirements(Category.production, with(Items.copper, 12));
            tier = 2;
            drillTime = 600;
            size = 2;
            //mechanical drill doesn't work in space
            envEnabled ^= Env.space;

            consumeLiquid(Liquids.water, 0.2f).boost();
        }};
    }
}
