package minedusty.world.blocks.defense;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.defense.RegenProjector;
import minedusty.content.DustyEffects;

import static mindustry.Vars.*;

// A diamond shaped region regen projector
public class ChloroMenderProjector extends RegenProjector{

    public ChloroMenderProjector(String name) {
        super(name);
        effect = DustyEffects.cregenParticle;
        effectChance = 0.005f;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        drawOverlay(x * tilesize + offset, y * tilesize + offset, rotation);

        float wx = x * tilesize + offset;
        float wy = y * tilesize + offset;

        dashDiamond(wx, wy, range * tilesize, baseColor);
        indexer.eachBlock(player.team(), Tmp.r1.setCentered(wx, wy, range * tilesize * 2), b -> withinDiamond(wx, wy, range * tilesize, b), t -> {
            Drawf.selected(t, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f)));
        });
    }

    public static boolean withinDiamond(float cx, float cy, float range, Building b){
        return Math.abs(b.x - cx) + Math.abs(b.y - cy) <= range + b.hitSize()/2f;
    }

    public class ChloroMenderBuild extends RegenProjectorBuild{
        
        @Override
        public void updateTargets(){
            targets.clear();
            indexer.eachBlock(team, Tmp.r1.setCentered(x, y, range * tilesize * 2), b -> withinDiamond(x, y, range * tilesize, b), targets::add);
        }

        @Override
        public void drawSelect(){
            drawOverlay(this.x, this.y, this.rotation);

            dashDiamond(x, y, range * tilesize, baseColor);
            for(var target : targets){
                Drawf.selected(target, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f)));
            }
        }
    }
    
    public static void dashDiamond(float x, float y, float radius, Color color){
        Drawf.dashLine(color, x, y + radius, x + radius, y);
        Drawf.dashLine(color, x + radius, y, x, y - radius);
        Drawf.dashLine(color, x, y - radius, x - radius, y);
        Drawf.dashLine(color, x - radius, y, x, y + radius);
    }
}


