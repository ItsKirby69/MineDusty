package minedusty.content;

import arc.graphics.Color;
import ent.anno.Annotations.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootHelix;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.BlockFlag;
import minedusty.gen.WaterMoveUnit;
import minedusty.graphics.DustPalette;
import minedusty.type.unit.DivineUnitType;
import minedusty.type.unit.DustUnitType;
import minedusty.type.unit.TemporUnitType;

public class DustUnitTypes extends UnitTypes{

	/** Sharded Faction */
	public static UnitType cricket, locust, mantis;

	// Airborne
	public static @EntityDef({Unitc.class}) UnitType dazzle;
	// Earthborne
	public static @EntityDef({Unitc.class, Mechc.class}) UnitType bulbus;

	//region Enemy units

	/** Crux Faction */
	public static @EntityDef({Unitc.class, Mechc.class}) UnitType cleave;
	public static @EntityDef({Unitc.class, WaterMovec.class}) UnitType minnow, sturgeon;

	/** Divine Faction */
	public static @EntityDef({Unitc.class, Legsc.class}) UnitType divineFlathead, devineNanitic; // WIP
	public static @EntityDef({Unitc.class, Mechc.class}) UnitType divineSwarmer, divineCyst, divineGlaive, divineBulwark;

	/** Tempor Faction */
	public static @EntityDef({Unitc.class, Mechc.class}) UnitType actaea;

	//end region

