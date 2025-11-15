package minedusty.world.meta;

import mindustry.world.meta.*;

public class DustStat extends Stat{

    public static final Stat
    powerRanged = new DustStat("powerRanged", StatCat.power),

    solarRequired = new DustStat("solarrequirement", StatCat.crafting),
    requiredEfficiency = new DustStat("requiredefficiency", StatCat.crafting),

    heatEfficiency = new DustStat("heatefficiency", StatCat.crafting);

    public DustStat(String name, StatCat category){
        super(name, category);
    }

    public DustStat(String name){
        this(name, StatCat.general);
    }
}
