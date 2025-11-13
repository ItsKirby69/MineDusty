package minedusty.world.blocks.power;

import arc.Core;
import arc.math.*;
import mindustry.graphics.*;
import mindustry.world.blocks.power.PowerNode;
import minedusty.graphics.DustPalette;

import static mindustry.Vars.*;

/** Custom PowerNode only with custom laser textures (for now). */
public class PowerPylon extends PowerNode{
    public PowerPylon(String name, int maxN){
        super(name);
        maxNodes = maxN;
        laserColor2 = DustPalette.laserColor2;
    }

    @Override 
    public void load(){
        super.load();
        laser = Core.atlas.find("minedusty-laser");
        laserEnd = Core.atlas.find("minedusty-laser-end");
    }

    @Override
    public void drawLaser(float x1, float y1, float x2, float y2, int size1, int size2){
        float angle1 = Angles.angle(x1, y1, x2, y2),
            vx = Mathf.cosDeg(angle1), vy = Mathf.sinDeg(angle1),
            len1 = size1 * tilesize / 2f - 1.5f, len2 = size2 * tilesize / 2f - 1.5f;

        Drawf.laser(laser, laserEnd, x1 + vx*len1, y1 + vy*len1, x2 - vx*len2, y2 - vy*len2, laserScale);
    }
}
