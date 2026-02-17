package minedusty.world.blocks.defense;

import arc.scene.ui.layout.Table;
import mindustry.graphics.Drawf;

public class ElectricDefroster extends DefrosterBlock{

    public ElectricDefroster(String name, int h) {
        super(name, h);
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

            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Eff: " + efficiency;
                }).left().growX();
            }).growX();
        }
    }
}
