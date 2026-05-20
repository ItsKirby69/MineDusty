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

    public SEffect(Sound sound, Effect effect, float pitchmin, float pitchmax, float volmin, float volmax){
        super();
        this.sound = sound;
        this.effect = effect;
        this.maxPitch = pitchmax;
        this.minPitch = pitchmin;
        this.maxVolume = volmin * settings.getInt("dusty-sfx-volume") / 100f;
        this.minVolume = volmax * settings.getInt("dusty-sfx-volume") / 100f;
    }
}
