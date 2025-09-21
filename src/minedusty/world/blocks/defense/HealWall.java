package minedusty.world.blocks.defense;

import arc.scene.ui.layout.Table;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.Wall;

/** Wall that heals in pulses after a delay when damaged. */
public class HealWall extends Wall{
    
    public float healPercent = 0.05f;
    public float healDelay = 6f * 60f;
    public float healInterval = 1.5f * 60f;

    public HealWall(String name){
        super(name);
        update = true;
    }

    public class HealWallBuild extends WallBuild{
        private float healtimer;
        public float damageTimer;
        public boolean wasDamaged = false;

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

                        Fx.healBlockFull.at(x, y, block.size, Tmp.c1.set(Pal.heal));
                    }
                }
            } else {
                damageTimer = 0f;
                healtimer = 0f;
                wasDamaged = false;
            }
        }

        @Override
        public void damage(float damage) {
            super.damage(damage);
            damageTimer = 0f;
            healtimer = 0f;
            wasDamaged = true;
        }
        
        // TEMP
        @Override
        public void display(Table table) {
            super.display(table);
            if (health < maxHealth) {
                table.row();
                // Show time until healing starts or healing status
                if (damageTimer < healDelay) {
                    table.add("[lightgray]Healing in: " + String.format("%.1f", (healDelay - damageTimer) / 60f) + "s");
                } else {
                    table.add("[green]Healing: +" + healPercent + "/s");
                }
            }
        }
    }
}
