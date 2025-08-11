package minedusty.world.blocks.production;

import arc.Core;
import arc.graphics.g2d.Draw;
import mindustry.gen.Building;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Drill;

public class TeamDrill extends Drill{
    
    public TeamDrill(String name){
        super(name);

        teamRegion = Core.atlas.find(name + "-team");
    }

    public class TeamDrillBuild extends DrillBuild {
        @Override
        public void draw(){
            super.draw();

            if(teamRegion != null){
                Draw.color(team.color);
                Draw.rect(teamRegion, x, y);
                Draw.color();
            }
        }
    }
}
