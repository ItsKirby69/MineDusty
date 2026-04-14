package minedusty.world.blocks.environment;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.world.*;
import mindustry.world.blocks.environment.OverlayFloor;
import minedusty.blocks.DustEnv;

/** An overlay floor which changes colors depending on tile it's placed on */
public class OverlayColorFloor extends OverlayFloor{
    public Seq<Block> maskBlock = new Seq<>();
    public Color maskColor = Color.valueOf("#e1e9f0");

    public OverlayColorFloor(String name) {
        super(name);
    }
    
    @Override
    public void load(){
        super.load();
        maskBlock.set(Seq.with(DustEnv.slushSnow, Blocks.ice));
    }
    
    @Override
    public void drawBase(Tile tile){
        if(!maskBlock.contains(tile.floor())) {
            Draw.blend(Blending.additive);
            Draw.color(maskColor);
        }
        Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
        Draw.blend();
        Draw.reset();
    }
}
