package minedusty.world.blocks.environment;

import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.blocks.environment.OreBlock;

public class ChunkOreBlock extends OreBlock{

    public ChunkOreBlock(String name, Item ore) {
        super(name, ore);
    }
    
    @Override
    public boolean canReplace(Block block) {
        return false;
    }
}
