package minedusty.content;

import static mindustry.Vars.state;

import arc.Events;
import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.*;
import mindustry.game.EventType.Trigger;
import mindustry.type.StatusEffect;
import minedusty.graphics.DustPalette;

public class DustStatusEffects {
    public static StatusEffect rotting, healingWash, saltcorrosion;

    public static StatusEffect drenched;

    public static void load(){
        // TODO icons
        drenched = new StatusEffect("drenched"){{
            color = Color.valueOf("#3b51b1");
            speedMultiplier = 0.9f;
            effect = Fx.wet;
            effectChance = 0.09f;
            transitionDamage = 22;

            init(() -> {
                affinity(StatusEffects.shocked, (unit, result, time) -> {
                    unit.damage(transitionDamage);

                    if(unit.team == state.rules.waveTeam){
                        Events.fire(Trigger.shock);
                    }
                });
                opposite(StatusEffects.burning, StatusEffects.melting);
            });
        }};

        saltcorrosion = new StatusEffect("salt-corrosion"){{
            color = Color.valueOf("#c8d7e2");
            intervalDamage = 10f;
            intervalDamageTime = 50f;
            damageMultiplier = 0.95f;

            effectChance = 0.08f;
            effect = DustyEffects.corrosionSalt;
            transitionDamage = 10f;

            init(() -> {
                affinity(StatusEffects.shocked, (unit, result, time) -> {
                    unit.damage(transitionDamage);

                    if(unit.team == state.rules.waveTeam){
                        Events.fire(Trigger.shock);
                    }
                });
            });
        }};

        healingWash = new StatusEffect("healing-wash"){{;
            color = DustPalette.chlorophyteWater;
            effect = DustyEffects.healingwet;
            effectChance = 0.09f;
            speedMultiplier = 1f;
            healthMultiplier = 1f;
        }};

        rotting = new StatusEffect("rotting"){{
            color = Color.valueOf("C32121");
            damage = 0.125f;
            effect = DustyEffects.rotting;
            transitionDamage = 5f;

            init(() -> {
                opposite(StatusEffects.burning, StatusEffects.melting);
                affinity(StatusEffects.wet, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    DustyEffects.rotting.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(rotting, Math.min(time + result.time, 300f));
                });
            });
        }};
    }
}
