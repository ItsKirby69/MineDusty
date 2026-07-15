package minedusty.world.blocks.defense;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.*;
import mindustry.world.meta.StatUnit;
import minedusty.content.DustyEffects;
import minedusty.world.meta.DustStat;

/** Wall that heals in pulses after a delay when damaged. */
public class HealWall extends DustWall{
    
    public float healMinPercent = 0.03f;
    public float healMaxPercent = 0.05f;
    public float healDelay = 10f * 60f;
    public float healInterval = 1.5f * 60f;

    public HealWall(String name){
        super(name);
        update = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(DustStat.wallHealRange, (int)(healMaxPercent * 100f) + "-" + (int)(healMinPercent * 100f) + "%", StatUnit.none);
        stats.add(DustStat.wallHealDelay, (int)(healDelay / 60f), StatUnit.seconds);
        stats.add(DustStat.wallHealPulse, (float)(healInterval / 60f), StatUnit.seconds);
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
                        float healthFrac = health / maxHealth;
                        heal(maxHealth * Mathf.lerp(healMaxPercent, healMinPercent, healthFrac));
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
                            return "[green]Healing: +" + (int)(maxHealth * Mathf.lerp(healMinPercent, healMaxPercent, health / maxHealth)) + " hp";
                        }
                    }).left().growX();
                }).growX();
            }
        }
    }
}
