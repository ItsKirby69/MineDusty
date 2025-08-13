package minedusty;

import minedusty.blocks.*;
import minedusty.content.*;
import minedusty.gen.*;
import minedusty.graphics.DustCacheLayers;
import minedusty.graphics.DustMenuRender;
import minedusty.graphics.DustShaders;
import minedusty.planets.*;
import minedusty.ui.DustSettings;
import minedusty.ui.DustyPopup;
import arc.*;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.mod.Mods.ModMeta;
import mindustry.ui.dialogs.*;
import mindustry.ui.fragments.MenuFragment;

import static arc.Core.bundle;
import static mindustry.Vars.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MineDustyMod extends Mod {

	public MineDustyMod() {
		Log.info("*cough* *cough* MineDusty is *cough* loaded");

		/*
		 * Events.on(EventType.ClientLoadEvent.class, e -> {
		 * Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", new
		 * DustMenuRender());
		 * });
		 */
	}

	@Override
	public void init() {
		DustSettings.load();
		Events.on(ClientLoadEvent.class, e -> {
			// show dialog upon startup
			DustyPopup.check();
		});
	}

	@Override
	public void loadContent() {
		/** Gets random line from a text file. */
		Seq<String> subtitles = new Seq<>(bundle.get("subtitle.lines").split("/"));
		Log.info("Lines: " + subtitles.random());
		Vars.mods.list().forEach(mod -> {
			if(mod.main == this){
				mod.meta.subtitle = "[acid]" + subtitles.random();
				Log.info("LOADED SUBTITLE AS: " + subtitles.random());
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

		DustPlanets.load();
		DustSectors.load();
		TheiaTechTree.load();

	}

}