package minedusty.world.blocks.environment;

import static arc.Core.settings;

import arc.audio.Sound;
import arc.graphics.*;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.Blocks;
import mindustry.entities.*;
import mindustry.gen.Sounds;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
//import mindustry.Vars.*;

/** Custom Floor which at a chance spawns an effect. */
public class TileEffect extends Floor{

	public Block parent = Blocks.air;

	public Seq<EffectEntry> effects = new Seq<>();
	
	public static class EffectEntry {
		public Effect effect; 
		public Color effectColor = Color.white;
		public Sound soundEffect = Sounds.none;
		public float effectSpacing = 80f;
		public float chance = 0.4f;

		public float timer = 0f;

		public EffectEntry(Effect effect, float chance, float spacing, Color color, Sound sound) {
			this.effect = effect;
			this.chance = chance;
			this.effectSpacing = spacing;
			this.effectColor = color;
			this.soundEffect = sound;
		}
	}
	
	public TileEffect(String name){
		super(name);
		variants = 3;
	}

	@Override
	public void drawBase(Tile tile){
		parent.drawBase(tile);

		Mathf.rand.setSeed(tile.pos());
		Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
	}
	
	//updates
	@Override
	public boolean updateRender(Tile tile){
		return true;
	}

	public void addEffect(Effect effect, float chance, float spacing, Color color, Sound sound) {
		effects.add(new EffectEntry(effect, chance, spacing, color, sound));
	}
	
	// Effect color and sounds default.
	public void addEffect(Effect effect, float chance, float spacing) {
		effects.add(new EffectEntry(effect, chance, spacing, Color.white, Sounds.none));
	}

	// Effect all default except sound.
	public void addEffect(Effect effect, Sound sound) {
		effects.add(new EffectEntry(effect, 0.4f, 80f, Color.white, sound));
	}

	// Effect all default except color.
	public void addEffect(Effect effect, Color color) {
		effects.add(new EffectEntry(effect, 0.4f, 80f, color, Sounds.none));
	}

	// Effect all is default.
	public void addEffect(Effect effect) {
		effects.add(new EffectEntry(effect, 0.4f, 80f, Color.white, Sounds.none));
	}

	//renders per frame? Checks if air block, randomizes and applies spacing
	@Override
	public void renderUpdate(UpdateRenderState state){
		if(state.tile.block() == Blocks.air) {

			for (EffectEntry entry : effects){
				if (entry.timer == 0f){
					entry.timer = Mathf.random(entry.effectSpacing);
				}
				entry.timer += Time.delta;

				if (entry.timer >= entry.effectSpacing){
					
					if(Mathf.random() < entry.chance){
						entry.effect.at(state.tile.worldx(), state.tile.worldy(), entry.effectColor);
						entry.soundEffect.at(state.tile.worldx(), state.tile.worldy(),
							Mathf.random(0.8f, 1.2f), 
							settings.getInt("@setting.dusty-sfx-volume") / 100f);
					}
					entry.timer = 0f;
				}			
			}
		}
	}
}

