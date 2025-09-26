package minedusty.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import mindustry.entities.*;
import mindustry.graphics.*;
import minedusty.graphics.DustPalette;

import static arc.Core.atlas;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;


public class DustyEffects {
	
	public static final Rand rand = new Rand();
	public static final Vec2 v = new Vec2();

	public static final Effect

	none = new Effect(0f, 0f, e -> {}),

	sparkles = new Effect(40, e -> {
		rand.setSeed(e.id);
		float angle = rand.random(360f);
		float dist = rand.random(4f);
		float x = e.x + Angles.trnsx(angle, dist);
		float y = e.y + Angles.trnsy(angle, dist);

		color(e.color, Color.valueOf("#fffaf1"),1f - e.finpow());
		
		float randScl = Mathf.randomSeed(e.id, 0.8f, 1.3f);
		float scale = randScl * 2.5f * Interp.pow2Out.apply(1f - Math.abs(e.fin() * 2f - 1f));
		Draw.scl(scale);
		
		float outerSize = 1.5f * scale;
		float innerSize = 0.56f * scale;
		
		// Pain
		for(int i = 0; i < 4; i++){
			float angle1 = i * 90f;
			float angle2 = angle1 + 45f;
			
			float x1 = x + Angles.trnsx(angle1, outerSize);
			float y1 = y + Angles.trnsy(angle1, outerSize);
			float x2 = x + Angles.trnsx(angle2, innerSize);
			float y2 = y + Angles.trnsy(angle2, innerSize);
			float x3 = x + Angles.trnsx(angle1 + 90f, outerSize);
			float y3 = y + Angles.trnsy(angle1 + 90f, outerSize);
			
			Fill.tri(x, y, x1, y1, x2, y2);
			Fill.tri(x, y, x2, y2, x3, y3);
		}
    }).layer(Layer.blockProp - 0.8f),
	
