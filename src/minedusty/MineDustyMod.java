package minedusty;

import minedusty.blocks.*;
import minedusty.content.*;
import minedusty.gen.*;
import minedusty.graphics.DustCacheLayers;
import minedusty.graphics.DustShaders;
import minedusty.planets.*;
import minedusty.ui.DustSettings;
import minedusty.ui.DustyPopup;
import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import static mindustry.Vars.*;

public class MineDustyMod extends Mod{

    public MineDustyMod(){
        Log.info("*cough* *cough* MineDusty is *cough* loaded");
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
		EntityRegistry.register(); //ignore error
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
		
        DustPlants.loadContent();
		DustEnv.loadContent();
		DustCore.loadContent();
		
		DustDistribution.loadContent();

		DustPlanets.load();
		
		//DustSectors.load();
        //DustTechTree.load(); idk where to put his one yet

    }

}