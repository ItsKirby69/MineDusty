package minedusty.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;
import mindustry.world.*;

public class BetterTallBlock extends Block{
    public float shadowOffset = -3f;
    public float layer = Layer.power + 1;
    public float shadowLayer = Layer.power - 1;
    public float rotationRand = 20f;
    public float shadowAlpha = 0.6f;

    public BetterTallBlock(String name){
        super(name);
        solid = true;
        clipSize = 90;
        customShadow = true;
        targetable = false;
        createRubble = false;
        underBullets = true;
        update = true;
        breakable = false;
        drawTeamOverlay = false;
        allowConfigInventory = false;
        rebuildable = false;
        forceTeam = Team.derelict;
        size = 3;
    }

    @Override
    public void init(){
        super.init();
        drawTeamOverlay = false;
    }

    // @Override
    // public boolean hasBuilding(){
    //     return false;
    // }

    @Override
    public boolean canBreak(Tile tile){
        return false;
    }

    @Override
    public void drawBase(Tile tile){
        float rot = Mathf.randomSeedRange(tile.pos() + 1, rotationRand);

        Draw.z(shadowLayer);
        Draw.color(0f, 0f, 0f, shadowAlpha);
        Draw.rect(variants > 0 ? variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))] : customShadowRegion,
            tile.worldx() + shadowOffset, tile.worldy() + shadowOffset, rot);

        Draw.color();

        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
            tile.worldx(), tile.worldy(), rot);
    }

    @Override
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }

    public class BetterTallBuild extends Building{
        
        @Override
        public void damage(float dmg){}

        @Override
        public boolean collide(Bullet other) {
            return false;
        }

        @Override
        public void drawSelect(){}

        @Override
        public void draw(){}

        @Override
        public float healthf(){
            return 1f;
        }

        @Override
        public void killed(){}

    }
}
