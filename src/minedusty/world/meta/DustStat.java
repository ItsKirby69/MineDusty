package minedusty.world.meta;

import mindustry.world.meta.*;

public class DustStat extends Stat{

    public static final Stat
    powerRanged = new Stat("powerRanged", StatCat.power),

    solarRequired = new DustStat("solarrequirement", StatCat.crafting),
    requiredEfficiency = new DustStat("requiredefficiency", StatCat.crafting);

    public DustStat(String name, StatCat category){
        super(name, category);
    }

    public DustStat(String name){
        this(name, StatCat.general);
    }
}
