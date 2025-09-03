package minedusty;

import arc.*;
import arc.struct.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import minedusty.annotations.Annotations.*;
import minedusty.blocks.*;
import minedusty.content.*;
import minedusty.graphics.*;
import minedusty.planets.*;
import minedusty.ui.*;

import static arc.Core.bundle;
import static mindustry.Vars.headless;

import mindustry.Vars;

/**
 * The mod's main mod class. Contains static references to other modules.
 * @author Avant Team
 */
@LoadRegs("error")// Need this temporarily, so the class gets generated.
@EnsureLoad
public class MineDustyMod extends Mod{
    public static boolean tools = false;

    public MineDustyMod(){
        this(false);
    }

    /**
     * Constructs the mod, and binds some functionality to the game under certain circumstances.
     * @param tools Whether the mod is in an asset-processing context.
     */
    public MineDustyMod(boolean tools){
        MineDustyMod.tools = tools;
        Log.info("*cough* *cough* MineDusty is *cough* loaded");

        /*
		 * Events.on(EventType.ClientLoadEvent.class, e -> {
		 * Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", new
		 * DustMenuRender());
		 * });
		 */
    }

    @Override
    public void init(){
		DustSettings.load();
		Events.on(ClientLoadEvent.class, e -> {
			// show dialog upon startup
			DustyPopup.check();
		});
    }

    @Override
    public void loadContent(){
		/** Gets random line from a text file. */
		Seq<String> subtitles = new Seq<>(bundle.get("subtitle.lines").split("/"));
		Vars.mods.list().forEach(mod -> {
			if(mod.main == this){
				mod.meta.subtitle = "[acid]" + subtitles.random();
			}
		});
		
		//EntityRegistry.register();
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