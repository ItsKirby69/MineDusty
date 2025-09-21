package minedusty.world.blocks.environment;

import mindustry.editor.EditorTile;
import mindustry.game.Team;
import mindustry.world.Tile;

public class WaterEffectTile extends TileEffect{
    
    public WaterEffectTile(String name){ 
        super(name);
        variants = 0;
        useColor = false;
        rotate = true;
        update = true;
        requiresWater = true;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return !wallOre || tile.block().solid;
    }

    @Override
    public void drawBase(Tile tile){
        if(tile instanceof EditorTile){
            super.drawBase(tile);
        }
    }
}