	breakLily = new Effect(35, e -> {
		rand.setSeed(e.id);
        float scl = Math.max(e.rotation / 2, 1);
		float darkFactor = rand.random(0.5f, 1f);
		color(
			e.color.r * darkFactor,
			e.color.g * darkFactor,
			e.color.b * darkFactor
			);
        randLenVectors(e.id, 6 * (int)e.rotation, 19f * e.finpow() * scl, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 3.5f * scl + 0.3f);
        });
    }).layer(Layer.debris),

	airBubble = new Effect(100f, e -> {
		alpha(e.fin() * 0.8f);
		randLenVectors(e.id, 1, e.fin() * 10f, (x, y) -> {
			rect(renderer.bubbles[Math.min((int)(renderer.bubbles.length * Mathf.curveMargin(e.fin(), 0.06f, 0.06f)), renderer.bubbles.length - 1)], e.x + x, e.y + y);
		});
	}).layer(Layer.darkness - 1f), 
	
	marshGas = new Effect(1200f, e -> {
		color(Color.valueOf("#72a027ff"), Color.valueOf("#9fb12dff"), e.fin());

		float progress = e.fin();
		float sizeScale;
		float alpha = 0f;

		if (progress < 0.5f) {
			sizeScale = Interp.pow3Out.apply(0f, 1f, progress * 2f);
			alpha = Interp.pow4Out.apply(0, 0.6f, progress * 2f);
		} else {
			sizeScale = Interp.pow2In.apply(1f, 0f, (progress - 0.5f) * 2f);
			alpha = Interp.pow2In.apply(0.6f, 0f, (progress - 0.5f));
		}
		alpha(alpha);

		float baseSize = 20f * sizeScale * Mathf.randomSeed(e.id, 1f, 1.8f);
		randLenVectors(e.id, 1, 2f + sizeScale * 20f, (x, y) -> {
			float finalSize = baseSize + sizeScale * 30f;
			rect(Core.atlas.find("minedusty-circool"), e.x + x, e.y + y, finalSize, finalSize);
		});		
	}).layer(Layer.legUnit + 3.77f),

	flowWater = new Effect(40f, 250f, e -> {
		float fade = Mathf.clamp(Mathf.slope(e.fin() * 1.3f), 0.3f, 1f); 
		color(Color.valueOf("#8598EC"), Color.valueOf("#CAD2F6"), fade);

		float width = 2.0f * (1.2f - e.fin() * e.fin()); 
		stroke(Mathf.clamp(width, 0.4f, 2f));

		rand.setSeed(e.id);
		float baseAngle = e.rotation;
		float angle = baseAngle + rand.random(-1.5f, 1.5f);
		float offsetX = rand.random(-3.2f, 3.2f);
		float offsetY = rand.random(-3.5f, 1.5f);
		float speed = rand.random(0.15f);
		float travel = e.fslope() * 10f * speed;

		float rise = 12f * e.fin(); 
		float shrink = Mathf.lerp(1f, 0.3f, e.fin());

		float cx = e.x + offsetX + Angles.trnsx(angle, travel);
		float cy = e.y + offsetY + Angles.trnsy(angle, travel);
		cy += Angles.trnsy(baseAngle, rise);
		cx += Angles.trnsx(baseAngle, rise);

		float baseLen = 6.5f + rand.random(1.5f);
		float length = Mathf.sin(e.fin() * Mathf.PI) * baseLen * 2.0f * shrink;

		float half = length / 2f;
		float sx = cx - Angles.trnsx(angle, half);
		float sy = cy - Angles.trnsy(angle, half);

		alpha(fade);
		lineAngle(sx, sy, angle, length);
	}).layer(28f).rotWithParent(true).followParent(true),

	// reduce color brightening
	mistCloud = new Effect(50f, e -> {
		//color(Color.valueOf("#f3f8ffff"), Color.valueOf("#a1d1eeff"), e.fin());
		alpha(Mathf.clamp(e.fslope()* 0.5f, 0f, 1f));

		randLenVectors(e.id, 1, 1f + e.finpow() * 4f, (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 7f + e.fin() * 3f);
		});
	}).layer(Layer.effect + 1.22f),

	treeBreak = new Effect(120f, e -> {
		rand.setSeed(e.id);

		float cutThresh = 0.8f;
		float fade = e.fin() <= cutThresh ? 1f : 1f - Mathf.clamp((e.fin() - cutThresh) / (1f - cutThresh));
		int randCount = Mathf.randomSeed(e.id, 50, 80);

		for (int i = 0; i < randCount; i++) {
			
			float rot = e.rotation + rand.range(180f);
			int randRegion = rand.random(1, 4);
			if (randRegion != 1) { //if leaves
				Color base = e.color.cpy();
				float darkFactor = rand.random(0.5f, 1f);
				base.r *= darkFactor;
				base.g *= darkFactor;
				base.b *= darkFactor;
				color(base, base.a(fade), e.fin());
			} else {
				color(Color.valueOf("ffffff"), Color.valueOf("ffffff").a(fade), e.fin());
			}
			TextureRegion region = Core.atlas.find("minedusty-tree-prop" + randRegion);

			v.trns(rot, rand.random(0f, 12f) * e.finpow());
			float fout = Math.max(e.fout(), 0.5f);
			float size = fout * 20f + 0.8f;
			float rotFactor = rot + rand.random(-180f, 180f) * Interp.pow2Out.apply(Mathf.clamp(e.fin() / 0.8f));

			float spawnRadius = rand.random(0f, 23f);
			Draw.rect(region, e.x + Mathf.cosDeg(rot) * spawnRadius + v.x * 4f, e.y + Mathf.sinDeg(rot) * spawnRadius + v.y * 4f, size, size, rotFactor);
		}
	}).layer(Layer.debris),

	treeBreakWhite = new Effect(120f, e -> {
		rand.setSeed(e.id);

		float cutThresh = 0.8f;
		float fade = e.fin() <= cutThresh ? 1f : 1f - Mathf.clamp((e.fin() - cutThresh) / (1f - cutThresh));
		int randCount = Mathf.randomSeed(e.id, 50, 80);

		for (int i = 0; i < randCount; i++) {
			
			float rot = e.rotation + rand.range(180f);
			int randRegion = rand.random(1, 4);
			TextureRegion region;
			if (randRegion != 1) { //if leaves
				Color base = e.color.cpy();
				float darkFactor = rand.random(0.5f, 1f);
				base.r *= darkFactor;
				base.g *= darkFactor;
				base.b *= darkFactor;
				color(base, base.a(fade), e.fin());
				region = Core.atlas.find("minedusty-tree-prop" + randRegion);
			} else {
				color(Color.valueOf("ffffff"), Color.valueOf("ffffff").a(fade), e.fin());
				region = Core.atlas.find("minedusty-tree-prop-white");
			}

			v.trns(rot, rand.random(0f, 12f) * e.finpow());
			float fout = Math.max(e.fout(), 0.5f);
			float size = fout * 20f + 0.8f;
			float rotFactor = rot + rand.random(-180f, 180f) * Interp.pow2Out.apply(Mathf.clamp(e.fin() / 0.8f));

			float spawnRadius = rand.random(0f, 23f);
			Draw.rect(region, e.x + Mathf.cosDeg(rot) * spawnRadius + v.x * 4f, e.y + Mathf.sinDeg(rot) * spawnRadius + v.y * 4f, size, size, rotFactor);
		}
	}).layer(Layer.debris),

	// TODO use the weather's wind vector or something similar
	fallingLeaves = new Effect(450f, e ->{
		color(e.color, e.color, e.fslope());
		alpha(e.fslope() * 3f);

		float drift = -20f * e.fin() * 4f;
		randLenVectors(e.id, 3, 30f + e.finpow() * 40f, (x, y) -> {
			Draw.rect(atlas.find("minedusty-tree-prop3"), e.x + x + drift, e.y + y + drift, 16f, 16f, e.fin() * 360f);
		});
	}).layer(Layer.darkness + 1),

	rotting = new Effect(35f, e -> {
		color(DustPalette.divineBulletRed, DustPalette.divineBulletRedBack, e.fin());

		randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 1.4f);
		});
	}),

    shootRotFlame = new Effect(38f, 80f, e -> {
        color(DustPalette.divineBulletRed, DustPalette.divineBulletRedBack, DustPalette.divineOutline, e.fin());

        randLenVectors(e.id, 10, e.finpow() * 60f, e.rotation, 10f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.5f);
        });
    });
}
