package minedusty;

import minedusty.blocks.*;
import minedusty.content.*;
import minedusty.gen.*;
import minedusty.graphics.*;
import minedusty.planets.*;
import minedusty.ui.*;
import minedusty.utils.*;
import minedusty.world.ui.DustSplashFrag;
import arc.*;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.environment.Floor;

import static arc.Core.bundle;
import static mindustry.Vars.*;

public class MineDustyMod extends Mod {
	public static Seq<String> subtitles;

	public MineDustyMod() {
		Log.info("[lightgray]*cough* *cough* [][acid]MineDusty[] [white]is [][lightgray]*cough*[] [white]loaded");
		/*
		 * Events.on(EventType.ClientLoadEvent.class, e -> {
		 * Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", new
		 * DustMenuRender());
		 * });
		 */

		Events.on(StateChangeEvent.class, e -> {
			if(e.to == GameState.State.menu){
				Core.atlas.addRegion("logo", randomizeLogo());
			}
		});
	}

	@Override
	public void init() {
		// Events.run(EventType.Trigger.draw, Renderer::draw);
		addModdedSubtitles();
		Log.info(subtitles);
		DustSettings.load();
		Events.on(ClientLoadEvent.class, e -> {
			// show dialog upon startup
			boolean hasSectorSave = Vars.control.saves.getSaveSlots().contains(s -> 
				s.isSector() && s.getSector().planet == DustPlanets.theia);
			if(hasSectorSave){
				Log.info("BALLS!!");
			}
			DustyPopup.check();
			IconLoader.loadIcons();

			// Make vanilla ice render below snow (for edge tiles)
			((Floor)Blocks.ice).blendId = ((Floor)Blocks.snow).blendId - 1;
			((Floor)Blocks.grass).blendId = ((Floor)DustEnv.wildGrass).blendId - 1;

			Core.atlas.addRegion("logo", randomizeLogo());
			new DustSplashFrag().build(ui.menuGroup);
		});
	}

	@Override
	public void loadContent() {
		/** Gets random line from a text file. */
		subtitles = new Seq<>(bundle.get("minedusty-subtitle.lines").split("/"));
		Vars.mods.list().each(mod -> {
			if(mod.main == this){
				mod.meta.subtitle = "[acid]" + subtitles.random();
			}
		});
		
		EntityRegistry.register();
		if (!headless) {
			DustShaders.load();
			DustCacheLayers.load();
		}
		// teams, items, fluids, effects. Thanks @sl0tterleet
		DustTeams.load();
		DustSounds.load();
		DustItems.load();
		DustLiquids.load();
		DustAttributes.load();
		DustStatusEffects.load();
		DustWeathers.load();
		DustUnitTypes.load();

		// blocks
		DustBlocks.load();

		DustEnvRenderers.init();
		DustPlanets.load();
		DustSectors.load();
		TheiaTechTree.load();
		// WIP
		GenBundles.generate();

		FrostCandyWrappers.loadFrostedBlocks();
		Core.atlas.addRegion("logo", randomizeLogo());
	}

	TextureRegion randomizeLogo(){
		String[] logos = {"logo1", "logo2", "logo3", "logo4"};
		TextureRegion region = Core.atlas.find("minedusty-" + logos[Mathf.random(logos.length - 1)]);
		return region;
	}

	/** Custom subtitles based on mods loaded */
	public void addModdedSubtitles(){
		if(modLoaded("aquarion")){
			subtitles.addAll(
				"You are Aquarioning it",
				"Beware of the Gerbs",
				"Twcash = Pitchblend",
				"Bigger and better machines!",
				"Tantros is real",
				"Now with Serpulo 2!"
			);
		}
		if(modLoaded("omaloon")){
			subtitles.addAll(
				"Get Omalooned",
				"Omalooning",
				"Gerb",
				"Beware of the [scarlet]Crux[]!"
			);
		}
		if(modLoaded("sw")){
			subtitles.addAll(
				"Steam works? Yea i sure hope it does",
				"What's with the create mod?",
				"Spinny!! :3"
			);
		}
		if(modLoaded("yellow-java")){
			subtitles.addAll(
				"Yellow like a bumblebee"
			);
		}
		if(modLoaded("allure")){
			subtitles.addAll(
				"Very Alluring!",
				"Celesital horrors included!",
				"Fluid Energy in my veins!",
				"Wandura appears!"
			);
		}
		if(modLoaded("sparemusic")){
			subtitles.addAll(
				"Got some Spare Music?",
				"Music straight to your ears!"
			);
		}
	}

	boolean modLoaded(String... names){
		for(String name: names){
			if(Vars.mods.locateMod(name) != null) return true;
		}
		return false;
	}
}