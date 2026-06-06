package minedusty.utils;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.graphics.Layer;
import minedusty.content.DustyEffects;

import static arc.graphics.g2d.Draw.*;

import arc.Core;

/** Stores epic effect manipulation methods for easier use in other classes */
public class EffectHelper {
	/** Allows passthrough for color on given effects. 
     * Used for coloring tree breaking particles (mapColor) or
	 * bullet effect colors for different ammo with same effects.
     */
	public static Effect colorEffect(Effect originalEffect, Color color){
		return new Effect(originalEffect.lifetime, e -> {
			e.color = color;
			originalEffect.renderer.get(e);
		}){{
			clip = originalEffect.clip;
			followParent = originalEffect.followParent;
			rotWithParent = originalEffect.rotWithParent;
			layer = originalEffect.layer;
		}};
	}

	/** Specialized bullet orb creating effect */
	public static Effect orbChargeBegin(float lifetime, Color color){
		return colorEffect( new Effect(lifetime, e -> {
			float margin = 1f - Mathf.curve(e.fin(), 0.9f);
			float fin = Math.min(margin, e.fin());

			color(e.color);
			Fill.circle(e.x, e.y, fin * 6f);

			color();
			Fill.circle(e.x, e.y, fin * 4f);
		}).followParent(true).rotWithParent(true), color);
	}

	// Generalized tree effect
	public static Effect treeBreakEffect(float life, int quantity, int maxRegionId, String tex, float sizeDiv, float spawnRad, float lay){
		return new Effect(life, e -> {
			DustyEffects.rand.setSeed(e.id);

			float cutThresh = 0.8f;
			float fade = e.fin() <= cutThresh ? 1f : 1f - Mathf.clamp((e.fin() - cutThresh) / (1f - cutThresh));
			int randCount = Mathf.randomSeed(e.id, quantity, quantity + 30);

			for (int i = 0; i < randCount; i++) {
				
				float rot = e.rotation + DustyEffects.rand.range(180f);
				int randRegion = DustyEffects.rand.random(1, maxRegionId);
				if (randRegion != 1) { //don't color the stick
					Color base = e.color.cpy();
					float darkFactor = DustyEffects.rand.random(0.5f, 1f);
					base.r *= darkFactor;
					base.g *= darkFactor;
					base.b *= darkFactor;
					color(base, base.a(fade), e.fin());
				} else {
					color(Color.valueOf("ffffff"), Color.valueOf("ffffff").a(fade), e.fin());
				}
				TextureRegion region = Core.atlas.find(tex + randRegion, "minedusty-tree-prop" + randRegion);

				DustyEffects.v.trns(rot, DustyEffects.rand.random(0f, 12f) * e.finpow());
				float fout = Math.max(e.fout(), 0.5f);
				float size = fout * (region.width/sizeDiv) + 0.8f;
				float rotFactor = rot + DustyEffects.rand.random(-180f, 180f) * Interp.pow2Out.apply(Mathf.clamp(e.fin() / 0.8f));

				float spawnRadius = DustyEffects.rand.random(0f, spawnRad);
				Draw.rect(region, e.x + Mathf.cosDeg(rot) * spawnRadius + DustyEffects.v.x * 4f, e.y + Mathf.sinDeg(rot) * spawnRadius + DustyEffects.v.y * 4f, size, size, rotFactor);
			}
		}).layer(lay);
	}
	
	// Generalized stump effect
	public static Effect stumpBreakEffect(float life, int quantity, int maxRegionId, String tex, float sizeDiv, float spawnRad){
		return new Effect(life, e -> {
			DustyEffects.rand.setSeed(e.id);

			float cutThresh = 0.8f;
			float fade = e.fin() <= cutThresh ? 1f : 1f - Mathf.clamp((e.fin() - cutThresh) / (1f - cutThresh));
			int randCount = Mathf.randomSeed(e.id, quantity, quantity + 5);

			for (int i = 0; i < randCount; i++) {
				
				float rot = e.rotation + DustyEffects.rand.range(180f);
				int randRegion = DustyEffects.rand.random(1, maxRegionId);
				color(Color.valueOf("ffffff"), Color.valueOf("ffffff").a(fade), e.fin());
				
				TextureRegion region = Core.atlas.find(tex + randRegion, "minedusty-tree-stump" + randRegion);

				DustyEffects.v.trns(rot, DustyEffects.rand.random(0f, 12f) * e.finpow());
				float fout = Math.max(e.fout(), 0.5f);
				float size = fout * (region.width/(sizeDiv * DustyEffects.rand.random(0.8f, 1f))) + 0.8f;
				float rotFactor = rot + DustyEffects.rand.random(-180f, 180f) * Interp.pow2Out.apply(Mathf.clamp(e.fin() / 0.8f));

				float spawnRadius = DustyEffects.rand.random(0f, spawnRad);
				Draw.rect(region, e.x + Mathf.cosDeg(rot) * spawnRadius + DustyEffects.v.x * 4f, e.y + Mathf.sinDeg(rot) * spawnRadius + DustyEffects.v.y * 4f, size, size, rotFactor);
			}
		}).layer(Layer.blockOver);
	}

	// Generalized
	public static ParticleEffect partEffect(Color fromColor, Color toColor, float fromWidth, float toWidth, float life, float conen, float spinn, int parts, float lengthh, String tex){
		return partEffect(fromColor, toColor, fromWidth, toWidth, life, conen, spinn, parts, lengthh, tex, true, Interp.linear);
	}
	public static ParticleEffect partEffect(Color fromColor, Color toColor, float fromWidth, float toWidth, float life, float conen, float spinn, int parts, float lengthh, String tex, Boolean booeal, Interp inter){
		return new ParticleEffect(){{
			followParent = rotWithParent = booeal;
			length = lengthh;
			particles = parts;
			region = tex;
			lifetime = life;
			cone = conen;
			spin = spinn;
			sizeFrom = fromWidth;
			sizeTo = toWidth;
			colorFrom = fromColor;
			colorTo = toColor.cpy().a(0f);
			baseRotation = 90f;
			interp = inter;
		}};
	}

	// Flashes
	public static ParticleEffect flashEffect(Color fromColor, Color toColor, float width, float life){
		return flashEffect(fromColor, toColor, width, life, 0f, 0f,"minedusty-flash-hori");
	}

	public static ParticleEffect flashEffect(Color fromColor, Color toColor, float width, float life, float cone, float spin){
		return flashEffect(fromColor, toColor, width, life, cone, spin, "minedusty-flash-hori");
	}

	public static ParticleEffect flashEffect(Color fromColor, Color toColor, float width, float life, float conen, float spinn, String flash){
		return partEffect(fromColor, toColor, width, 0f, life, conen, spinn, 1, 0f, flash);
	}

	// Waves
	public static ParticleEffect waveEffect(Color fromColor, Color toColor, float width, float life){
		return waveEffect(fromColor, toColor, width, life, "minedusty-splashwave");
	}

	public static ParticleEffect waveEffect(Color fromColor, Color toColor, float width, float life, String tex){
		return partEffect(fromColor, toColor, 0f, width, life, 0f, 0f, 1, 0f, "minedusty-splashwave", false, Interp.pow2Out);
	}
}
