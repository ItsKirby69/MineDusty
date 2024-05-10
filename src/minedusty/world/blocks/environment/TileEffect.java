package minedusty.world.blocks.environment;

import arc.graphics.*;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.*;
import minedusty.content.DustyEffects;

public class TileEffect extends Floor{

	public Effect effect = DustyEffects.ventSteam;
    public float effectChance = 0.015f;

	public int tilesize = 8;
	public float effectSpacing = 15f;
	public Color effectColor = Pal.vent;
	

    public TileEffect(String name) {
        super(name);
    }
	/* 
    @Override
    public void update(Tile tile) {
        if(Mathf.chance(effectChance)) effect.at(tile.worldx(), tile.worldy(), 0, tile);
    }*/

    @Override
    public void renderUpdate(UpdateRenderState state){
		effect.at(state.tile.x * tilesize - tilesize, state.tile.y * tilesize - tilesize, effectColor);
        /*if((state.data += Time.delta) >= effectSpacing){ //state.tile.block() == Blocks.air && 
            effect.at(state.tile.x * tilesize - tilesize, state.tile.y * tilesize - tilesize, effectColor);
            state.data = 0f;
        } */
    }
}
