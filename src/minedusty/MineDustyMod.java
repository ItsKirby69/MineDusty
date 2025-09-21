package minedusty;

import minedusty.blocks.*;
import minedusty.content.*;
import minedusty.gen.*;
import minedusty.graphics.*;
import minedusty.planets.*;
import minedusty.ui.DustSettings;
import minedusty.ui.DustyPopup;
import arc.*;
import arc.files.Fi;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.EventType.*;
import mindustry.mod.*;

import static arc.Core.bundle;
import static mindustry.Vars.*;

import org.w3c.dom.events.EventTarget;

public class MineDustyMod extends Mod {

	public MineDustyMod() {
		Log.info("[lightgray]*cough* *cough* [][acid]MineDusty[] [white]is [][lightgray]*cough*[] [white]loaded");

		/*
		 * Events.on(EventType.ClientLoadEvent.class, e -> {
		 * Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", new
		 * DustMenuRender());
		 * });
		 */
	}

	@Override
	public void init() {
		Events.run(EventType.Trigger.draw, Renderer::draw);
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

		DustPlanets.load();
		DustSectors.load();
		TheiaTechTree.load();

	}

}