package minedusty.content;

import arc.graphics.Color;
import mindustry.ai.types.*;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;

public class DustUnitTypes {
	
	// core units
	public static UnitType cricket, lotus, mantis;

	//region Enemy units

	//mechs
	public static UnitType divineMech1; //@EntityDef({Unitc.class, Mechc.class}) UnitType divineMech1;
	//end region

	//very wip. TODO fix outlines, need entityDefs to work. Need to port mod to Glenn's template.
	public static void load(){
		divineMech1 = new UnitType("divine-mech1"){{
			constructor = UnitEntity::create;
			speed = 0.5f;
			hitSize = 10f;
			rotateSpeed = 3f;
			targetAir = false;
			health = 500;
			armor = 7;
			mechFrontSway = 0.55f; //gotta test these values
			outlineColor = Color.valueOf("390000");

			ammoType = new ItemAmmoType(DustItems.divinityMatter);
			weapons.add(new Weapon("divine-mech-arm"){{
				top = false;
				y = 1f;
				x = 9f;
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
                    backColor = Pal.bulletYellowBack;
                    frontColor = Pal.bulletYellow;
				}};
			}});
		}};

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
