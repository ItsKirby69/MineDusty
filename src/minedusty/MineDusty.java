package minedusty;

import minedusty.content.DustBlocks;
import minedusty.content.DustLiquids;
import minedusty.content.DustTechTree;
import minedusty.content.DustLiquids;
import minedusty.content.DustBullets;
import minedusty.content.DustPlanets;
import minedusty.content.DustItems;
import minedusty.content.DustSectors;

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
                dialog.cont.add("A spooky Warning").row();
                dialog.cont.image(Core.atlas.find("minedusty-frog.png")).pad(20f).row();
				dialog.cont.add("[gray]Please note that this mod (Minedusty) is potentially unstable.[]").row();
				dialog.cont.add("[scarlet]Contact me through discord[] [brick]itsdakirby69[] [scarlet]for any bugs that occur[]").row();
				dialog.cont.add("[gray]Mess around with the mod and have fun :)[]").row();
                dialog.cont.button("Got it fam", dialog::hide).size(150f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent(){
		DustItems.load();
        DustBlocks.load();
        //DustTechTree.load();
        //DustLiquids.load();
        //DustBullets.load();
        //DustPlanets.load();
        //DustSectors.load();
    }

}