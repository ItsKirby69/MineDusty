package minedusty.content;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.world.Block;
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

    redCloud = new Effect(80f, e -> {
        color(DustPalette.divineBulletRed);
        randLenVectors(e.id, e.fin(), 7, 9f, (x, y, fin, fout) -> {
            Fill.circle(e.x + x, e.y + y, 5f * fout);
        });
    }),

    corrosionSalt = new Effect(70f, e -> {
        color(e.color);
        alpha(Interp.pow2Out.apply(e.fslope()) * 0.45f);

        randLenVectors(e.id, 1, 8f + e.finpow() * 2.5f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 4f);
        });
    }),
	
    steam = new Effect(100, e -> {
        color(Pal.lightishGray);
        alpha(e.fslope() * 0.8f);

        rand.setSeed(e.id);
        for(int i = 0; i < 7; i++){
            v.trns(rand.random(360f), rand.random(e.finpow() * 18f)).add(e.x, e.y);
            Fill.circle(v.x, v.y, rand.random(1f, 1.8f));
        }
    }).layer(Layer.bullet - 1f),

    carbonicDust = new Effect(100, e -> {
        color(DustPalette.carbon);
        alpha(e.fslope() * 0.8f);

        rand.setSeed(e.id);
        for(int i = 0; i < 5; i++){
            v.trns(rand.random(360f), rand.random(e.finpow() * 10f)).add(e.x, e.y);
            Fill.circle(v.x, v.y, rand.random(1.4f, 3.4f));
        }
    }).layer(Layer.bullet - 1f),

    healingwet = new Effect(80f, e -> {
        color(Color.valueOf("#25b69eff"), Color.valueOf("#60e0d8"), e.fin());
        alpha(Mathf.clamp(e.fin() * 2f));

        // Fill.circle(e.x, e.y, e.fout());
        randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 1.4f);
        });
    }),

    healWallhealing = new Effect(25, e -> {
        if(!(e.data instanceof Block block)) return;

		blend(Blending.additive);
        color(Color.white, 0.2f);
        alpha(e.fout());
        Draw.rect(block.fullIcon, e.x, e.y);
		blend();
		reset();
    }),

	sandExplosion = new Effect(20, e -> {
        color(DustPalette.sandColor);

        e.scaled(5, i -> {
            stroke(3f * i.fout());
            Lines.circle(e.x, e.y, 3f + i.fin() * 10f);
        });

        color(DustPalette.sandColorBack);

        randLenVectors(e.id, 5, 2f + 23f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.5f);
        });

        color(DustPalette.sandColor);
        stroke(e.fout());

        randLenVectors(e.id + 1, 4, 1f + 23f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
        });

        Drawf.light(e.x, e.y, 50f, DustPalette.sandColor, 0.8f * e.fout());
    }),

	dustExplosion = new Effect(20, e -> {
        color(Pal.siliconAmmoFront);

        e.scaled(8, i -> {
            stroke(3f * i.fout());
            Lines.circle(e.x, e.y, 3f + i.fin() * 10f);
        });

        color(Pal.siliconAmmoBack);

        randLenVectors(e.id, 5, 2f + 23f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.5f);
        });

        color(Pal.siliconAmmoFront);
        stroke(e.fout());

        randLenVectors(e.id + 1, 4, 1f + 23f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
        });

        Drawf.light(e.x, e.y, 20f, Pal.siliconAmmoFront, 0.8f * e.fout());
    }),

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

	flowWater = new Effect(85f, 250f, e -> {
		float thresh = 0.3f;
		float fade = e.fin() < thresh ?
			Interp.pow4Out.apply(e.fin() / thresh) :
			1f - Interp.pow2In.apply((e.fin() - thresh) / (1f - thresh));
		
		fade *= 0.5f; 

		color(Color.valueOf("#4f5fb8"), Color.valueOf("#edefffff"), e.finpow());

		rand.setSeed(e.id);
		float baseAngle = e.rotation;
		float angle = baseAngle + rand.random(-2f, 2f);
		// Keep in mind that the default position of the effect is pointing East
		float offsetX = -13f;
		float offsetY = rand.random(-6f, 6f);
		// Offsets according to direction.
		float ox = Angles.trnsx(angle, offsetX, offsetY);
		float oy = Angles.trnsy(angle, offsetX, offsetY);

		float speed = rand.random(0.15f);
		float travel = 10f * speed;

		float rise = 43f * e.fin() * Mathf.clamp(1.75f - e.finpow(), 0f, 1f);
		float shrink = 1f; //Mathf.lerp(1f, 0.2f, e.finpow());

		float cx = e.x + ox + Angles.trnsx(angle, travel);
		float cy = e.y + oy + Angles.trnsy(angle, travel);
		cy += Angles.trnsy(baseAngle, rise);
		cx += Angles.trnsx(baseAngle, rise);

		float baseSize = 5f + rand.random(2f);
		float len = baseSize * (2f + shrink);
		float wid = baseSize * (1f * 0.7f) * shrink * Mathf.clamp(e.finpow() * 7f - 4f, 1f, 10f); //Mathf.sin(e.fin() * Mathf.PI)

		alpha(fade);
		Draw.rect(Core.atlas.find("minedusty-circooler"), cx, cy, len, wid, angle);
		// lineAngle(sx, sy, angle, length);
	}).layer(Layer.block - 0.1f).rotWithParent(true).followParent(true),

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
			float size = fout * (region.width/3.2f) + 0.8f;
			float rotFactor = rot + rand.random(-180f, 180f) * Interp.pow2Out.apply(Mathf.clamp(e.fin() / 0.8f));

			float spawnRadius = rand.random(0f, 23f);
			Draw.rect(region, e.x + Mathf.cosDeg(rot) * spawnRadius + v.x * 4f, e.y + Mathf.sinDeg(rot) * spawnRadius + v.y * 4f, size, size, rotFactor);
		}
	}).layer(Layer.blockOver),

	treeBreakLarge = new Effect(180f, e -> {
		rand.setSeed(e.id);

		float cutThresh = 0.8f;
		float fade = e.fin() <= cutThresh ? 1f : 1f - Mathf.clamp((e.fin() - cutThresh) / (1f - cutThresh));
		int randCount = Mathf.randomSeed(e.id, 100, 120);

		for (int i = 0; i < randCount; i++) {
			
			float rot = e.rotation + rand.range(180f);
			int randRegion = rand.random(1, 5);
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
			float size = fout * (region.width/2.6f) + 0.8f;
			float rotFactor = rot + rand.random(-180f, 180f) * Interp.pow2Out.apply(Mathf.clamp(e.fin() / 0.8f));

			float spawnRadius = rand.random(0f, 39f);
			Draw.rect(region, e.x + Mathf.cosDeg(rot) * spawnRadius + v.x * 4f, e.y + Mathf.sinDeg(rot) * spawnRadius + v.y * 4f, size, size, rotFactor);
		}
	}).layer(Layer.blockOver),

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
			float size = fout * (region.width/3.2f) + 0.8f;
			float rotFactor = rot + rand.random(-180f, 180f) * Interp.pow2Out.apply(Mathf.clamp(e.fin() / 0.8f));

			float spawnRadius = rand.random(0f, 23f);
			Draw.rect(region, e.x + Mathf.cosDeg(rot) * spawnRadius + v.x * 4f, e.y + Mathf.sinDeg(rot) * spawnRadius + v.y * 4f, size, size, rotFactor);
		}
	}).layer(Layer.debris),

	fallingLeaves = new Effect(450f, 150f, e ->{
		color(e.color, e.color, e.fslope());
		alpha(e.fslope() * 3f);

		float drift = -20f * e.fin() * 4f;
		randLenVectors(e.id, 1, 30f + e.finpow() * 40f, (x, y) -> {
			Draw.rect(atlas.find("minedusty-tree-prop3"), e.x + x + drift, e.y + y + drift, 16f, 16f, e.fin() * 360f);
		});
	}).layer(Layer.darkness + 1),

	fallingEmbers = new Effect(140f, e ->{
		float a = Mathf.sin(e.fin() * Mathf.PI) * 0.9f;
		a = Mathf.clamp(a, 0f, 0.8f);
		Color curr;

		float drift = -10f * e.fin() * 2.5f;
		if(e.fin() < 0.8f){
			float t = e.fin() * 2f;
			curr = Color.valueOf("#ee6339ff").lerp(Color.valueOf("#eda445"), t);
		}else{
			float t = (e.fin() - 0.8f) * 2f;
			curr = Color.valueOf("#b3b78dff").lerp(Color.valueOf("#595959ff"), t);
		}
		curr.a(a);
		Draw.blend(Blending.additive);
		Draw.color(curr);

		float oscX = Mathf.sin((e.fin() * 6f) + e.id) * 4f;
		float oscY = Mathf.cos((e.fin() * 5f) + e.id) * 4f;

		float radi = Mathf.clamp((e.fslope() + 0.7f), 0.7f, 1f);

		randLenVectors(e.id, 1, 30f + e.finpow() * 40f, (x, y) -> {
			Draw.rect(atlas.find("minedusty-circooler"), e.x + drift + oscX, e.y + drift + oscY, 4.5f * radi, 4.5f * radi, e.fin());
		});
		Draw.blend();
		Draw.reset();
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
