package minedusty.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
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

	airBubble = new Effect(100f, e -> {
		alpha(e.fin() * 0.8f);
		randLenVectors(e.id, 1, e.fin() * 10f, (x, y) -> {
			//REMINDER: margins changed from 0.06f -> 0.1f. This makes that weird effect seen now
			rect(renderer.bubbles[Math.min((int)(renderer.bubbles.length * Mathf.curveMargin(e.fin(), 0.06f, 0.06f)), renderer.bubbles.length - 1)], e.x + x, e.y + y);
		});
	}).layer(Layer.darkness - 1f), 
	
	marshGas = new Effect(220f, e -> {
		color(e.color, Color.valueOf("346524"), e.fin());
		alpha(e.fslope()* 0.4f);

		randLenVectors(e.id, 3, 2f + e.finpow() * 20f, (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 16f + e.fin() * 9f);
		});
	}),

	flowWater = new Effect(20f, 250f, e -> {
		color(Color.valueOf("#8598EC"), Color.valueOf("#CAD2F6"), e.fslope());
		stroke(1f * (0.8f + (e.fslope() / 2f)));

		rand.setSeed(e.id);
		float angle = 90f + rand.random(-1.5f, 1.5f);
		float offsetX = rand.random(-3.2f, 3.2f);
		float offsetY = rand.random(-3.5f, 1.5f);
		float speed = rand.random(0.15f);
		float travel = e.fslope() * 10f * speed;

		float x = e.x + offsetX + Angles.trnsx(angle, travel);
		float y = e.y + offsetY + Angles.trnsx(angle, travel);

		float length = Mathf.sin(e.fin() * Mathf.PI) * (4.5f + rand.random(1.5f));
		alpha(e.fslope());

		lineAngle(x,y,angle,length);

	}).layer(30.1f),

	mistCloud = new Effect(50f, e -> {
		color(Color.valueOf("#aac1e3ff"), Color.valueOf("#cae6f6"), e.fin());
		alpha(e.fslope()* 0.4f);

		randLenVectors(e.id, 1, 2f + e.finpow() * 10f, (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 5f + e.fin() * 3f);
		});
	}),

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
	}).layer(Layer.power + 2),

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
