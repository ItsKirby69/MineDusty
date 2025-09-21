package minedusty.world.blocks.environment;

import static arc.Core.settings;

import arc.audio.Sound;
import arc.math.Mathf;
import arc.util.*;
import mindustry.editor.EditorTile;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.world.*;
import minedusty.content.DustyEffects;

// Rotatable special effect block. An overlay
public class FlowWaterTile extends Block {
    public Effect effect = DustyEffects.flowWater;
    public Sound soundEffect = Sounds.none;
    public float effectSpacing = 8f;
    public float chance = 1.0f; // unused

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

        buildType = () -> new FlowWaterTileBuild();
    }

    @Override
    public void drawBase(Tile tile){
        if(tile instanceof EditorTile){
            super.drawBase(tile);
        }
    }

    // I will burn this planet // I can't believe I spent like 4 hours straight trying to figure out why
    // the damn effect isn't spawning now realizing that updateTile() doesn't update in the in-game editor.
    public class FlowWaterTileBuild extends Building {
        public float effectTimer;

        // @Override
        // public void created() {
        //     super.created();
        // }
        
        public void updateTile(){
            super.updateTile();
            effectTimer += Time.delta;

            if (effectTimer >= effectSpacing){
                // if(Mathf.random() < chance){
                effect.at(tile.worldx(), tile.worldy(), rotdeg());
                soundEffect.at(tile.worldx(), tile.worldy(),
                    Mathf.random(0.8f, 1.2f), 
                    settings.getInt("@setting.dusty-sfx-volume") / 100f); //settings.getInt("@setting.dusty-sfx-volume")
                // }
                effectTimer = 0f;
            }
        }
    }
}
