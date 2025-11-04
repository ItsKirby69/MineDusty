package minedusty.world.blocks.environment;

import static arc.Core.settings;

import arc.math.Mathf;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.TreeBlock;
import mindustry.world.meta.BuildVisibility;

/** Just a tree block with spawning effects */
public class TreeBlockEffect extends TreeBlock{
    public Effect effect = Fx.none;
    public float radius = 2f;

    public TreeBlockEffect(String name){
        super(name);
        emitLight = true;
        shadowOffset = -1.5f;
        buildVisibility = BuildVisibility.sandboxOnly;

    }

    @Override
    public void drawBase(Tile tile){
        super.drawBase(tile);
        if(Vars.state.isPaused()) return;
        int effectChance = settings.getInt("@setting.dusty-falling-density");
        if(settings.getBool("@setting.dusty-falling-leaves-enabled") && Mathf.chanceDelta((effectChance * 0.002f) * size)){
            effect.at(
                tile.worldx() + Mathf.range(6f) * radius,
                tile.worldy() + Mathf.range(6f) * radius,
                mapColor
            );
        }
    }
}
