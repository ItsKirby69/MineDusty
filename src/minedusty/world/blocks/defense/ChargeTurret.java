package minedusty.world.blocks.defense;

import mindustry.world.blocks.defense.turrets.LiquidTurret;

public class ChargeTurret extends LiquidTurret{

    public ChargeTurret(String name) {
        super(name);
    }

    public class ChargeTurretBuild extends LiquidTurretBuild{
        @Override
        public void updateTile(){
            super.updateTile();
            if(charging()){
                heat = Math.max(heat, reloadCounter / reload) / 2f;
            }
        }

        @Override
        public void update(){
            float prevRotation = rotation;
            super.update();
            if(charging()){
                rotation = prevRotation;
            }
        }
    }
    
}
