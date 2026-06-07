package minedusty.world.blocks.defense;

import arc.scene.ui.layout.Table;
import mindustry.graphics.Drawf;

import static arc.Core.*;

import arc.math.Mathf;

public class ElectricDefroster extends DefrosterBlock{

    public ElectricDefroster(String name, int heat) {
        super(name, heat);
        itemCapacity = 0;
    }

    public class ElectricDefrosterBuild extends DefrosterBlockBuild{

        @Override
        public void updateTile(){
            super.updateTile();
            
            heat = efficiency * heatOutput;
        }
    
        @Override
        public void drawLight(){
            Drawf.light(x, y, lightRadius * efficiency, baseColor, 0.7f * efficiency);
        }

        @Override
        public void display(Table table) {
            super.display(table);
            if(settings.getBool("dusty-block-debug")){
                table.row();
                table.table(t -> {
                    t.label(() -> {
                        return "Efficiency: " + Mathf.round(efficiency * 100f, 1f) + "%";
                    }).left().growX();
                }).growX();
            }
        }
    }
}
