package minedusty.content;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.*;
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
			alpha(e.fslope()* 0.7f);
	
			randLenVectors(e.id, 3, 2f + e.finpow() * 20f, (x, y) -> {
				Fill.circle(e.x + x, e.y + y, 16f + e.fin() * 3f);
			});
		});
}