	public static void load(){
		//region Sharded
		// Core unit | Insects
		cricket = new DustUnitType("cricket"){{
			// Fiddle around with this in the future?
			controller = u -> u.team.isAI() ? new BuilderAI(true, 400f) : new CommandAI();
			//aiController = BuilderAI::new;
			isEnemy = false;
			
			constructor = UnitEntity::create;

			targetBuildingsMobile = false;
            lowAltitude = true;
			flying = true;
			mineSpeed = 7f;
			mineTier = 1;
			buildSpeed = 0.6f;
			drag = 0.05f;
			speed = 2.8f;
			rotateSpeed = 15f;
			accel = 0.1f;
			fogRadius = 0f;
			itemCapacity = 45;
			health = 220f;
			hitSize = 9f;
			alwaysUnlocked = true;
			engineOffset = 6f;
			// outlines = false;
			outlineColor = DustPalette.turretOutline;
			
			weapons.add(new Weapon("minedusty-cricket-weapon"){{
				reload = 17.5f;
				top = false;
				ejectEffect = Fx.casing1;
				x = 3.25f;
				y = 2.5f;
				// layerOffset = -0.01f;
				//speed dmg
				bullet = new BasicBulletType(2.5f, 11){{
					width = 7f;
					height = 10f;
					lifetime = 75f;

					trailLength = 4;
					trailWidth = 1.4f;

					hitColor = backColor = DustPalette.oxidecopperMid;
					trailColor = DustPalette.oxidecopperBack;
					frontColor = DustPalette.oxidecopper;
					shootEffect = Fx.shootSmall;
					shootEffect = Fx.shootSmallSmoke;
					buildingDamageMultiplier = 0.5f;
					homingPower = 0.02f;
				}};
			}});
		}};
		
		mantis = new DustUnitType("mantis"){{
			drawCell = false;
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
				shootSound = Sounds.shootLaser;
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

		// Airborne | Effects
        dazzle = new DustUnitType("dazzle"){{
			constructor = UnitEntity::create;
            researchCostMultiplier = 0.5f;
            speed = 2.5f;
            accel = 0.08f;
            drag = 0.04f;
            flying = true;
            health = 170;
            engineOffset = 5.75f;
            targetFlags = new BlockFlag[]{BlockFlag.generator, null};
            hitSize = 10;
            itemCapacity = 8;
            circleTarget = true;
            omniMovement = false;
            rotateSpeed = 5f;
            circleTargetRadius = 45f;
            wreckSoundVolume = 0.7f;
			crashDamageMultiplier = 1.5f;

            moveSound = Sounds.loopThruster;
            moveSoundPitchMin = 0.3f;
            moveSoundPitchMax = 1.5f;
            moveSoundVolume = 0.2f;

			outlineColor = DustPalette.turretOutline;

            weapons.add(new Weapon(){{
                y = 1f;
                x = 0f;
                minShootVelocity = 1.1f;
                shootCone = 10f;
                reload = 55f;
				shoot = new ShootHelix();
                ejectEffect = Fx.casing1;
                mirror = false;
				shootSound = Sounds.shoot;
                bullet = new BasicBulletType(2.5f, 24){{
                    inaccuracy = 4f;
                    width = 7f;
                    height = 9f;
                    lifetime = 32f;
					hitColor = backColor = DustPalette.oxidecopperMid;
					trailColor = DustPalette.oxidecopperBack;
					frontColor = DustPalette.oxidecopper;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    ammoMultiplier = 2;
					trailLength = 2;
					trailWidth = 1;
                }};
            }});
        }};

		// Earthborne | Plant-spinoffs
        bulbus = new DustUnitType("bulbus"){{
			constructor = MechUnit::create;
            researchCostMultiplier = 0.5f;
            speed = 0.46f;
            hitSize = 9f;
            health = 175f;
            stepSoundVolume = 0.4f;
			
			loopSound = Sounds.none;

            weapons.add(new Weapon("minedusty-bulbus-weapon"){{
                reload = 140f;
                top = false;
				mirror = false;
                ejectEffect = Fx.casing1;
				shootY = 5f;
				x = y = 0;
				recoil = 0;
				shootSound = Sounds.shootSap;
				chargeSound = Sounds.chargeLancer;
				
				shootStatus = StatusEffects.slow;
				shootStatusDuration = reload + 10f;

				cooldownTime = 100f;
				ejectEffect = Fx.none;
				MultiEffect multEff = new MultiEffect(DustyEffects.orbCharge, DustyEffects.orbChargeBegin(45f));
				shoot.firstShotDelay = multEff.lifetime;
				parentizeEffects = true;

                bullet = new BasicBulletType(2f, 35, "minedusty-energy-orb"){{
					lifetime = 60f;
                    width = height = 13.5f;
					shrinkX = shrinkY = 0.1f;
					pierceCap = 3;
					pierceBuilding = false;
					homingPower = 0.015f;
					homingRange = 80f;
					splashDamage = 15;
					splashDamageRadius = 12f;
                    chargeEffect = multEff;
                    hitEffect = DustyEffects.hitChloroSpark;

                    backColor = DustPalette.chlorophyteBack;
                    lightColor = DustPalette.chlorophyte;
                    frontColor = hitColor= DustPalette.chlorophyteWater;

					healPercent = 8;
					collidesTeam = true;
                }};
            }

				@Override
				public void update(Unit unit, WeaponMount mount){
					super.update(unit, mount);
					if(mount.charge > mount.heat){
						mount.heat = mount.charge / 2f;
					}
				}
			});
        }};

		//endregion
		//region Crux

		// Ground unit | Knives
        cleave = new UnitType("cleave"){{
			constructor = MechUnit::create;
            researchCostMultiplier = 0.5f;
            speed = 0.48f;
            hitSize = 9f;
            health = 425;
            stepSoundVolume = 0.5f;
			outlineColor = DustPalette.turretOutline;

            weapons.add(new Weapon("minedusty-larger-weapon"){{
                reload = 60f;
                x = 4f;
                y = 2f;
                top = false;
                ejectEffect = Fx.casing1;
				shoot.shots = 3;
				shoot.shotDelay = 4f;
				outlineColor = DustPalette.turretOutline;
                bullet = new BasicBulletType(3.25f, 10){{
                    width = 7f;
                    height = 9f;
                    lifetime = 70f;
					homingPower = 0.015f;
                }};
            }});
        }};
		
		// Water units | Fishes
        minnow = new UnitType("minnow"){{
			constructor = WaterMoveUnit::create;
            speed = 1.35f;
            drag = 0.13f;
            hitSize = 10f;
            health = 160;
            armor = 1f;
            accel = 0.45f;
            rotateSpeed = 3.75f;
            faceTarget = false;

            trailLength = 20;
            waveTrailX = 3f;
            trailScl = 1.3f;

            moveSoundVolume = 0.3f;
            moveSound = Sounds.shipMove;

            weapons.add(new Weapon("minedusty-water-small-weapon"){{
                reload = 7f;
                shootY = 4f;
				x = 4f;
                y = -1f;
                rotate = true;
				recoil = 0.75f;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(4f, 2f){{
                    width = 6f;
                    height = 7f;
                    lifetime = 50f;
                    ammoMultiplier = 2;
					drag = -0.007f;
                    weaveScale = 8f;
                    weaveMag = 1.25f;
                }};
            }});
        }};

        sturgeon = new UnitType("sturgeon"){{
			constructor = WaterMoveUnit::create;
            speed = 1.05f;
            drag = 0.15f;
            hitSize = 13f;
            health = 470;
            armor = 3f;
            accel = 0.45f;
            rotateSpeed = 2.85f;
            faceTarget = false;

            trailLength = 20;
            waveTrailX = 6.2f;
            trailScl = 1.8f;

            moveSoundVolume = 0.55f;
			moveSoundPitchMin = moveSoundPitchMax = 0.9f;
            moveSound = Sounds.shipMove;

            weapons.add(new Weapon("minedusty-water-mount"){{
                reload = 12f;
                shootY = 4f;
				x = 4f;
                y = 3f;
                rotate = true;
				rotateSpeed = 4f;
				inaccuracy = 8f;
				recoil = 0.75f;
                ejectEffect = Fx.casing1;
				shootSound = Sounds.shootDuo;
                bullet = new FlakBulletType(4f, 4f){{
                    width = 6f;
                    height = 8f;
                    lifetime = 52f;
                    ammoMultiplier = 4f;
					hitEffect = Fx.flakExplosion;
					shootEffect = Fx.shootSmall;
					splashDamage = 25f * 1.5f;
					splashDamageRadius = 12f;
                }};
            }});

			weapons.add(new Weapon("minedusty-water-missile"){{
				shootSound = Sounds.shootRetusa;
				reload = 45f;
				x = 0f;
				y = -8f;
				top = true;
				mirror = false;
				rotate = true;
				inaccuracy = 1.5f;
				rotateSpeed = 2f;
				shake = 1.2f;
				ejectEffect = Fx.casing2;
				shootSound = Sounds.shootRetusa;
				bullet = new ArtilleryBulletType(2.5f, 25, "mine-bullet"){{
					hitEffect = Fx.flakExplosion;
					backColor = DustPalette.aquameriumBack;
					frontColor = DustPalette.aquamerium;
					knockback = 0.8f;
					lifetime = 65f;
					width = height = 9f;
					collidesTiles = false;
					splashDamageRadius = 22f;
					splashDamage = 35f;
				}};
			}});
        }};
		
		//endregion
		//region Tempor

        actaea = new TemporUnitType("actaea"){{
			constructor = MechUnit::create;
			// drawCell = false;
            speed = 0.5f;
            hitSize = 10f;
            health = 250;
			armor = 2f;
            weapons.add(new Weapon("minedusty-tempor-weapon"){{
                reload = 15f;
                x = 4.4f;
                y = 0f;
                top = false;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(2.5f, 11){{
					frontColor = DustPalette.chlorophyteBullet;
					backColor = DustPalette.chlorophyte;
					shootEffect = DustyEffects.temporshootSmall;
					
					// status = DustStatusEffects.rotting;
                    width = 7f;
                    height = 10f;
                    lifetime = 60f;
                }};
            }});
        }};

		//endregion
		//region Divine
		
		// WIP
		devineNanitic = new DivineUnitType("divine-nanitic"){{
            constructor = LegsUnit::create;
			drawCell = false;
            drag = 0.08f;
            speed = 0.65f;
            hitSize = 13f;
            health = 800;
            armor = 2f;
			stepShake = 0.2f;

            legCount = 6;
            legLength = 15f;
			legSpeed = 0.2f;
            legGroupSize = 3;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -2f;
            legBaseOffset = 2.5f;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.95f;
            legForwardScl = 0.6f;
			legStraightness = 0.3f;

			legMoveSpace = 1f;
			hoverable = true;
			mechSideSway = 0.2f;
			
			rotateSpeed = 7f;
			shadowElevation = 0.07f;
			groundLayer = Layer.legUnit - 1f;
			
			// Make head 
			parts.add(
				new RegionPart("-antenna"){{
					mirror = true;
					under = true;
					outline = true;
					x = 3f; y = 11.5f;
				}},
				new RegionPart("-antenna"){{
					mirror = true;
					under = false;
					outline = false;
					x = 3f; y = 11.5f;
					layerOffset = 0.001f;
				}});

            weapons.add(new Weapon("minedusty-divine-stinger"){{
                mirror = false;
                rotate = true;
				rotationLimit = 70f;
				rotateSpeed = 8f;
				// alwaysShootWhenMoving = true;

                reload = 90f;
				recoil = 5f;
				shake = 3f;
                x = 0f;
				y = -7f;
				shootX = 0f;
				shootY = -3f;
                shootSound = Sounds.shootRetusa;
                

                // shoot.shots = 1;
                shoot.shotDelay = 7f;

                bullet = new BasicBulletType(){{
                    sprite = "mine-bullet";
                    width = height = 8f;
                    // layer = Layer.scorch;
                    shootEffect = smokeEffect = Fx.none;

                    maxRange = 45f;
                    ignoreRotation = true;

                    backColor = DustPalette.divineBulletRedBack;
                    frontColor = Color.white;
                    mixColorTo = Color.white;

                    hitSound = Sounds.explosionQuad;
                    // underwater = true;

                    ejectEffect = Fx.none;
                    hitSize = 22f;

                    collidesAir = true;

                    lifetime = 87f;

                    hitEffect = new MultiEffect(Fx.blastExplosion, DustyEffects.redCloud);
                    keepVelocity = false;

                    shrinkX = shrinkY = 0.2f;

                    inaccuracy = 1.25f;
                    weaveMag = 5f;
                    weaveScale = 4f;
                    speed = 0.7f;
                    drag = -0.017f;
                    homingPower = 0.05f;
                    // collideFloor = true;
                    trailColor = DustPalette.divineBulletRed;
                    trailWidth = 3f;
                    trailLength = 8;

                    splashDamage = 40f;
                    splashDamageRadius = 32f;
                }};
            }});
		}};

        divineSwarmer = new DivineUnitType("divine-swarmer"){{
			constructor = MechUnit::create;
			drawCell = false;
            speed = 1.1f;
            hitSize = 9f;
            health = 200;
            mechSideSway = 0.28f;
            range = 35f;

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
		
        divineCyst = new DivineUnitType("divine-cyst"){{
			constructor = MechUnit::create;
			drawCell = false;
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

		divineGlaive = new DivineUnitType("divine-glaive"){{
			constructor = MechUnit::create;
			drawCell = false;
			speed = 0.5f;
			hitSize = 10f;
			health = 600f;
			armor = 5f;
			
			mechFrontSway = 0.2f;
			//immunities.add(StatusEffects.burning);

			weapons.add(new Weapon("minedusty-divine-mech-finger"){{
				layerOffset = -0.0001f;
				top = false;
				shootSound = Sounds.shootFlame;
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

		divineBulwark = new DivineUnitType("divine-bulwark"){{
			constructor = MechUnit::create;
			drawCell = false;
			speed = 0.45f;
			hitSize = 10f;
			rotateSpeed = 3f;
			targetAir = false;
			health = 900;
			armor = 8;
			mechSideSway = 0.5f;
			mechFrontSway = 0.38f; //gotta test these values

			weapons.add(new Weapon("minedusty-divine-mech-arm"){{
				layerOffset = -0.0001f;
				top = false;
				y = 3.5f;
				x = 8f;
				reload = 60f;
				recoil = 4f;
				shake = 1f;
				ejectEffect = Fx.casing2;
				shootSound = Sounds.shootArtillery;
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
			drawCell = false;
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
        }};
		//end region
	}
}
