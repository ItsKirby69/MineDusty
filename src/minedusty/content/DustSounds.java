package minedusty.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.*;
import mindustry.Vars;

public class DustSounds {

	public static Sound
		bubblePop = new Sound();

		//something went wrong with sounds playing... not sure if its because incorrect loading or playing
		public static void load(){
			bubblePop = loadSound("bubblePop");
		}

		public static Sound loadSound(String soundName){
			//taken from Omaloon, please support this mod -> https://github.com/xstabux/Omaloon/blob/master/src/omaloon/content/OlSounds.java
			//making sure it doesn't load serverside (i think)
			if(!Vars.headless) {
				String name = "assets/sounds/" + soundName;
				String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

				Sound sound = new Sound();

            	AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
            	desc.errored = Throwable::printStackTrace;

				return sound;

			} else {
				return new Sound();
			}
		}
}
