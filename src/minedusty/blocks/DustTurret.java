package minedusty.blocks;

import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.with;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustLiquids.*;

import arc.graphics.Color;
import arc.math.Interp;
import arc.struct.EnumSet;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.entities.part.RegionPart;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BlockFlag;
import minedusty.content.*;
import minedusty.graphics.DustPalette;
import minedusty.world.blocks.distribution.BuoyMassDriver;

public class DustTurret {
    // 1x1 turrets
    // TODO 3x3 sandspitter shotgun. large flat face which obliterates enemies?
    public static Block sandSpitter, sleet, wipe;
    // 2x2 turrets
    public static Block pellucid, blaze, spout;

    // Special turrets
    public static Block buoyDriver;

    public static void loadContent() {
        // Add custom ammos to vanilla turret(s)
        ((ItemTurret) Blocks.duo).ammoTypes.put(
            DustItems.oxidecopper, new BasicBulletType(2f, 9){{
                width = 7f;
                height = 9f;
                lifetime = 60f;
                ammoMultiplier = 2;

                trailLength = 3;
                trailWidth = 1.2f;

                hitEffect = despawnEffect = Fx.hitBulletColor;
                hitColor = backColor = DustPalette.oxidecopperMid;
                trailColor = DustPalette.oxidecopperBack;
                frontColor = DustPalette.oxidecopper;
            }});

        spout = new LiquidTurret("spout"){{
            requirements(Category.turret, with(aquamerium, 35, oxidecopper, 25, Items.lead, 50));
            researchCost = with(aquamerium, 250, Items.lead, 500);
            consumePower(120f / 60f);
            ammo(
                Liquids.water, new LiquidBulletType(Liquids.water){{
                    knockback = 0.9f;
                    drag = 0.01f;
                    layer = Layer.bullet - 2f;
                }},
                Liquids.slag, new LiquidBulletType(Liquids.slag){{
                    damage = 5f;
                    drag = 0.01f;
                }},
                DustLiquids.bioLiquid, new LiquidBulletType(DustLiquids.bioLiquid){{
                    damage = 2f;
                    splashDamage = 1f;
                    splashDamageRadius = 10f;
                    drag = 0.01f;
                }},
                DustLiquids.saltWater, new LiquidBulletType(DustLiquids.saltWater){{
                    status = DustStatusEffects.saltcorrosion;
                    drag = 0.015f;
                }}
            );
            drawer = new DrawTurret("braced-");
            size = 2;
            recoil = 0f;
            reload = 2.5f;
            inaccuracy = 3f;
            shootCone = 60f;
            liquidCapacity = 12f;
            shootEffect = Fx.shootLiquid;
            outlineColor = DustPalette.turretOutline;
            range = 95f;
            scaledHealth = 220f;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};

        buoyDriver = new BuoyMassDriver("buoy-driver"){{
            requirements(Category.distribution, with(Items.silicon, 25, Items.lead, 60, oxidecopper, 30));
            researchCost = with(lead, 200, oxidecopper, 500);
            size = 2;
            itemCapacity = 30;
            reload = 250f;
            range = 170f;
            consumePower(160f / 60f);

            // Make it water baby
            floating = true;
            requiresWater = true;
            squareSprite = false;
        }};
/* Not working, will redo once i get better at making custom turrets
        wipe = new LiquidTurret("wipe"){{
            requirements(Category.turret, with(Items.lead, 50, aquamerium, 15));
            researchCost = with(oxidecopper, 200, Items.lead, 50);
            ammo(
                Liquids.water, new LiquidBulletType(Liquids.water){{
                    puddleSize = 4f;
                    orbSize = 2.5f;
                    speed = 6f;
                    knockback = 0.8f;
                    drag = 0.005f;
                    layer = Layer.bullet - 2f;
                }},
                DustLiquids.bioLiquid, new LiquidBulletType(DustLiquids.bioLiquid){{
                    puddleSize = 4f;
                    orbSize = 2.5f;
                    speed = 5f;
                    drag = 0.01f;
                    status = DustStatusEffects.healingWash;
                }}
            );
            
            drawer = new DrawTurret("braced-");
            recoil = 0.5f;
            reload = 2f;
            inaccuracy = 2f;
            shootCone = 30f;
            liquidCapacity = 8f;
            shootEffect = Fx.shootLiquid;
            range = 120f;
            health = 250;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};
*/
        // TODO heat for much more damage boost
        sandSpitter = new ItemTurret("sandspitter"){{
            requirements(Category.turret, with(oxidecopper, 45));
            researchCost = with(oxidecopper, 70);
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
                }},
                silicadust, new BasicBulletType(4f, 7.5f){{
                    shoot = new ShootSpread(7, 6f);
                    sprite = "circle-shadow";
                    width = 7f;
                    height = 9f;
                    lifetime = 13f;
                    ammoMultiplier = 3;

                    homingPower = 0.075f;
                    homingRange = 25f;
                    reloadMultiplier = 0.8f;
                    rangeChange = 12;
                    trailLength = 5;
                    trailWidth = 3f;

                    hitEffect = DustyEffects.dustExplosion;
                    despawnEffect = Fx.hitBulletColor;
                    hitColor = backColor = trailColor = Pal.siliconAmmoBack;
                    frontColor = Pal.siliconAmmoFront;
                }}
            );

            drawer = new DrawTurret("braced-"){{
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
            itemCapacity = 6;
            outlineColor = DustPalette.turretOutline;
            // Needs a sandy pew sound
            // shootSound = DustSounds.chromaPew1;

            limitRange(1f);
        }};

        sleet = new ItemTurret("sleet"){{
            requirements(Category.turret, with(oxidecopper, 55, graphite, 12));
            researchCost = with(oxidecopper, 450, graphite, 300);
            ammo(
                aquamerium, new FlakBulletType(3f, 17){{
                    knockback = 0.4f;
                    lifetime = 40;
                    width = height = 11;
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
                        collidesGround = true;
                    }};
                }},
                lead, new FlakBulletType(2.5f, 10f){{
                    lifetime = 40;
                    width = height = 9;
                    splashDamage = 8f;
                    splashDamageRadius = 20f;
                    reloadMultiplier = 0.75f;
                    collidesTiles = false;
                    collides = false;
                    collidesAir = false;
                    scaleLife = true;

                    hitColor = backColor = trailColor = DustPalette.leadAmmoBack;
                    frontColor = DustPalette.leadAmmoFront;
                    despawnEffect = Fx.hitBulletColor;
                    hitShake = 0.6f;
                    hitSound = Sounds.explosion;
                    hitEffect = Fx.flakExplosion;
                    shootEffect = Fx.shootBig;
                    trailEffect = Fx.artilleryTrail;

                    shrinkX = 0.15f;
                    shrinkY = 0.63f;
                    shrinkInterp = Interp.slope;

                    fragBullets = 6;
                    fragBullet = new BasicBulletType(3f, 10){{
                        keepVelocity = true;
                        inaccuracy = 1;
                        width = height = 8;
                        shrinkY = 1;
                        lifetime = 20;
                        backColor = trailColor = DustPalette.leadAmmoBack;
                        hitColor = frontColor = DustPalette.leadAmmoFront;
                        despawnEffect = Fx.none;
                        collidesGround = true;
                    }};
                }},
                graphite, new FlakBulletType(3f, 15f){{
                    lifetime = 40;
                    width = height = 10;
                    splashDamage = 13f;
                    splashDamageRadius = 30f;
                    reloadMultiplier = 0.6f;
                    collidesTiles = false;
                    collides = false;
                    collidesAir = false;
                    scaleLife = true;

                    hitColor = backColor = trailColor = Pal.graphiteAmmoBack;
                    frontColor = Pal.graphiteAmmoFront;
                    despawnEffect = Fx.hitBulletColor;
                    hitShake = 1f;
                    hitSound = Sounds.explosion;
                    hitEffect = Fx.flakExplosion;
                    shootEffect = Fx.shootBig;
                    trailEffect = Fx.artilleryTrail;

                    shrinkX = 0.15f;
                    shrinkY = 0.63f;
                    shrinkInterp = Interp.slope;

                    fragBullets = 3;
                    fragBullet = new BasicBulletType(3f, 12){{
                        keepVelocity = true;
                        inaccuracy = 1;
                        width = height = 8;
                        shrinkY = 1;
                        lifetime = 20;
                        backColor = trailColor = DustPalette.leadAmmoBack;
                        hitColor = frontColor = DustPalette.leadAmmoFront;
                        despawnEffect = Fx.none;
                        collidesGround = true;
                    }};
                }}
            );
            
            drawer = new DrawTurret("braced-"){{
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
            itemCapacity = 6;
            outlineColor = DustPalette.turretOutline;

            limitRange(0);
        }};

        pellucid = new ItemTurret("pellucid"){{
            requirements(Category.turret, with(oxidecopper, 75, chlorophyte, 55, lead, 20));
            researchCost = with(oxidecopper, 500, chlorophyte, 200, lead, 400);

            outlineRadius = 0;
            Effect sfe = new MultiEffect(Fx.shootSmallColor, Fx.colorSpark);

            ammo(
                DustItems.chlorophyte, new BasicBulletType(4.5f, 20){{
                    lifetime = 50f;
                    width = 7f;
                    height = 10f;
                    shootEffect = sfe;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    pierce = true;
                    pierceCap = 6;
                    pierceBuilding = false;
                    hitColor = backColor = trailColor = DustPalette.chlorophyteBack;
                    frontColor = Color.white;
                    trailWidth = 1.8f;
                    trailLength = 5;
                    ammoMultiplier = 3f;
                    // splashDamage = 20f;
                    // splashDamageRadius = 16f;
                }},
                lead, new BasicBulletType(4.5f, 12){{
                    lifetime = 50f;
                    width = 6f;
                    height = 8f;
                    shootEffect = sfe;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    splashDamage = 10f;
                    splashDamageRadius = 15f;
                    hitColor = backColor = trailColor = lead.color;
                    frontColor = Color.white;
                    trailWidth = 1.2f;
                    trailLength = 4;
                    ammoMultiplier = 2f;
                    inaccuracy = 2f;
                    reloadMultiplier = 1.15f;
                }},
                silicon, new BasicBulletType(5f, 18){{
                    lifetime = 50f;
                    width = 7f;
                    height = 10f;
                    shootEffect = sfe;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    pierce = false;
                    pierceCap = 3;
                    pierceBuilding = false;
                    hitColor = backColor = trailColor = Pal.siliconAmmoBack;
                    frontColor = Color.white;
                    trailLength = 7;
                    trailWidth = 3f;
                    homingPower = 0.1f;
                    homingRange = 50f;
                    
                    ammoMultiplier = 3f;
                    reloadMultiplier = 1.1f;
                }}

            );
            drawer = new DrawTurret("braced-");

            reload = 75f;
            range = 140f;
            recoil = 1f;
            size = 2;
            shoot.shotDelay = 7f;
            shoot.shots = 2;
            shootY = -0.5f;
            targetAir = false;
            ammoPerShot = 4;

            rotateSpeed = 2f;
            inaccuracy = 0.5f;
            shootCone = 15f;

            scaledHealth = 170;
            // TODO more lasery shoot sfx
            shootSound = Sounds.shootSnap;
            outlineColor = DustPalette.turretOutline;

            // consumeLiquid(Liquids.water, 12f/60f).boost();

            limitRange(2);
        }};
    }
}
