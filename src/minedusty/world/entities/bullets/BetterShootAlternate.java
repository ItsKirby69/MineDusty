package minedusty.world.entities.bullets;

import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.entities.pattern.ShootPattern;

/** A alternating burst of shots pattern. */
public class BetterShootAlternate extends ShootPattern{
    public int barrels = 2;
    public float spread = 5f;
    public int barrelOffset = 1;
    public boolean mirror = false;

    private int burstCount = 0;

    public BetterShootAlternate(float spread){
        this.spread = spread;
    }

    public BetterShootAlternate(){
    }

    @Override
    public void flip(){
        mirror = !mirror;
    }

    @Override
    public void shoot(int totalShots, BulletHandler handler, @Nullable Runnable barrelIncrementer){
        float index = ((burstCount + barrelOffset) % barrels) - (barrels-1)/2f;
        if(barrelIncrementer != null) barrelIncrementer.run();
        burstCount += 1;
        for(int i = 0; i < shots; i++){
            handler.shoot(index * spread * -Mathf.sign(mirror), 0, 0f, firstShotDelay + shotDelay * i);      
        }
    }
}
