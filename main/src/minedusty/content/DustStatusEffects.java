package minedusty.content;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.*;
import mindustry.type.StatusEffect;

public class DustStatusEffects {
    public static StatusEffect rotting;

    public static void load(){
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
