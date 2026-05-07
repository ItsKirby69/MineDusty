package minedusty.blocks;

import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.with;
import static minedusty.content.DustItems.*;
import static minedusty.content.DustLiquids.*;

import arc.graphics.Color;
import arc.math.Interp;
import arc.struct.EnumSet;
import mindustry.content.*;
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
import minedusty.world.blocks.defense.ChargeTurret;
import minedusty.world.blocks.distribution.BuoyMassDriver;
import minedusty.world.entities.bullets.BetterShootAlternate;

public class DustTurrets {
    // Basic turrets
    public static Block sandSpitter, sandHammer;
    public static Block sleet, pellucid, cascade;
    // Special turrets
    public static Block volt;
    public static Block spout, pistil;

    // WIP
    public static Block blaze, wipe;

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

        cascade = new ItemTurret("cascade"){{
            requirements(Category.turret, with(chlorophyte, 45, oxidecopper, 60));
            ammo(
                Items.lead, new FlakBulletType(4f, 3){{
                    lifetime = 60f;
                    ammoMultiplier = 3f;
                    shootEffect = Fx.shootSmall;
                    width = 5.5f;
                    height = 7f;
                    hitEffect = Fx.flakExplosion;
                    splashDamage = 20f * 1.5f;
                    splashDamageRadius = 12f;
                    reloadMultiplier = 0.8f;
                    shoot.shotDelay = 6f;
                }},
                aquamerium, new FlakBulletType(4.5f, 4){{
                    backColor = trailColor = DustPalette.aquameriumBack;
                    hitColor = frontColor = DustPalette.aquamerium;
                    despawnEffect = Fx.hitBulletColor;

                    lifetime = 60f;
                    ammoMultiplier = 4f;
                    shootEffect = Fx.shootSmall;
                    width = 6f;
                    height = 8f;
                    hitEffect = Fx.flakExplosion;
                    splashDamage = 27f * 1.5f;
                    splashDamageRadius = 20f;
                    fragBullets = 6;
                    fragBullet = new BasicBulletType(3f, 5){{
                        width = 5f;
                        height = 10f;
                        shrinkY = 1f;
                        lifetime = 15f;
                        backColor = trailColor = DustPalette.aquamerium;
                        hitColor = frontColor = DustPalette.aquameriumBack;
                        despawnEffect = Fx.none;
                        collidesGround = false;
                    }};
                }},
                amethyst, new FlakBulletType(5.1f, 2){{
                    hitColor = backColor = trailColor = DustPalette.amethystBack;
                    frontColor = DustPalette.amethyst;
                    despawnEffect = Fx.hitBulletColor;

                    lifetime = 60f;
                    ammoMultiplier = 4f;
                    shootEffect = Fx.shootSmall;
                    width = 6f;
                    height = 8f;
                    hitEffect = Fx.flakExplosion;
                    splashDamage = 32f * 1.5f;
                    splashDamageRadius = 25f;
                    fragBullets = 8;
                    reloadMultiplier = 1.15f;
                    trailLength = 5;
                    trailWidth = 2.5f;
                    fragBullet = new BasicBulletType(4f, 5){{
                        width = 6.5f;
                        height = 9f;
                        shrinkY = 1.2f;
                        lifetime = 12f;
                        hitColor = backColor = trailColor = DustPalette.amethystBack;
                        frontColor = DustPalette.amethyst;
                        despawnEffect = Fx.none;
                        collidesGround = true;
                    }};
                }}
            );

            reload = 45f;
            range = 240f;
            size = 2;
            targetGround = false;
            outlineRadius = 0;

            shoot = new BetterShootAlternate();
            shoot.shotDelay = 4f;
            shoot.shots = 3;

            drawer = new DrawTurret(){{
                for(int i = 0; i < 2; i++){
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")){{
                        progress = PartProgress.recoil;
                        recoilIndex = f;
                        under = true;
                        moveY = -1.25f;
                    }});
                }
            }};

            recoil = 1f;
            recoils = 2;
            rotateSpeed = 12f;
            inaccuracy = 15f;
            shootCone = 45f;

            scaledHealth = 180;
            shootSound = Sounds.shootScatter;
            coolant = consumeCoolant(0.2f);
            depositCooldown = 0.5f;

            limitRange(2);
        }};
        
        volt = new PowerTurret("volt"){{
            requirements(Category.turret, with(oxidecopper, 50, graphite, 20));
            researchCost = with(oxidecopper, 450, graphite, 300);
            shootType = new LaserBulletType(){{
                damage = 18;
                recoil = 0f;
                sideAngle = 35f;
                width = 12f;
                length = 120f;
                sideWidth = 1f;
                sideLength = 45f;
                collidesTeam = true;
                colors = new Color[]{DustPalette.aquamerium.cpy().mul(1f, 1f, 1f, 0.4f), DustPalette.aquamerium, Color.white};

                //for visual stats only.
                buildingDamageMultiplier = 0.25f;
                status = StatusEffects.shocked;
                statusDuration = 60f * 5f;
            }};
            reload = 45f;
            shootCone = 50f;
            rotateSpeed = 6f;
            range = 90f;
            shootEffect = Fx.lightningShoot;
            heatColor = Color.red;
            recoil = 1.2f;
            size = 1;
            health = 240;
            shootSound = Sounds.shootArc;
            consumePower(3f);
            coolant = consumeCoolant(4.5f/60f);
        }};

        pistil = new ChargeTurret("pistil"){{
            requirements(Category.turret, with(chlorophyte, 60, graphite, 45));
            researchCost = with(chlorophyte, 450);
            MultiEffect multEff = new MultiEffect(DustyEffects.orbCharge, DustyEffects.orbChargeBegin(120f));
            ammo(
                DustLiquids.bioLiquid, new BasicBulletType(1.2f, 60f, "minedusty-energy-orb"){{
                    lifetime = 230f;
                    width = height = 15f;
                    lightRadius = 30f;
                    shrinkX = shrinkY = 0.25f;
                    chargeEffect = multEff;
                    hitEffect = DustyEffects.hitChloroSpark;
                    despawnEffect = Fx.none;
                    pierceCap = 10;
                    pierceBuilding = false;
                    backColor = DustPalette.chlorophyteBack;
                    lightColor = DustPalette.chlorophyte;
                    frontColor = hitColor= DustPalette.chlorophyteWater;
                    ammoMultiplier = 0.4f;

                    homingPower = 0.024f;
                    homingRange = 180f;

                    splashDamage = 40f;
                    splashDamageRadius = 16f;
                }},
                saltWater, new BasicBulletType(1.4f, 45f, "minedusty-energy-orb"){{
                    lifetime = 180f;
                    width = height = 18f;
                    lightRadius = 30f;
                    lightOpacity = 0.2f;
                    shrinkX = shrinkY = 0.25f;
                    chargeEffect = multEff;
                    hitEffect = DustyEffects.hitChloroSpark;
                    status = DustStatusEffects.saltcorrosion;
                    despawnEffect = Fx.none;
                    pierceCap = 5;
                    pierceBuilding = false;
                    reloadMultiplier = 1.25f;
                    backColor = DustPalette.saltColorBack;
                    lightColor = frontColor = hitColor = DustPalette.saltColor;
                    ammoMultiplier = 0.25f;

                    homingPower = 0.02f;
                    homingRange = 120f;

                    splashDamage = 25f;
                    splashDamageRadius = 16f;
                }},
                Liquids.water, new BasicBulletType(2.2f, 15f, "minedusty-energy-orb"){{
                    lifetime = 160f;
                    width = height = 15f;
                    lightRadius = 20f;
                    lightOpacity = 0.2f;
                    shrinkX = shrinkY = 0.25f;
                    chargeEffect = multEff;
                    hitEffect = DustyEffects.hitChloroSpark;
                    status = StatusEffects.wet;
                    despawnEffect = Fx.none;
                    pierceCap = 10;
                    pierceBuilding = false;
                    reloadMultiplier = 1.5f;
                    backColor = hitColor = Pal.water;
                    lightColor = frontColor = DustPalette.waterFront;
                    ammoMultiplier = 0.1f;

                    homingPower = 0.02f;
                    homingRange = 120f;

                    splashDamage = 25f;
                    splashDamageRadius = 34f;
                }}
            );

            drawer = new DrawTurret("braced-"){{
                parts.add(new RegionPart("-arm"){{
                    progress = PartProgress.warmup;
                    moveRot = -12;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.recoil, 0f, 0f, -4f));
                    under = true;
                    cooldownTime = 160f;
                }});
            }};

            consumePower(270f / 60f);
            
            range = 240f;
            recoil = 1f;
            size = 2;
            reload = 220f;
            shoot.firstShotDelay = 120;
            shootY = 5f;
            targetAir = false;
            ammoPerShot = 20;

            rotateSpeed = 1.5f;
            shootCone = 15f;

            scaledHealth = 280;

            loopSound = Sounds.none;
            shootEffect = Fx.hitLiquid;
            shootSound = Sounds.shootSap;
            chargeSound = Sounds.chargeLancer;
            outlineColor = DustPalette.turretOutline;
            outlineRadius = 0;
        }};

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
                bioLiquid, new LiquidBulletType(bioLiquid){{
                    damage = 2f;
                    splashDamage = 1f;
                    splashDamageRadius = 10f;
                    drag = 0.01f;
                }},
                saltWater, new LiquidBulletType(saltWater){{
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
            range = 210f;
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
                salt, new BasicBulletType(7.5f, 0.5f){{
                    shoot = new ShootSpread(8, 5.5f);
                    sprite = "circle-shadow";
                    knockback = 0.5f;
                    width = 4f;
                    height = 6f;
                    lifetime = 6f;
                    ammoMultiplier = 1f;
                    reloadMultiplier = 2.5f;
                    hitEffect = Fx.hitBulletColor;
                    despawnEffect = Fx.hitBulletColor;
                    hitColor = backColor = trailColor = salt.color;
                    frontColor = Color.valueOf("#dbf1f3");
                }},
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
                    reloadMultiplier = 0.88f;
                    rangeChange = 12;
                    trailLength = 5;
                    trailWidth = 3f;

                    hitEffect = DustyEffects.dustExplosion;
                    despawnEffect = Fx.hitBulletColor;
                    hitColor = backColor = trailColor = Pal.siliconAmmoBack;
                    frontColor = Pal.siliconAmmoFront;
                }},
                amethyst, new BasicBulletType(5.1f, 12f){{
                    shoot = new ShootSpread(7, 6f);
                    sprite = "minedusty-shard-bullet";
                    width = 7f;
                    height = 9f;
                    lifetime = 13f;
                    ammoMultiplier = 2;

                    reloadMultiplier = 0.8f;
                    rangeChange = 12;
                    trailLength = 5;
                    trailWidth = 2.5f;

                    hitEffect = Fx.hitBulletColor; // TODO make it more exciting
                    despawnEffect = Fx.hitBulletColor;
                    hitColor = backColor = trailColor = DustPalette.amethystBack;
                    frontColor = DustPalette.amethyst;
                }}
            );
            coolant = consumeLiquid(Liquids.water, 6f/60f);
            coolantMultiplier = 10f;

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
            range = 75;
            shootCone = 30f;
            health = 300;
            inaccuracy = 12f;
            rotateSpeed = 6f;
            targetAir = false;
            itemCapacity = 12;
            outlineColor = DustPalette.turretOutline;
            // Needs a sandy pew sound
            // shootSound = DustSounds.chromaPew1;

            limitRange(1f);
        }};

        sandHammer = new ItemTurret("sandhammer"){{
            requirements(Category.turret, with(silicon, 120, chlorophyte, 75, oxidecopper, 55));
            researchCost = with(silicon, 250, chlorophyte, 200, oxidecopper, 1200);
            consumeLiquid(Liquids.water, 6f/60f);
            ammo(
                Items.sand, new BasicBulletType(7f, 45, "circle-shadow"){{
                    shoot = new ShootSpread(20, 3f);
                    knockback = 4f;
                    width = 6f;
                    height = 10f;
                    lifetime = 15f;
                    splashDamage = 25f;
                    splashDamageRadius = 12f;
                    hitEffect = DustyEffects.sandExplosion;
                    despawnEffect = Fx.hitBulletColor;
                    hitColor = backColor = trailColor = DustPalette.sandColorBack;
                    frontColor = DustPalette.sandColor;
                }},
                amethyst, new BasicBulletType(8.1f, 58f, "minedusty-shard-bullet"){{
                    shoot = new ShootSpread(18, 3f);
                    width = 7f;
                    height = 9f;
                    lifetime = 17f;
                    knockback = 2f;
                    
                    reloadMultiplier = 0.85f;
                    trailLength = 5;
                    trailWidth = 2.5f;

                    hitEffect = Fx.hitBulletColor;
                    despawnEffect = Fx.hitBulletColor;
                    hitColor = backColor = trailColor = DustPalette.amethystBack;
                    frontColor = DustPalette.amethyst;
                }}
            );

            drawer = new DrawTurret("braced-"){{
                parts.add(new RegionPart("-barrel"){{
                    progress = PartProgress.recoil;
                    under = true;
                    moveY = -1.5f;
                }});
            }};

            cooldownTime = 75f;

            velocityRnd = 0.12f;
            reload = 120f;
            ammoPerShot = 5;

            recoil = 3f;
            shootY = 6f;
            range = 90f;
            shootCone = 30f;
            health = 460;
            inaccuracy = 12f;
            size = 3;
            itemCapacity = 20;
            outlineColor = DustPalette.turretOutline;
            shootSound = Sounds.shootFuse;
            shootSoundVolume = 0.8f;
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
            shootSound = Sounds.shootArtillerySmall;
            itemCapacity = 6;
            outlineColor = DustPalette.turretOutline;

            limitRange(0);
        }};

        pellucid = new ItemTurret("pellucid"){{
            requirements(Category.turret, with(oxidecopper, 75, chlorophyte, 55, lead, 20));
            researchCost = with(oxidecopper, 500, chlorophyte, 200, lead, 400);

            outlineRadius = 0;

            ammo(
                DustItems.chlorophyte, new BasicBulletType(4.5f, 20){{
                    lifetime = 50f;
                    width = 7f;
                    height = 10f;
                    shootEffect = new MultiEffect(Fx.shootSmallColor, Fx.colorSpark);
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
                    shootEffect = new MultiEffect(Fx.shootSmallColor, Fx.colorSpark);
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
                    shootEffect = new MultiEffect(Fx.shootSmallColor, Fx.colorSpark);
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
            shootY = 1.5f;
            targetAir = false;
            ammoPerShot = 4;

            rotateSpeed = 2f;
            inaccuracy = 0.5f;
            shootCone = 15f;

            scaledHealth = 170;
            // TODO more lasery shoot sfx
            shootSound = Sounds.shootScatter;
            outlineColor = DustPalette.turretOutline;

            // consumeLiquid(Liquids.water, 12f/60f).boost();

            limitRange(2);
        }};
    }
}
