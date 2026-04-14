package minedusty.world.entities.bullets;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;

public class OrbBulletType extends BulletType{
    public float orbRadius = 12f;

    public OrbBulletType(float speed, float damage){
        super(speed, damage);
        pierce = true;
        pierceBuilding = false;
        hittable = true;
        absorbable = true;
        hitSize = orbRadius * 2f;
    }

    @Override
    public void draw(Bullet b){
        Draw.color(Color.white);
        Fill.circle(b.x, b.y, orbRadius);
    }

}
