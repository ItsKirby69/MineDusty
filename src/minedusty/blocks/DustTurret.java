package minedusty.blocks;

import static minedusty.content.DustItems.*;

import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.entities.part.RegionPart;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.DrawTurret;
import minedusty.content.*;
import minedusty.graphics.DustPalette;

public class DustTurret {
    // 1x1 turrets
    public static Block sandSpitter, sleet;
    // 2x2 turrets
    public static Block hyaline;

    public static void loadContent() {
        sandSpitter = new ItemTurret("sandspitter"){{
            ammo(
                Items.sand, new BasicBulletType(5.5f, 4){{
                    shoot = new ShootSpread(9, 5.5f);
                    sprite = "circle-shadow";
                    knockback = 1.2f;
                    width = 5f;
                    height = 8f;
                    lifetime = 6f;
                    ammoMultiplier = 2f;
                    splashDamage = 3.5f;
                    splashDamageRadius = 12f;
                    hitEffect = DustyEffects.sandExplosion;
                    despawnEffect = Fx.hitBulletColor;
                    hitColor = backColor = trailColor = DustPalette.sandColorBack;
                    frontColor = DustPalette.sandColor;
                }}
            );

            drawer = new DrawTurret(){{
                // TODO have heat region be on base instead
                parts.add(new RegionPart("-barrel"){{
                    progress = PartProgress.recoil;
                    under = true;
                    moveY = -1f;
                    
                }});
            }};

            velocityRnd = 0.2f;
            reload = 50f;

            recoil = 1.3f;
            shootY = 3f;
            range = 60;
            shootCone = 30f;
            health = 300;
            inaccuracy = 12f;
            rotateSpeed = 6f;
            targetAir = false;
            // Needs a sandy pew sound
            // shootSound = DustSounds.chromaPew1;

            limitRange(1f);
        }};

        sleet = new ItemTurret("sleet"){{
            ammo(
                aquamerium, new FlakBulletType(3f, 17){{
                    knockback = 0.4f;
                    lifetime = 40;
                    width = height = 11;
                    collidesTiles = false;
                    splashDamage = 15f;
                    splashDamageRadius = 24f;
                    collidesTiles = false;
                    collides = false;
                    collidesAir = false;
                    scaleLife = true;

                    hitColor = backColor = trailColor = DustPalette.aquameriumBack;
                    frontColor = DustPalette.aquamerium;
                    despawnEffect = Fx.hitBulletColor;
                    hitShake = 1f;
                    hitSound = Sounds.explosion;
                    hitEffect = Fx.flakExplosion;
                    shootEffect = Fx.shootBig;
                    trailEffect = Fx.artilleryTrail;
                    scaleLife = true;

                    shrinkX = 0.15f;
                    shrinkY = 0.63f;
                    shrinkInterp = Interp.slope;

                    fragBullets = 3;
                    fragBullet = new BasicBulletType(3f, 12){{
                        keepVelocity = true;
                        inaccuracy = 4;
                        width = 8;
                        height = 10;
                        shrinkY = 1;
                        lifetime = 20;
                        backColor = trailColor = DustPalette.aquameriumBack;
                        hitColor = frontColor = DustPalette.aquamerium;
                        despawnEffect = Fx.none;
                        collidesGround = false;
                    }};
                }}
            );
            
            drawer = new DrawTurret(){{
                // Borrowed and modified from Aquarion's Vector turret
                parts.addAll(new RegionPart("-pellet") {{
                    under = true;
                    colorTo = new Color(1f, 1f, 1f, 0f);
                    color = Color.white;
                    progress = PartProgress.reload;
                    recoilIndex = 0;
                    mixColor = new Color(1f, 1f, 1f, 0f);
                    mixColorTo = Color.black;
                    y = 0f;
                    moveY = -1f;
                }});
                parts.add(new RegionPart("-back"){{
                    under = true;                    
                }});
            }};
            targetAir = false;
            reload = 60f;
            recoil = 2f;
            range = 200;
            health = 250;
            shootCone = 12f;
            inaccuracy = 2f;
            shootSound = Sounds.bang;

            limitRange(0);
        }};

        // hyaline = new ItemTurret("hyaline"){{

        // }};
    }
}
