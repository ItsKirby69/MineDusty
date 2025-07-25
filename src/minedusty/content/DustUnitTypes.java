package minedusty.content;

import arc.graphics.Color;
import ent.anno.Annotations.*;
import mindustry.ai.types.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import minedusty.graphics.DustPalette;
import minedusty.type.unit.DivineUnitType;

public class DustUnitTypes {

	// core units
	public static UnitType cricket, lotus, mantis;

	//region Enemy units

	//mechs
	public static @EntityDef({Unitc.class, Legsc.class}) UnitType divineFlathead;
	public static @EntityDef({Unitc.class, Mechc.class}) UnitType divineSwarmer; // crawler and suicide type units
	public static @EntityDef({Unitc.class, Mechc.class}) UnitType divineCyst, divineGlaive, divineBulwark; // normal mech
	//end region

	//region Enemies
	public static void load(){
		
        divineSwarmer = new DivineUnitType("divine-swarmer"){{
			constructor = MechUnit::create;
			
            speed = 1.1f;
            hitSize = 9f;
            health = 200;
            mechSideSway = 0.28f;
            range = 35f;
            ammoType = new ItemAmmoType(Items.coal);

            weapons.add(new Weapon(){{
                shootOnDeath = true;
                targetUnderBlocks = false;
                reload = 24f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                x = shootY = 0f;
                mirror = false;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;
                    hitSound = Sounds.explosion;

                    rangeOverride = 25f;
                    hitEffect = Fx.pulverize;
                    speed = 0f;
                    splashDamageRadius = 50f;
					status = DustStatusEffects.rotting;
                    instantDisappear = true;
                    splashDamage = 70f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};
		
		// Dagger Clone
        divineCyst = new DivineUnitType("divine-cyst"){{
			constructor = MechUnit::create;
            speed = 0.5f;
            hitSize = 10f;
            health = 250;
			armor = 2f;
            weapons.add(new Weapon("minedusty-divine-mech-nail"){{
                reload = 15f;
                x = 4f;
                y = 1f;
                top = false;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(2.5f, 11){{
					frontColor = DustPalette.divineBulletRed;
					backColor =DustPalette.divineBulletRedBack;
					status = DustStatusEffects.rotting;
                    width = 7f;
                    height = 10f;
                    lifetime = 60f;
                }};
            }});
        }};

		// Mace Clone
		divineGlaive = new DivineUnitType("divine-glaive"){{
			constructor = MechUnit::create;
			speed = 0.5f;
			hitSize = 10f;
			health = 600f;
			armor = 5f;
			
			mechFrontSway = 0.2f;
			//immunities.add(StatusEffects.burning);

			weapons.add(new Weapon("minedusty-divine-mech-finger"){{
				layerOffset = -0.0001f;
				top = false;
				shootSound = Sounds.flame2;
				shootY = 1.2f;
				y = 1.5f;
				x = 5f;
				reload = 9.5f;
				recoil = 1.05f;
				ejectEffect = Fx.none;
				bullet = new BulletType(4.0f, 25f){{
                    ammoMultiplier = 3f;
                    hitSize = 7f;
                    lifetime = 13f;
                    pierce = true;
                    pierceBuilding = false;
                    pierceCap = 2;
                    statusDuration = 60f * 8;
                    shootEffect = DustyEffects.shootRotFlame;
                    hitEffect = Fx.hitFlameSmall;
                    despawnEffect = Fx.none;
                    status = DustStatusEffects.rotting;
                    keepVelocity = true;
                    hittable = false;
				}};
			}});
		}};

		// Fortress clone
		divineBulwark = new DivineUnitType("divine-bulwark"){{
			constructor = MechUnit::create;
			speed = 0.45f;
			hitSize = 10f;
			rotateSpeed = 3f;
			targetAir = false;
			health = 900;
			armor = 8;
			mechSideSway = 0.5f;
			mechFrontSway = 0.38f; //gotta test these values

			ammoType = new ItemAmmoType(DustItems.divinityMatter);
			weapons.add(new Weapon("minedusty-divine-mech-arm"){{
				layerOffset = -0.0001f;
				top = false;
				y = 3.5f;
				x = 8f;
				reload = 60f;
				recoil = 4f;
				shake = 1f;
				ejectEffect = Fx.casing2;
				shootSound = Sounds.artillery;
				//not touched below taken from Fortress
				bullet = new ArtilleryBulletType(2f, 20, "shell"){{
					pierce = true;
					hitEffect = Fx.blastExplosion;
					knockback = 0.8f;
					lifetime = 120f;
					width = height = 14f;
					collides = true;
                    collidesTiles = true;
                    splashDamageRadius = 35f;
                    splashDamage = 80f;
                    backColor = DustPalette.divineBulletRedBack;
                    frontColor = DustPalette.divineBulletRed;
				}};
			}});
		}};

        divineFlathead = new DivineUnitType("divine-flathead"){{
            constructor = LegsUnit::create;
            drag = 0.12f;
            speed = 0.45f;
            hitSize = 13f;
            health = 2000;
            armor = 5f;
			stepShake = 0.2f;

            legCount = 6;
			legLength = 18f;	
			legGroupSize = 3;
			lockLegBase = true;
			legContinuousMove = true;
			legExtension = -3f;
			legBaseOffset = 7f;
			legMinLength = 0.2f;
			legLengthScl = 0.95f;
			legForwardScl = 0.9f;

			legMoveSpace = 1f;
			hoverable = true;

			shadowElevation = 0.1f;
			groundLayer = Layer.legUnit - 1f;

            abilities.add(new ShieldArcAbility(){{
                region = "minedusty-divine-flathead-shield";
                radius = 30f;
                angle = 95f;
                regen = 0.4f;
                cooldown = 60f * 8f;
                max = 1100f;
                y = -20f;
                width = 4.5f;
                whenShooting = false;
            }});

            rotateSpeed = 2.1f;

            // weapons.add(new Weapon("tecta-weapon"){{
            //     shootSound = Sounds.malignShoot;
            //     mirror = true;
            //     top = false;

            //     x = 62/4f;
            //     y = 1f;
            //     shootY = 47 / 4f;
            //     recoil = 3f;
            //     reload = 40f;
            //     shake = 3f;
            //     cooldownTime = 40f;

            //     shoot.shots = 3;
            //     inaccuracy = 3f;
            //     velocityRnd = 0.33f;
            //     heatColor = Color.red;

            //     bullet = new MissileBulletType(4.2f, 60){{
            //         homingPower = 0.2f;
            //         weaveMag = 4;
            //         weaveScale = 4;
            //         lifetime = 55f;
            //         shootEffect = Fx.shootBig2;
            //         smokeEffect = Fx.shootSmokeTitan;
            //         splashDamage = 70f;
            //         splashDamageRadius = 30f;
            //         frontColor = Color.white;
            //         hitSound = Sounds.none;
            //         width = height = 10f;

            //         lightColor = trailColor = backColor = Pal.techBlue;
            //         lightRadius = 40f;
            //         lightOpacity = 0.7f;

            //         trailWidth = 2.8f;
            //         trailLength = 20;
            //         trailChance = -1f;
            //         despawnSound = Sounds.dullExplosion;

            //         despawnEffect = Fx.none;
            //         hitEffect = new ExplosionEffect(){{
            //             lifetime = 20f;
            //             waveStroke = 2f;
            //             waveColor = sparkColor = trailColor;
            //             waveRad = 12f;
            //             smokeSize = 0f;
            //             smokeSizeBase = 0f;
            //             sparks = 10;
            //             sparkRad = 35f;
            //             sparkLen = 4f;
            //             sparkStroke = 1.5f;
            //         }};
            //     }};
            // }});
        }};
		/*
        anthicus = new ErekirUnitType("anthicus"){{
            speed = 0.65f;
            drag = 0.1f;
            hitSize = 21f;
            rotateSpeed = 3f;
            health = 2900;
            armor = 7f;
            fogRadius = 40f;
            stepShake = 0f;

            legCount = 6;
            legLength = 18f;
            legGroupSize = 3;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -3f;
            legBaseOffset = 7f;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.95f;
            legForwardScl = 0.9f;

            legMoveSpace = 1f;
            hovering = true;

            shadowElevation = 0.2f;
            groundLayer = Layer.legUnit - 1f;

            for(int j = 0; j < 3; j++){
                int i = j;
                parts.add(new RegionPart("-blade"){{
                    layerOffset = -0.01f;
                    heatLayerOffset = 0.005f;
                    x = 2f;
                    moveX = 6f + i * 1.9f;
                    moveY = 8f + -4f * i;
                    moveRot = 40f - i * 25f;
                    mirror = true;
                    progress = PartProgress.warmup.delay(i * 0.2f);
                    heatProgress = p -> Mathf.absin(Time.time + i * 14f, 7f, 1f);

                    heatColor = Pal.techBlue;
                }});
            }

            weapons.add(new Weapon("anthicus-weapon"){{
                shootSound = Sounds.missileLarge;
                x = 29f / 4f;
                y = -11f / 4f;
                shootY = 1.5f;
                showStatSprite = false;
                reload = 130f;
                layerOffset = 0.01f;
                heatColor = Color.red;
                cooldownTime = 60f;
                smoothReloadSpeed = 0.15f;
                shootWarmupSpeed = 0.05f;
                minWarmup = 0.9f;
                rotationLimit = 70f;
                rotateSpeed = 2f;
                inaccuracy = 20f;
                shootStatus = StatusEffects.slow;
                alwaysShootWhenMoving = true;

                rotate = true;

                shoot = new ShootPattern(){{
                    shots = 2;
                    shotDelay = 6f;
                }};

                parts.add(new RegionPart("-blade"){{
                    mirror = true;
                    moveRot = -25f;
                    under = true;
                    moves.add(new PartMove(PartProgress.reload, 1f, 0f, 0f));

                    heatColor = Color.red;
                    cooldownTime = 60f;
                }});

                parts.add(new RegionPart("-blade"){{
                    mirror = true;
                    moveRot = -50f;
                    moveY = -2f;
                    moves.add(new PartMove(PartProgress.reload.shorten(0.5f), 1f, 0f, -15f));
                    under = true;

                    heatColor = Color.red;
                    cooldownTime = 60f;
                }});

                bullet = new BulletType(){{
                    shootEffect = new MultiEffect(Fx.shootBigColor, new Effect(9, e -> {
                        color(Color.white, e.color, e.fin());
                        stroke(0.7f + e.fout());
                        Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                        Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                    }), new WaveEffect(){{
                        colorFrom = colorTo = Pal.techBlue;
                        sizeTo = 15f;
                        lifetime = 12f;
                        strokeFrom = 3f;
                    }});

                    smokeEffect = Fx.shootBigSmoke2;
                    shake = 2f;
                    speed = 0f;
                    keepVelocity = false;
                    inaccuracy = 2f;

                    spawnUnit = new MissileUnitType("anthicus-missile"){{
                        trailColor = engineColor = Pal.techBlue;
                        engineSize = 1.75f;
                        engineLayer = Layer.effect;
                        speed = 3.7f;
                        maxRange = 6f;
                        lifetime = 60f * 1.5f;
                        outlineColor = Pal.darkOutline;
                        health = 55;
                        lowAltitude = true;

                        parts.add(new FlarePart(){{
                            progress = PartProgress.life.slope().curve(Interp.pow2In);
                            radius = 0f;
                            radiusTo = 35f;
                            stroke = 3f;
                            rotation = 45f;
                            y = -5f;
                            followRotation = true;
                        }});

                        weapons.add(new Weapon(){{
                            shootSound = Sounds.none;
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(140f, 25f){{
                                shootEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Pal.techBlue, 24f), new WaveEffect(){{
                                    colorFrom = colorTo = Pal.techBlue;
                                    sizeTo = 40f;
                                    lifetime = 12f;
                                    strokeFrom = 4f;
                                }});
                            }};
                        }});
                    }};
                }};
            }});
        }};*/
		//end region

		cricket = new UnitType("cricket"){{
			aiController = BuilderAI::new;
			isEnemy = false;
			
			constructor = UnitEntity::create;

            lowAltitude = true;
			flying = true;
			mineSpeed = 8f;
			mineTier = 1;
			buildSpeed = 0.6f;
			drag = 0.05f;
			speed = 2.8f;
			rotateSpeed = 15f;
			accel = 0.1f;
			fogRadius = 0f;
			itemCapacity = 45;
			health = 220f;
			hitSize = 10f;
			alwaysUnlocked = true;
			engineOffset = 6.9f;
			outlines = false;
			
			weapons.add(new Weapon("minedusty-cricket-weapon"){{
				reload = 25f;
				shootSound = Sounds.missile;
				top = false;
				ejectEffect = Fx.none;
				x = 3.7f;
				y = 2f;
				layerOffset = -0.01f;
				//speed dmg
				bullet = new MissileBulletType(3f, 8){{
                    homingPower = 0.09f;
                    homingDelay = 15f;
                    weaveMag = 4f;
                    weaveScale = 3f;
                    width = 7f;
                    height = 9f;
					lifetime = 45f;
                    keepVelocity = false;

                    shootEffect = Fx.shootHeal;
                    smokeEffect = Fx.hitLaser;
                    hitEffect = despawnEffect = Fx.hitLaser;
                    healPercent = 7.5f;
					collidesTeam = true;
					backColor = Pal.heal;
					frontColor = Color.white;
                    trailColor = Pal.heal;
				}};
			}});
		}};
		mantis = new UnitType("mantis"){{
			aiController = BuilderAI::new;
			isEnemy = false;

			constructor = UnitEntity::create;

			flying = true;
			mineSpeed = 8f;
			mineTier = 2;
			buildSpeed = 0.8f;
			drag = 0.05f;
			speed = 3.2f;
			rotateSpeed = 19f;
			accel = 0.11f;
			fogRadius = 0f;
			itemCapacity = 80;
			health = 325f;
			hitSize = 17f;
			engineOffset = 8.5f;
			engineSize = 3f;
			trailLength = 6;
			outlines = false;

			weapons.add(new Weapon("minedusty-mantis-weapon"){{
				reload = 140f;
				shootSound = Sounds.lasershoot;
				top = false;
				ejectEffect = Fx.none;
				//alternate = false;
				mirror = false;
				cooldownTime = 130f;
				x = y = 0f;
				recoil = 1.2f;
				//speed dmg
				bullet = new LaserBoltBulletType(3f, 4){{
					lifetime = 50f;
					healPercent = 3.5f;
					collidesTeam = true;
					backColor = Pal.heal;
					frontColor = Color.white;
				}};
			}});
		}};
	}
}
