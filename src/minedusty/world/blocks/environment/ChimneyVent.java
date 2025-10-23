package minedusty.world.blocks.environment;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.EnumSet;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;
import minedusty.graphics.DrawPseudo3D;

import static arc.Core.atlas;
import static mindustry.Vars.*;

/** A 2x2 steam vent with cool 3d effects B) */
public class ChimneyVent extends Block{
    public TextureRegion[] columnRegions, topRegions, shadowRegions;
    public int pillarCount = 5;
    public float startScale = 1.45f;
    public float endScale = 0.95f;
    public float totalHeight = 0.006f;

    public Block parent = Blocks.air;
    public Effect effect = Fx.ventSteam;
    public Color effectColor = Pal.vent;
    public float effectChance = 0.01f;

    public ChimneyVent(String name) {
        super(name);
        variants = 1;
        size = 2;
        breakable = false;
        rebuildable = false;
        solid = false;
        update = true;
        customShadow = true;
    }

	@Override
	public boolean canBreak(Tile tile){
		// breakable in sandBox or in-game editor
		return Vars.state.rules.infiniteResources || Vars.state.rules.editor;
	}

    @Override
    public void load(){
        super.load();
        if (variants > 0){
            columnRegions = new TextureRegion[variants];
            topRegions = new TextureRegion[variants];
            shadowRegions = new TextureRegion[variants];

            for(int i = 0; i < variants; i++){
                columnRegions[i] = atlas.find(name + "-column" + (i + 1));
                topRegions[i] = atlas.find(name + "-top" + (i + 1));
                shadowRegions[i] = atlas.find(name + "-shadow" + (i + 1));
            }
        }else{
			variantRegions = new TextureRegion[1];
			variantRegions[0] = atlas.find(name);
            columnRegions = new TextureRegion[1];
            columnRegions[0] = atlas.find(name + "-column");
            topRegions = new TextureRegion[1];
            topRegions[0] = atlas.find(name + "-top");
			shadowRegions = new TextureRegion[1];
			shadowRegions[0] = atlas.find(name + "-shadow");
        }
        region = variantRegions[0];
    }

    @Override
    public void drawBase(Tile tile){
        float x = tile.worldx() + 4, y = tile.worldy() + 4;
        int variation = Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1));

        Draw.z(Layer.blockProp + 0.5f);
        Draw.rect(variantRegions[variation], x, y);
        
        Draw.z(Layer.blockProp + 1f);

        float step  = totalHeight / (pillarCount - 1);
        
        for(int i = 1; i < pillarCount; i++){
            float t = (float)i / (pillarCount -1);
            float scl = Mathf.lerp(startScale, endScale, t);

            float rotato = Mathf.randomSeed(tile.pos() + i, -30, 30);
            float height = i * step;
            float drawX = DrawPseudo3D.xHeight(x, height);
            float drawY = DrawPseudo3D.yHeight(y, height);
            Draw.rect(columnRegions[variation], drawX, drawY, size * tilesize * scl, size * tilesize * scl, rotato);
        }

        Draw.z(Layer.blockProp + 1.5f);
        float height = totalHeight + step;
        float drawX = DrawPseudo3D.xHeight(x, height);
        float drawY = DrawPseudo3D.yHeight(y, height);
        Draw.rect(topRegions[variation], drawX, drawY);

        Draw.z(Layer.blockProp + 0.25f);
        Draw.alpha(0.8f);
        Draw.rect(shadowRegions[variation], x, y);

        if (Mathf.chanceDelta(effectChance)) {
            effect.at(drawX, drawY, 0f, effectColor);
        }
    }

    // @Override
    // public void update(Tile tile) {
    //     if (Mathf.chanceDelta(effectChance)) {
    //         effect.at(tile.worldx(), tile.worldy(), 0f, effectColor);
    //     }
    // }
    
}
