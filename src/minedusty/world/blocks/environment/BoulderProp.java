package minedusty.world.blocks.environment;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.world.*;
import mindustry.world.meta.BuildVisibility;

/** Combines both Prop and TallBlock. Mainly created for breakable blocks with shadowOffset. */
public class BoulderProp extends Block{
    public float layer = Layer.blockProp + 1;
    public float shadowOffset = -1f;
    public float shadowAlpha = 0.4f;
    public Color shadowColor = Color.black;

    public BoulderProp(String name){
        this(name, 2);
    }

    public BoulderProp(String name, int variants){
        super(name);
        this.variants = variants;
        customShadow = true;
        breakable = true;
        alwaysReplace = true;
        instantDeconstruct = true;
        unitMoveBreakable = true;
        breakEffect = Fx.breakProp;
        breakSound = Sounds.rockBreak;
        buildVisibility = BuildVisibility.sandboxOnly;
    }

    @Override
    public void init(){
        super.init();
        hasShadow = true;
    }

    @Override
    public void drawBase(Tile tile){
        int variation = variants > 0 ? Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1)) : 0;
        TextureRegion variantShad = variants > 0 ? variantShadowRegions[variation] : customShadowRegion;
        // Making it so theres always some shadow at the edges if shadowOffset isn't 0
        if(shadowOffset < -1f){
            Draw.color(shadowColor, shadowAlpha/2);
            Draw.rect(variantShad,
                tile.worldx(), tile.worldy());
        }
        Draw.color(shadowColor, shadowAlpha);
        Draw.rect(variantShad,
            tile.worldx() + shadowOffset, tile.worldy() + shadowOffset); //, variantShad.width * variantShad.scl(), variantShad.height* variantShad.scl() // potential realistic flipping?
        Draw.color();

        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[variation] : region,
            tile.worldx(), tile.worldy());
    }

    @Override
    public void drawShadow(Tile tile){

    }

    @Override
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }
}
