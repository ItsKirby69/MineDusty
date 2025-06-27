package minedusty.content;

import arc.graphics.Color;
import ent.anno.Annotations.*;
import mindustry.ai.types.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
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

		//end region







		cricket = new UnitType("cricket"){{
			aiController = BuilderAI::new;
			isEnemy = false;
			
			constructor = UnitEntity::create;

			flying = true;
			mineSpeed = 8f;
			mineTier = 1;
			buildSpeed = 0.5f;
			drag = 0.05f;
			speed = 1.8f;
			rotateSpeed = 15f;
			accel = 0.1f;
			fogRadius = 0f;
			itemCapacity = 40;
			health = 220f;
			hitSize = 12f;
			alwaysUnlocked = true;
			engineOffset = 6.9f;
			trailLength = 4;
			outlines = false;
			//outlineColor = Color.valueOf("41ffb8"); //2f3734
			
			weapons.add(new Weapon("minedusty-cricket-weapon"){{
				reload = 30f;
				shootSound = Sounds.lasershoot;
				top = false;
				ejectEffect = Fx.none;
				//alternate = false;
				x = 3.7f;
				y = 2f;
				layerOffset = -4f;
				recoil = 1.2f;
				//speed dmg
				bullet = new LaserBoltBulletType(3f, 10){{
					lifetime = 50f;
					healPercent = 3.5f;
					collidesTeam = true;
					backColor = Pal.heal;
					frontColor = Color.white;
				}};
			shoot = new ShootSpread(1, 0.5f);
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
