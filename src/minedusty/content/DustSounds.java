package minedusty.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.*;
import mindustry.Vars;

public class DustSounds {

	public static Sound
		chromaPew1 = new Sound(),
		chromaPew2 = new Sound(),
		chromartillery = new Sound(),
		chromalastershoot = new Sound(),
		bubblePop = new Sound(),
		destroyTree = new Sound();

		public static void load(){
			chromaPew1 = loadSound("chromaPew1");
			chromaPew2 = loadSound("chromaPew2");
			chromartillery = loadSound("chromartillery");
			chromalastershoot = loadSound("chromalastershoot");
			
			bubblePop = loadSound("bubblePop");
			destroyTree = loadSound("destroyTree");
		}

		public static Sound loadSound(String soundName){
			//taken from Omaloon, please support this mod -> https://github.com/xstabux/Omaloon/blob/master/src/omaloon/content/OlSounds.java
			
			if(!Vars.headless) {
				String name = "sounds/" + soundName;
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
