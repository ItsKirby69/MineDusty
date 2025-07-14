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
import arc.util.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.fragments.MenuFragment;

import static mindustry.Vars.*;

public class MineDustyMod extends Mod{

    public MineDustyMod(){
        Log.info("*cough* *cough* MineDusty is *cough* loaded");
    
		/*Events.on(EventType.ClientLoadEvent.class, e -> {
			Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", new DustMenuRender());
		});*/
	}

	@Override
	public void init(){
		DustSettings.load();
        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
			DustyPopup.check();
        });
	}

    @Override
    public void loadContent(){
		// @ts-ignore
		EntityRegistry.register();
		if (!headless){
			DustShaders.load();
			DustCacheLayers.load();
		}
		//teams, items, fluids, effects. Thanks @sl0tterleet
		DustTeams.load();
		DustSounds.load();
		DustItems.load();
		DustLiquids.load();
		DustAttributes.load();
		DustStatusEffects.load();
		//DustTeams.load();
		DustWeathers.load();
		DustUnitTypes.load();
		//blocks
		DustDefence.loadContent();
		DustCrafters.loadContent();
		
		DustEnv.loadContent();
        DustPlants.loadContent();
		DustCore.loadContent();
		
		DustDistribution.loadContent();

		DustPlanets.load();
		
		DustSectors.load();
        TerraTechTree.load();

    }

}