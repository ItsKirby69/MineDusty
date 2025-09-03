package minedusty.world.blocks.production;

import arc.util.Nullable;
import mindustry.type.ItemStack;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.consumers.ConsumeItemFilter;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.world.meta.*;

//This is heavily WIP and probably doesn't work
public class ConsumeOutputGenerator extends ConsumeGenerator{
    /** Written to outputItems as a single-element array if outputItems is null. */
    public @Nullable ItemStack outputItem;

	public ConsumeOutputGenerator(String name){
		super(name);
	}

	@Override
    public void init(){
        filterItem = findConsumer(c -> c instanceof ConsumeItemFilter);
        filterLiquid = findConsumer(c -> c instanceof ConsumeLiquidFilter);
		
        if(outputLiquid != null){
            outputsLiquid = true;
            hasLiquids = true;
        }
		if(outputItem != null){
			hasItems = true;
		} 

        if(explodeOnFull && outputLiquid != null && explosionPuddleLiquid == null){
            explosionPuddleLiquid = outputLiquid.liquid;
        }

        //TODO hardcoded
        emitLight = true;
        lightRadius = 65f * size;
        super.init();
    }

	@Override
    public void setStats(){
        super.setStats();

        if((hasItems && itemCapacity > 0) || outputItem != null){
            stats.add(Stat.productionTime, itemDuration / 60f, StatUnit.seconds);
        }

		if(outputItem != null){
			stats.add(Stat.output, StatValues.items(itemDuration, outputItem));
		}

        if(outputLiquid != null){
            stats.add(Stat.output, StatValues.liquid(outputLiquid.liquid, outputLiquid.amount * 60f, true));
        }
    }
	@Override
    public boolean outputsItems(){
        return outputItem != null;
    }

}	
