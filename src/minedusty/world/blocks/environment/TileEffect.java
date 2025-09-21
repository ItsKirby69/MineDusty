package minedusty.world.blocks.environment;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
//import mindustry.Vars.*;
import minedusty.utils.EffectSystem;

/** Custom Floor which at a chance spawns an effect. */
public class TileEffect extends Floor{
	public EffectSystem fxSystem = new EffectSystem();
	
	public TileEffect(String name){
		super(name);
		variants = 3;
	}

	public TileEffect(String name, int variants){
		super(name);
		this.variants = variants;
	}

    public void addEffect(Effect effect, float chance, float spacing, Color color, Sound sound) {
        fxSystem.addEffect(effect, chance, spacing, color, sound);
    }

    public void addEffect(Effect effect, float chance, float spacing, Color color) {
        fxSystem.addEffect(effect, chance, spacing, color);
    }

    public void addEffect(Effect effect, float chance, float spacing, float rotate) {
        fxSystem.addEffect(effect, chance, spacing, rotate);
    }
	
    public void addEffect(Effect effect, float chance, float spacing) {
        fxSystem.addEffect(effect, chance, spacing);
    }

    public void addEffect(Effect effect, Sound sound) {
        fxSystem.addEffect(effect, sound);
    }

    public void addEffect(Effect effect, Color color) {
        fxSystem.addEffect(effect, color);
    }

    public void addEffect(Effect effect) {
        fxSystem.addEffect(effect);
    }

	@Override
	public void drawBase(Tile tile){

		Mathf.rand.setSeed(tile.pos());
		Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
	}
	
	//updates
	@Override
	public boolean updateRender(Tile tile){
		return true;
	}

	@Override
	public void renderUpdate(UpdateRenderState state) {
		fxSystem.updateEffects(state.tile);
	}
}

