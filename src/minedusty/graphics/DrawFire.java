package minedusty.graphics;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;
import minedusty.world.blocks.power.LanternBlock;
import minedusty.world.blocks.power.LanternBlock.LanternBuild;

public class DrawFire extends DrawBlock{
    public TextureRegion top;

    public float flameRadiusScl = 3.5f, flameRadiusMag = 1.5f, flameRadiusInMag = 1f;
    public float flameRadius = 3f, flameRadiusIn = 1.9f;
    public float flameX = 0, flameY = 0;

    public DrawFire(){
    }

    @Override
    public void load(Block block){
        top = Core.atlas.find(block.name + "-top");
    }

    @Override
    public void draw(Building build){
        if(!(build instanceof LanternBuild lantern)) return;

        float bright = lantern.brightnessScale;
        float power = lantern.efficiency;
        if(bright <= 0.001f) return;

        float g = 0.3f;
        float r = 0.06f;
        float cr = Mathf.random(0.1f);

        float outer = 0.5f + flameRadius * bright;
        float inner = 0.5f + flameRadiusIn * bright;

        Color flameCol = Tmp.c1.set(((LanternBlock)lantern.block).dimColor).lerp(((LanternBlock)lantern.block).brightColor, bright);
        Draw.z(Layer.block + 0.01f);

        Draw.alpha(power);
        Draw.rect(top, build.x, build.y);

        float sca = flameRadiusScl + (flameRadiusScl/2) * bright * power;

        Draw.alpha(((1f - g) + Mathf.absin(Time.time, 8f, g) + Mathf.random(r) - r) * power);
        Draw.tint(flameCol);
        Fill.circle(build.x + flameX, build.y + flameY, outer + Mathf.absin(Time.time, sca, flameRadiusMag) + cr);

        Draw.alpha(power);
        Draw.color(1f, 1f, 1f, power);
        Fill.circle(build.x + flameX, build.y + flameY, inner + Mathf.absin(Time.time, sca, flameRadiusInMag) + cr);

        Draw.color();
    }
}
