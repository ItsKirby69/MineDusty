package minedusty;

import minedusty.blocks.*;
import minedusty.content.*;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

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
				dialog.cont.add("[gray]Please note that this mod ([][white]Minedusty[][gray]) is potentially unstable.[]").row();
				dialog.cont.add("[scarlet]Contact me through discord[] [brick]itsdakirby69[] [scarlet]or file[]").row();
				dialog.cont.add("[scarlet]an issue for the repo for any[] [gold]questions/bugs/suggestions![]").row();
				dialog.cont.add("[gray]Mess around with the mod and have fun[]").row();
                dialog.cont.button("Got it fam", dialog::hide).size(150f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent(){
		//teams, items, fluids, effects. Thanks @sl0tterleet
		DustSounds.load();
		DustItems.load();
		DustLiquids.load();
		//DustEffects.load(); the actual file is named DustyEffects for some reason
		//DustTeams.load();
		DustWeathers.load();
		DustUnits.load();
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