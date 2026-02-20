package minedusty.world.blocks.defense;

import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import arc.util.*;
import minedusty.content.DustyEffects;

/** Wall that heals in pulses after a delay when damaged. */
public class HealWall extends DustWall{
    
    public float healPercent = 0.05f;
    public float healDelay = 6f * 60f;
    public float healInterval = 1.5f * 60f;

    public HealWall(String name){
        super(name);
        update = true;
    }

    public class HealWallBuild extends DustWallBuild{
        private float healtimer;
        public float damageTimer;

        @Override
        public void updateTile() {
            super.updateTile();

            if (health < maxHealth){
                damageTimer += Time.delta;

                // After a delay
                if (damageTimer >= healDelay) {
                    healtimer += Time.delta;
                    
                    // Pulses of healing
                    if (healtimer >= healInterval) {
                        heal(maxHealth * healPercent);
                        healtimer = 0f;

                        DustyEffects.healWallhealing.at(x, y, block.size, Color.valueOf("#84f491"), block);
                    }
                }
            } else {
                damageTimer = 0f;
                healtimer = 0f;
            }
        }

        @Override
        public void damage(float damage) {
            super.damage(damage);
            damageTimer = 0f;
            healtimer = 0f;
        }
        
        // TEMP
        @Override
        public void display(Table table) {
            super.display(table);

            if(health < maxHealth){
                table.row();
                table.table(t -> {
                    t.label(() -> {
                        if(damageTimer < healDelay){
                            return "[lightgray]Healing in: " + String.format("%.1f", (healDelay - damageTimer) / 60f) + "s";
                        }else{
                            return "[green]Healing: +" + healPercent + "/s";
                        }
                    }).left().growX();
                }).growX();
            }
        }
    }
}
