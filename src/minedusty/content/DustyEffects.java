package minedusty.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.*;

public class DustyEffects {
	
	public static final Rand rand = new Rand();
	public static final Vec2 v = new Vec2();

	public static final Effect

		none = new Effect(0f, 0f, e -> {}),
	
		test = new Effect(50, e -> {
			Draw.color(Color.white, Pal.plastanium, Pal.darkMetal, e.fin());

		}),
		
		//based off of Endless rusting!11!!
        testbubbles = new Effect(323f, 80f, e -> {
            color(Liquids.water.color, Color.gray, e.finpow());
            Draw.alpha(e.finpow() * 0.75f + 0.25f);

            Draw.z(Mathf.lerp(Layer.groundUnit + 1, Layer.flyingUnit + 4, Math.min(e.fin() * 2, 1)));

            randLenVectors(e.id, 2, e.finpow() * 12, e.rotation, 360, (x, y) -> {
                Fill.circle(e.x + x, e.y + y + Math.max(e.finpow() - 0.3f, 0)/7 * 10 * 9 * Tmp.v1.set(x, y).len()/10f * 9, e.fout() * 1.6f);
            });
        }),

		ventSteam = new Effect(140f, e -> {
			color(e.color, Pal.vent2, e.fin());
	
			alpha(e.fslope() * 0.78f);
	
			float length = 3f + e.finpow() * 10f;
			rand.setSeed(e.id);
			for(int i = 0; i < rand.random(3, 5); i++){
				v.trns(rand.random(360f), rand.random(length));
				Fill.circle(e.x + v.x, e.y + v.y, rand.random(1.2f, 3.5f) + e.fslope() * 1.1f);
			}
		}).layer(Layer.darkness - 1);

		/* 
		bubbles = new Effect(140f, e -> {
			color(e.color, Pal.vent2, e.fin());
			
			alpha(e.fslope() * 0.78f);
			
			float length = 3f + e.finpow() * 10f;
			rand.setSeed(e.id);
			for(int i = 0; i < rand.random(3,5); i++){
				v.trns(rand.random(360f), rand.random(length));
				Fill.circle(e.x + v.x, e.y + v.y, rand.random(1.2f, 3.5f) + e.fslope() * 1.1f);
			}
		}).layer(Layer.darkness - 1),

		airBubble = new Effect(100f, e -> {
			randLenVectors(e.id, 1, e.fin() * 12f, (x, y) -> {
				rect(renderer.bubbles[Math.min((int)(renderer.bubbles.length * Mathf.curveMargin(e.fin(), 0.11f, 0.06f)), renderer.bubbles.length - 1)], e.x + x, e.y + y);
			});
		}).layer(Layer.flyingUnitLow + 1);
		*/
}
