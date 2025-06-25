package minedusty.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;

public class BoulderProp extends Block{
    public float layer = Layer.blockProp;
    public float shadowOffset = -1.5f;

    public BoulderProp(String name){
        this(name, 2);
    }

    public BoulderProp(String name, int variants){
        super(name);
        this.variants = variants;
        breakable = true;
        alwaysReplace = true;
        instantDeconstruct = true;
        unitMoveBreakable = true;
        breakEffect = Fx.breakProp;
        breakSound = Sounds.rockBreak;
    }

    @Override
    public void drawBase(Tile tile){
        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region, tile.worldx(), tile.worldy());
    }

    @Override
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }
}
