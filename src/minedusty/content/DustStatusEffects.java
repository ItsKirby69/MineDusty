package minedusty.content;

import static mindustry.Vars.state;

import arc.Events;
import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.*;
import mindustry.game.EventType.Trigger;
import mindustry.type.StatusEffect;
import minedusty.graphics.DustPalette;
import minedusty.utils.EffectHelper;

public class DustStatusEffects {
    public static StatusEffect rotting, poison, healingWash;
    public static StatusEffect saltcorrosion, chlorinecorrosion;

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
            effect = DustyEffects.corrosion;
            transitionDamage = 10f;

            init(() -> {
                affinity(StatusEffects.shocked, (unit, result, time) -> {
                    unit.damage(transitionDamage);

                    if(unit.team == state.rules.waveTeam){
                        Events.fire(Trigger.shock);
                    }
                });
                affinity(StatusEffects.wet, (unit, result, time) -> {
                    result.time = Math.max(0f, result.time - time * 2f);
                });
                affinity(drenched, (unit, result, time) -> {
                    result.time = Math.max(0f, result.time - time * 4f);
                });
            });
        }};

        chlorinecorrosion = new StatusEffect("chlorine-corrosion"){{
            color = Color.valueOf("#ebf9af");
            intervalDamage = 25f;
            intervalDamageTime = 80f;
            damageMultiplier = 0.85f;
            reloadMultiplier = 0.85f;

            effectChance = 0.08f;
            effect = DustyEffects.corrosion;
            transitionDamage = 0f;

            init(() -> {
                affinity(StatusEffects.wet, (unit, result, time) -> {
                    result.time = Math.max(0f, result.time - time * 2f);
                });
                affinity(drenched, (unit, result, time) -> {
                    result.time = Math.max(0f, result.time - time * 4f);
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
            color = Color.valueOf("#C32121");
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

        // WIP
        poison = new StatusEffect("poison"){{
            color = Color.valueOf("#679836");
            damage = 0.15f;
            effect = DustyEffects.poison;
            transitionDamage = 10f;

            init(() -> {
                opposite(DustStatusEffects.rotting);
            });
        }};
    }
}
