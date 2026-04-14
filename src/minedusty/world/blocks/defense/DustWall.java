package minedusty.world.blocks.defense;

import arc.scene.ui.layout.Table;
import mindustry.world.blocks.defense.*;
import minedusty.world.meta.FrostModule;

/** A wall that may frost up during cold weather. */
public class DustWall extends Wall {
    public FrostModule frost = new FrostModule();
    /** How many default frost variants for Walls. */
    public int frostVariants = 1;

    public DustWall(String name){
        super(name);
        update = true;
        variants = 0;
    }

    @Override
    public void load(){
        super.load();
        // Stages based on size
        frost.init(size, name);
    }

    public class DustWallBuild extends WallBuild {
        public FrostModule.FrostState frostState;

        @Override
        public void created(){
            super.created();
            frostState = frost.createState(this);
        }
        
        float heatTimer = 0f;
        float brittleTimer = 0f;

        @Override
        public void updateTile(){
            frost.update(this, frostState);
        }

        @Override
        public void display(Table table) {
            super.display(table);
            frost.display(table, frostState);
        }

        @Override
        public void damage(float amount){
            super.damage(amount * frost.damageMulti(frostState));
        }

        @Override
        public void draw(){
            super.draw();
            frost.drawFrost(this, frostState, frostVariants);
        }
    }
}