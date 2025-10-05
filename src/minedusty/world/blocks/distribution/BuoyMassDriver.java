package minedusty.world.blocks.distribution;

import arc.graphics.g2d.Draw;
import arc.math.*;
import arc.util.Time;
import mindustry.graphics.*;
import mindustry.world.blocks.distribution.MassDriver;
import minedusty.graphics.DrawPseudo3D;

/** A mass driver built for the waters. Has moving regions depicting it on the water. */
public class BuoyMassDriver extends MassDriver{
    // Fix for weird offset of block as usual
    float offsetX = 4f;
    float offsetY = 4f;
    // For moving turret barrel a bit forward
    float barrelY = 2f;
    float barrelX = 0f;

    public BuoyMassDriver(String name){
        super(name);
        outlineIcon = false;
        outlineRadius = 0;
    }

    public class BuoyMassDriverBuild extends MassDriverBuild{

        @Override
        public void draw(){
            Draw.z(Layer.block + 0.05f);
            float x = tile.worldx(), y = tile.worldy(),
            w = baseRegion.width * baseRegion.scl(), h = baseRegion.height * baseRegion.scl(),
            scl = 12f, mag = 0.2f;

            float swayX = Mathf.sin(y*2 + Time.time, scl, mag) + Mathf.sin(x*2 - Time.time, 70, 1f);
            float swayY = Mathf.sin(y*2 + Time.time + 16, scl + 3f, mag) + Mathf.sin(x*2 - Time.time, 50, 0.3f);

            // Making it smovin'
            Draw.rectv(baseRegion, x + offsetX, y + offsetY, w, h, 
                vec -> vec.add(swayX * 0.6f, swayY * 0.6f)
            );

            Draw.z(Layer.turret);

            // Offset for turret's recoil
            float recoilX = Angles.trnsx(rotation + 180f, reloadCounter * knockback);
            float recoilY = Angles.trnsy(rotation + 180f, reloadCounter * knockback);

            // Offset for actual turret barrel itself
            float offX = Angles.trnsx(rotation, barrelY) + Angles.trnsx(rotation + 90f, barrelX);
            float offY = Angles.trnsy(rotation, barrelY) + Angles.trnsy(rotation + 90f, barrelX);

            Drawf.shadow(region,
            x + offsetX + swayX + recoilX - (size / 2),
            y + offsetY + swayY + recoilY - (size / 2), rotation - 90);
            
            // Making it 3d
            float drawX = DrawPseudo3D.xHeight(x, 0.001f);
            float drawY = DrawPseudo3D.yHeight(y, 0.001f);

            // TODO maybe make the turret region itself wobble. Or show counter wobbling effects.
            Draw.rect(region,
            drawX + offsetX + recoilX + offX - (swayX * 0.34f),
            drawY + offsetY + recoilY + offY - (swayY * 0.34f), rotation - 90);
        }
    }
}
