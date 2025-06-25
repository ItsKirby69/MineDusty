package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.game.Team;
import mindustry.world.*;
import mindustry.world.blocks.environment.OverlayFloor;

/**A type of floor that is overlaid on top of other floors. ADDED EDGE SUPPORT (doesn't seem to work lmfao)*/
public class OverlayFloorEdged extends OverlayFloor{

    public OverlayFloorEdged(String name){
        super(name);
        useColor = false;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return !wallOre || tile.block().solid;
    }
    
    @Override
    public void drawBase(Tile tile){
        Mathf.rand.setSeed(tile.pos());
        Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());

        this.drawEdges(tile);
    }
}
