package minedusty.entities.effect;

import static arc.Core.settings;

import arc.audio.Sound;
import mindustry.entities.Effect;
import mindustry.entities.effect.SoundEffect;

public class SEffect extends SoundEffect{

    public SEffect(Sound sound, Effect effect, float vol){
        super();
        this.sound = sound;
        this.effect = effect;
        this.maxVolume = vol * settings.getInt("dusty-sfx-volume") / 100f;
        this.minVolume = vol * settings.getInt("dusty-sfx-volume") / 100f;
    }
}
