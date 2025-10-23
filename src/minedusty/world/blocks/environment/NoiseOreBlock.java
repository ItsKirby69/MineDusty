package minedusty.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.noise.Simplex;
import mindustry.type.Item;
import mindustry.world.Tile;

/** Used solely for oxide copper */
public class NoiseOreBlock extends TieredOreBlock{
    public TextureRegion[][] stageRegions;
    public int stages = 3;

    public NoiseOreBlock(String name, Item ore){
        super(name, ore);
        this.variants = 3;
        this.mapColor.set(ore.color);
        this.useColor = true;
    }

    public NoiseOreBlock(Item ore){
        this("ore-" + ore.name, ore);
    }

    @Override
    public void load(){
        super.load();
        stageRegions = new TextureRegion[stages][variants];
        for(int s = 0; s < stages; s++){
            for(int v = 0; v < variants; v++){
                // Ex: ore1-3, stage 1 of variant 3
                stageRegions[s][v] = Core.atlas.find(name + (s+1) + "-" + (v+1));
            }
        }
    }

    int variant(Tile tile){
        return Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1));
    }

    int stage(Tile tile){
        float n = Simplex.noise2d(1, 4, 4, 0.01f, tile.x, tile.y);
        int val = Mathf.clamp((int)(n * stages), 0, stages - 1);
        return val;
    }

    @Override
    public void drawBase(Tile tile){
        int s = stage(tile);
        int v = variant(tile);
        // TODO this is kinda hacky, brings back the shadows on the ore.
        float x = tile.worldx(), y = tile.worldy();

        Draw.color(0, 0, 0, 0.3f);
        Draw.rect(stageRegions[s][v], x, y - 0.75f);
        Draw.color();

        Draw.rect(stageRegions[s][v], x, y);
    }


}
