package minedusty.graphics;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.world.draw.DrawBubbles;

public class DrawAlphaBubbles extends DrawBubbles{
    public float alpha = 0.5f;

    @Override
    public void draw(Building build) {
        if (!(build.warmup() <= 0.001F)) {
            Draw.color(this.color);
            Draw.alpha(alpha);
            rand.setSeed((long)build.id);

            for(int i = 0; i < this.amount; ++i) {
                float x = rand.range(this.spread);
                float y = rand.range(this.spread);
                float life = 1.0F - (Time.time / this.timeScl + rand.random(this.recurrence)) % this.recurrence;
                if (life > 0.0F) {
                float rad = (1.0F - life) * this.radius;
                if (this.fill) {
                    Fill.circle(build.x + x, build.y + y, rad);
                } else {
                    Lines.stroke(build.warmup() * (life + this.strokeMin));
                    Lines.poly(build.x + x, build.y + y, this.sides, rad);
                }
                }
            }

            Draw.color();
        }
    }
}
