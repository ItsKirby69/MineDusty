package minedusty.graphics;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawRegion;

public class DrawPress extends DrawRegion{
    public TextureRegion shadowRegion;
    public String shadowSuffix = "-shadow";

    /** How far down left does the shadow move */
    public float shadowOffset = 2f;
    /** Scale of press head at peak "height"*/
    public float maxScale = 1.3f;
    /** At what point in the progress the press head "falls" back? */
    public float thresholdFall = 0.8f;

    /** Power value when going up. 0.5f by default eases out. */
    public float easeUp = 0.5f;
    /** Power value when going down. 2.0f by default eases in. */
    public float easeDown = 2f;
    /** Offset for the press progress */
    public float progressOffset = 0f;


    public DrawPress(String suffix){
        super(suffix);
    }

    public DrawPress(){}

    @Override
    public void draw(Building build){
        float prog = (build.progress() + progressOffset) % 1f;
        float scale;
        if(prog < thresholdFall){
            float upProg = prog / thresholdFall;
            scale = 1f + (maxScale - 1f) * Mathf.pow(upProg, easeUp);
        }else{
            float downProg = (prog - thresholdFall) / (1f - thresholdFall);
            scale = maxScale - (maxScale - 1f) * Mathf.pow(downProg, easeDown);
        }

        if(shadowRegion != null){
            float xOffset, yOffset;
            if(prog < thresholdFall){
                float moveProg = prog / thresholdFall;
                float easeOut = Mathf.pow(moveProg, easeUp);
                xOffset = -shadowOffset * easeOut;
                yOffset = -shadowOffset * easeOut;
            }else{
                float moveProg = (prog - thresholdFall) / (1f - thresholdFall);
                float easeIn = Mathf.pow(moveProg, easeDown);
                xOffset = -shadowOffset + shadowOffset * easeIn;
                yOffset = -shadowOffset + shadowOffset * easeIn;
            }

            Draw.rect(shadowRegion, build.x + xOffset + x, build.y + yOffset + y,
                shadowRegion.width * shadowRegion.scl(),
                shadowRegion.height * shadowRegion.scl());
        }

        Draw.rect(region, build.x + x, build.y + y,
            region.width * region.scl() * scale,
            region.height * region.scl() * scale);
    }

    @Override
    public void load(Block block){
        super.load(block);
        shadowRegion = Core.atlas.find(block.name + suffix + shadowSuffix, region);
    }

}
