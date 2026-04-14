package minedusty.world.blocks.environment;

import static arc.Core.settings;

import arc.audio.Sound;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.editor.EditorTile;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import minedusty.content.DustyEffects;

import static mindustry.Vars.*;

// Rotatable special effect  An overlay
public class FlowWaterTile extends Block {
    public Effect effect = DustyEffects.flowWater;
    public Sound soundEffect = Sounds.none;
    public float effectSpacing = 6f;
    public int angles = 16;

    public FlowWaterTile(String name) {
        super(name);
        variants = 0;
        rotate = true;
        update = true;
        targetable = false;
        underBullets = true;
        hasShadow = false;
        customShadow = false;
        solid = false;
        useColor = false;
        placeableLiquid = true;
        breakable = false;
        destructible = false;
        alwaysReplace = false;

		drawTeamOverlay = false;
		forceTeam = Team.derelict;
		allowDerelictRepair = false;

        configurable = true;
        saveConfig = true;
        clearOnDoubleTap = true;

        config(Integer.class, (FlowWaterTileBuild build, Integer angle) -> {
           build.angleIndex = angle; 
        });

        buildType = () -> new FlowWaterTileBuild();
    }

    @Override
    public void drawBase(Tile tile){
        if(tile instanceof EditorTile){
            if(tile.build instanceof FlowWaterTileBuild build){
                Draw.rect(region, tile.worldx(), tile.worldy(), build.getAngle());
            }
        }
    }

	@Override
	public boolean canBreak(Tile tile){
		return Vars.state.rules.editor;
	}

    // I will burn this planet // I can't believe I spent like 4 hours straight trying to figure out why
    // the damn effect isn't spawning now realizing that updateTile() doesn't update in the in-game editor.
    public class FlowWaterTileBuild extends Building {
        public float effectTimer;
        public int angleIndex = 0;

        @Override
        public void damage(float amoutn){
        }
        
        public void updateTile(){
            super.updateTile();
            effectTimer += Time.delta;

            if (effectTimer >= effectSpacing){
                effect.at(tile.worldx(), tile.worldy(), getAngle());
                soundEffect.at(tile.worldx(), tile.worldy(),
                    Mathf.random(0.8f, 1.2f), 
                    settings.getInt("@setting.dusty-sfx-volume") / 100f);
                effectTimer = 0f;
            }
        }

        @Override
        public Integer config(){
            return angleIndex;
        }

        @Override
        public boolean onConfigureTapped(float x, float y){
            // Angle to clicked position :D
            float distance = Mathf.len(x - this.x, y - this.y);

            // Ignore changes if at position or beyond 3 tiles
            if(distance < tilesize || distance > tilesize * 3f){
                return false;
            }

            float angle = Mathf.atan2(x - this.x, y - this.y) * Mathf.radDeg;
            int snappedIndex = Mathf.round(angle / (360f  / angles));
            configure(Mathf.mod(snappedIndex, angles));
            return false;
        }

        @Override
        public void drawConfigure(){
            Drawf.poly(x, y, angles, tilesize * 1.5f, 360f/angles/2, Pal.accent);

            float knobX = x + Angles.trnsx(getAngle(), tilesize * 1.5f);
            float knobY = y + Angles.trnsy(getAngle(), tilesize * 1.5f);
            Draw.color(Pal.accent);
            Fill.circle(knobX, knobY, 3f);
            Lines.stroke(1f, Pal.gray);
            Lines.circle(knobX, knobY, 3f);
            Draw.reset();
        }

        @Override
        public void drawSelect(){
            Draw.reset();
        }

        public float getAngle(){
            return angleIndex * (360f / angles);
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y, getAngle());
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(angleIndex);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read);

            if(revision > 0){
                angleIndex = read.i();
            }else{
                angleIndex = rotation;
            }
        }
    }
}
