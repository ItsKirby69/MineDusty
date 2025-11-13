package minedusty.world.blocks.distribution;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.Time;
import mindustry.graphics.*;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.MassDriver;
import minedusty.graphics.DrawPseudo3D;

/** A mass driver built for the waters. Has moving regions depicting it on the water. */
public class BuoyMassDriver extends MassDriver{
    public TextureRegion shadowRegion;
    // Fix for weird offset of block as usual
    float offsetX = 4f;
    float offsetY = 4f;
    // For moving turret barrel a bit forward
    float barrelY = 2f;
    float barrelX = 0f;
    
    float shadowOffset = -2f;
    float bobbingMag = 0.08f;

    public BuoyMassDriver(String name){
        super(name);
        outlineIcon = false;
        outlineRadius = 0;
        customShadow = true;
        hasShadow = true;
    }

    @Override
    public void load(){
        super.load();
        shadowRegion = Core.atlas.find(name + "-shadow");
    }

    // Probably inefficient.
    @Override
    public void drawShadow(Tile tile) {
        float x = tile.worldx(), y = tile.worldy(),
        w = shadowRegion.width * shadowRegion.scl(), h = shadowRegion.height * shadowRegion.scl();

        float bob = 0.98f + Mathf.sin(Time.time / 40f + x * 0.1f + y * 0.1f) * bobbingMag;
        Draw.color(0.0F, 0.0F, 0.0F, BlockRenderer.shadowColor.a);
        Draw.rect(shadowRegion, x + offset, y + offset, w * bob, h * bob);
        Draw.color();
    }

    public class BuoyMassDriverBuild extends MassDriverBuild{

        @Override
        public void draw(){
            Draw.z(Layer.block + 0.05f);
            float x = tile.worldx(), y = tile.worldy(),
            w = baseRegion.width * baseRegion.scl(), h = baseRegion.height * baseRegion.scl();

            float bob = 1f + Mathf.sin(Time.time / 40f + x * 0.1f + y * 0.1f) * bobbingMag;

            // Making it smovin'
            Draw.rect(baseRegion, x + offset, y + offset, w * bob, h * bob);

            Draw.z(Layer.turret);

            // Offset for turret's recoil
            float recoilX = Angles.trnsx(rotation + 180f, reloadCounter * knockback);
            float recoilY = Angles.trnsy(rotation + 180f, reloadCounter * knockback);

            // Offset for actual turret barrel itself
            float offX = Angles.trnsx(rotation, barrelY) + Angles.trnsx(rotation + 90f, barrelX);
            float offY = Angles.trnsy(rotation, barrelY) + Angles.trnsy(rotation + 90f, barrelX);

            Drawf.shadow(region,
            x + offsetX + recoilX + offX - (size / 2),
            y + offsetY + recoilY + offY - (size / 2), rotation - 90);

            // Making it 3d
            float drawX = DrawPseudo3D.xHeight(x, 0.001f);
            float drawY = DrawPseudo3D.yHeight(y, 0.001f);

            Draw.rect(region,
            drawX + offsetX + recoilX + offX,
            drawY + offsetY + recoilY + offY, rotation - 90);
        }
    }
}
