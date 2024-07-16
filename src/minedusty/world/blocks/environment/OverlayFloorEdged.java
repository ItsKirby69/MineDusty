package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.Floor;

/**A type of floor that is overlaid on top of other floors. ADDED EDGE SUPPORT*/
public class OverlayFloorEdged extends Floor{

    public OverlayFloorEdged(String name){
        super(name);
        useColor = false;
    }

    @Override
    public void drawBase(Tile tile){
        Mathf.rand.setSeed(tile.pos());
        Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());

        drawEdges(tile);
    }
}
