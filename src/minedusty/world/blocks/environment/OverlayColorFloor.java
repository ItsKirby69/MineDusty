package minedusty.world.blocks.environment;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.world.*;
import mindustry.world.blocks.environment.OverlayFloor;

/** An overlay floor which changes colors depending on tile it's placed on */
public class OverlayColorFloor extends OverlayFloor{
    public Seq<Block> maskBlock = new Seq<>();
    public Color maskColor = Color.clear;
    public Color baseColor = Color.white;

    public OverlayColorFloor(String name) {
        super(name);
    }
    
    @Override
    public void load(){
        super.load();
    }
    
    @Override
    public void drawBase(Tile tile){
        if(maskBlock.contains(tile.floor())) {
            Draw.color(maskColor);
            Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
        }else{
            Draw.color(baseColor);
            Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
        }
        Draw.reset();
    }
}
