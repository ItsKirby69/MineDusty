package minedusty;

import minedusty.blocks.*;
import minedusty.content.*;
import minedusty.graphics.DustCacheLayers;
import minedusty.graphics.DustShaders;
import minedusty.planets.*;
import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import static mindustry.Vars.*;

public class MineDusty extends Mod{

    public MineDusty(){
        Log.info("*cough* *cough* MineDusty is *cough* loaded");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("warn");
                dialog.cont.add("A spooky Disclaimer").row();
                dialog.cont.image(Core.atlas.find("minedusty-dog")).pad(20f).row();
				dialog.cont.add("[gray]This mod ([][white]Minedusty[][gray]) is in Beta![]").row();
				dialog.cont.add("[gray]That means its potentially unstable and incomplete![]").row();
				dialog.cont.add("[scarlet]If you'd like to give [gold]feedback, bug reports, or more...[]").row();
				dialog.cont.add("[scarlet]Create an issue in the github repo![]").row();
                dialog.cont.button("Alrighty", dialog::hide).size(150f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent(){
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
		//DustEffects.load(); the actual file is named DustyEffects for some reason
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