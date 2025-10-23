package minedusty.type.unit;

import mindustry.entities.bullet.LiquidBulletType;
import mindustry.gen.*;
import mindustry.type.Liquid;

// need to make it heal damaged units and blocks

public class HealingLiquidBulletType extends LiquidBulletType {
    public HealingLiquidBulletType(Liquid liquid){
        super(liquid);
        hittable = false;
        // collidesTeam = true;
        collidesTiles = true;
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        // pool at end of lifetime
        if(b.time >= lifetime){
            b.remove();
        }
    }

}