package minedusty.content;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import mindustry.entities.*;
import mindustry.graphics.*;
import minedusty.graphics.DustPalette;

import static arc.Core.atlas;
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
		alpha(e.fslope()* 0.4f);

		randLenVectors(e.id, 3, 2f + e.finpow() * 20f, (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 16f + e.fin() * 9f);
		});
	}),

	flowWater = new Effect(20f, e -> {
		color(Color.valueOf("#8598EC"), Color.valueOf("#CAD2F6"), e.fslope());
		Lines.stroke(1f * (0.8f + (e.fslope() / 2f)));

		rand.setSeed(e.id);
		float angle = 90f + rand.random(-1.5f, 1.5f);
		float offsetX = rand.random(-3.2f, 3.2f);
		float offsetY = rand.random(-3f, 1f);
		float speed = rand.random(0.15f);
		float travel = e.fslope() * 10f * speed;

		float x = e.x + offsetX + Angles.trnsx(angle, travel);
		float y = e.y + offsetY + Angles.trnsx(angle, travel);

		float length = Mathf.sin(e.fin() * Mathf.PI) * (4.5f + rand.random(1.5f));
		alpha(e.fslope());

		Lines.lineAngle(x,y,angle,length);
	}).layer(30.1f),

	
	fallingLeaves = new Effect(450f, e ->{
		color(e.color, e.color, e.fslope());
		alpha(e.fslope() * 3f);

		float drift = -20f * e.fin() * 4f;
		randLenVectors(e.id, 3, 30f + e.finpow() * 40f, (x, y) -> {
			Draw.rect(atlas.find("minedusty-leaf"), e.x + x + drift, e.y + y + drift, 8f, 8f, e.fin() * 360f);
		});
		
		Drawf.light(e.x, e.y, 15f * e.fslope(), Color.black, 0.4f * e.fout());
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
