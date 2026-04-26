package minedusty.world.blocks.production;

import arc.graphics.g2d.Draw;
import arc.math.geom.Geometry;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.production.GenericCrafter;

import static mindustry.Vars.*;

/** Generic Crafter with custom features, like directional item outputs for example */
public class BetterGenericCrafter extends GenericCrafter{
    /** Same as liquid Output directions but for items. -1 for all directions */
    public int[] itemOutputDirections = {-1};
    /** Directional input. -1 for all directions */
    public int[] liquidInputDirections = {-1}; // TODO ehhh later

    public BetterGenericCrafter(String name) {
        super(name);
    }

    @Override
    public void drawOverlay(float x, float y, int rotation){
        super.drawOverlay(x, y, rotation);

        if(outputItems != null){
            for(int i = 0; i < outputItems.length; i++){
                int dir = itemOutputDirections.length > i ? itemOutputDirections[i] : -1;

                if(dir != -1){
                    Draw.rect(
                        outputItems[i].item.fullIcon,
                        x + Geometry.d4x(dir + rotation) * (size * tilesize / 2f + 4),
                        y + Geometry.d4y(dir + rotation) * (size * tilesize / 2f + 4),
                        8f, 8f
                    );
                }
            }
        }
    }

    public class BetterGenericCrafterBuild extends GenericCrafterBuild{
        @Override
        public void craft(){
            consume();

            if(outputItems != null){
                for(var output : outputItems){
                    for(int i = 0; i < output.amount; i++){
                        ioffload(output.item);
                    }
                }
            }

            if(wasVisible){
                craftEffect.at(x, y);
            }
            progress %= 1f;
        }

        public void ioffload(Item item) {
            produced(item, 1);
            int dump = cdump;

            int dir = -1;
            if(outputItems != null){
                for(int j = 0; j < outputItems.length; j++){
                    if(outputItems[j].item == item){
                        dir = itemOutputDirections.length > j ? itemOutputDirections[j] : - 1;
                        break;
                    }
                }
            }
            for(int i = 0; i < proximity.size; ++i) {
                incrementDump(proximity.size);
                Building other = (Building)proximity.get((i + dump) % proximity.size);
                if(dir == -1){
                    if (other.acceptItem(this, item) && canDump(other, item)) {
                        other.handleItem(this, item);
                        return;
                    }
                }else{
                    if((dir + rotation) % 4 != relativeTo(other)) continue;
                    if(other.acceptItem(this, item) && canDump(other, item)) {
                        other.handleItem(this, item);
                        return;
                    }
                }
            }

            handleItem(this, item);
        }

        @Override
        public void dumpOutputs(){
            if(outputItems != null && timer(timerDump, dumpTime / timeScale)){
                for(int j = 0; j < outputItems.length; j++){
                    Item item = outputItems[j].item;
                    int dir = itemOutputDirections.length > j ? itemOutputDirections[j] : -1;

                    if(dir == -1){
                        dump(item);
                    }else{
                        int dump = cdump;
                        for(int i = 0; i < proximity.size; ++i) {
                            incrementDump(proximity.size);
                            Building other = (Building)proximity.get((i + dump) % proximity.size);

                            if((dir + rotation) % 4 != relativeTo(other)) continue;
                            if(!items.has(item)) break;

                            if(other.acceptItem(this, item) && canDump(other, item)) {
                                other.handleItem(this, item);
                                items.remove(item, 1);
                                break;
                            }
                        }
                    }
                }
            }

            if(outputLiquids != null){
                for(int i = 0; i < outputLiquids.length; i++){
                    int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

                    dumpLiquid(outputLiquids[i].liquid, 2f, dir);
                }
            }
        }
    }
}
