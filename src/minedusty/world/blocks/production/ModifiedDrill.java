package minedusty.world.blocks.production;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.blocks.production.Drill;

public class ModifiedDrill extends Drill{
    public TextureRegion teamRegion;
    public boolean drawTopUnder = false;
    
    public ModifiedDrill(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();
        teamRegion = Core.atlas.find(name + "-team", Core.atlas.find("clear"));
    }

    public class ModifiedDrillBuild extends DrillBuild {
        public float boost1, boost2;

        @Override
        public void draw(){
            float s = 0.3f;
            float ts = 0.6f;

            Draw.rect(region, x, y);
            Draw.z(Layer.blockCracks);
            drawDefaultCracks();

            Draw.z(Layer.blockAfterCracks);
            if(drawRim){
                Draw.color(heatColor);
                Draw.alpha(warmup * ts * (1f - s + Mathf.absin(Time.time, 3f, s)));
                Draw.blend(Blending.additive);
                Draw.rect(rimRegion, x, y);
                Draw.blend();
                Draw.color();
            }

            if(drawTopUnder){
                Draw.rect(topRegion, x, y);
                if(drawSpinSprite){
                    Drawf.spinSprite(rotatorRegion, x, y, timeDrilled * rotateSpeed);
                }else{
                    Draw.rect(rotatorRegion, x, y, timeDrilled * rotateSpeed);
                }
            } else {
                if(drawSpinSprite){
                    Drawf.spinSprite(rotatorRegion, x, y, timeDrilled * rotateSpeed);
                }else{
                    Draw.rect(rotatorRegion, x, y, timeDrilled * rotateSpeed);
                }
                Draw.rect(topRegion, x, y);
            }

            if(dominantItem != null && drawMineItem){
                Draw.color(dominantItem.color);
                Draw.rect(itemRegion, x, y);
                Draw.color();
            }

            if(teamRegion != null){
                Draw.color(team.color);
                Draw.rect(teamRegion, x, y);
                Draw.color();
            }
        }
    }
}
