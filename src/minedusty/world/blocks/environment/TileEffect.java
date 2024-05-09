package minedusty.world.blocks.environment;

import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.world.Tile;
import minedusty.content.DustyEffects;

public class TileEffect extends TickFloor{

	public Effect effect = DustyEffects.ventSteam;
    public float effectChance = 0.015f;

    public TileEffect(String name) {
        super(name);
    }

    @Override
    public void update(Tile tile) {
        if(Mathf.chance(effectChance)) effect.at(tile.worldx(), tile.worldy(), 0, tile);
    }
}
