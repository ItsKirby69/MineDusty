package minedusty.utils;

import static arc.Core.settings;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.Blocks;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.world.Tile;

public class EffectSystem {

	public Seq<EffectEntry> effects = new Seq<>();
	
	public static class EffectEntry {
		public Effect effect; 
		public Color effectColor = Color.white;
		public Sound soundEffect = Sounds.none;
		public float effectSpacing = 80f;
		public float chance = 0.4f;
		public float rotation = 0f;

		public float timer = 0f;

		public EffectEntry(Effect effect, float chance, float spacing, Color color, Sound sound, float rotation) {
			this.effect = effect;
			this.chance = chance;
			this.effectSpacing = spacing;
			this.effectColor = color;
			this.soundEffect = sound;
			this.rotation = rotation;
		}
	}

	// There is probably a better way to do this ngl
	public void addEffect(Effect effect, float chance, float spacing, Color color, Sound sound) {
		effects.add(new EffectEntry(effect, chance, spacing, color, sound, 0f));
	}

	public void addEffect(Effect effect, float chance, float spacing, Color color) {
		effects.add(new EffectEntry(effect, chance, spacing, color, Sounds.none, 0f));
	}

	public void addEffect(Effect effect, float chance, float spacing, float rotate) {
		effects.add(new EffectEntry(effect, chance, spacing, Color.white, Sounds.none, rotate));
	}

	public void addEffect(Effect effect, float chance, float spacing) {
		effects.add(new EffectEntry(effect, chance, spacing, Color.white, Sounds.none, 0f));
	}

	public void addEffect(Effect effect, Sound sound) {
		effects.add(new EffectEntry(effect, 0.4f, 80f, Color.white, sound, 0f));
	}

	public void addEffect(Effect effect, Color color) {
		effects.add(new EffectEntry(effect, 0.4f, 80f, color, Sounds.none, 0f));
	}

	// Effect all is default.
	public void addEffect(Effect effect) {
		effects.add(new EffectEntry(effect, 0.4f, 80f, Color.white, Sounds.none, 0f));
	}

	//renders per frame? Checks if air block, randomizes and applies spacing
	public void updateEffects(Tile tile) {
		if(tile.block() == Blocks.air) {

			for (EffectEntry entry : effects){
				// if (entry.timer == 0f){
				// 	entry.timer = Mathf.random(entry.effectSpacing);
				// }
				entry.timer += Time.delta;

				if (entry.timer >= entry.effectSpacing){
					if(Mathf.random() < entry.chance){
						entry.effect.at(tile.worldx(), tile.worldy(), entry.rotation, entry.effectColor);
						entry.soundEffect.at(tile.worldx(), tile.worldy(),
							Mathf.random(0.8f, 1.2f), 
							settings.getInt("@setting.dusty-sfx-volume") / 100f); //settings.getInt("@setting.dusty-sfx-volume")
					}
					entry.timer = 0f;
				}
			}
		}
	}
}