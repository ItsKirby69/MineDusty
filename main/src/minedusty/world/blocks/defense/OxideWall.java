package minedusty.world.blocks.defense;

import arc.graphics.Blending;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.*;
import mindustry.content.Weathers;
import mindustry.world.blocks.defense.Wall;
import minedusty.utils.WeatherUtil;

import static mindustry.Vars.*;
import static arc.Core.*;

/** Wall which oxidizes over time */
public class OxideWall extends Wall {

    public int maxStages = 3;
    public float stageDur = 3600 * 5f;
    /** How fast stagetime passes by if rainy weather */
    public float rainAccel = 2f;

    public TextureRegion[][] stageRegions;

    public OxideWall(String name){
        super(name);
        update = true;
    }

    @Override
    public void load(){
        super.load();

        stageRegions = new TextureRegion[maxStages + 1][variants];

        for(int i = 0; i <= maxStages; i++){
            for(int v = 0; v < (variants > 0 ? variants : 1); v++){
                stageRegions[i][v] = atlas.find(variants > 0 ?
                    name + "-stage" + i + "-" + (v + 1) :
                    name + "-stage" + i
                );
            }
        }
    }

    public class OxideWallBuild extends WallBuild {
        public int currentStage = 0;
        public float stageTimer = 0f;
        public float currStageDuration;

        public float timeScale = 1f;

        @Override
        public void created(){
            randomStageDur();
        }

        public void randomStageDur(){
            currStageDuration = stageDur + Mathf.random(3600f * 1f, 3600f * 12f);
        }

        @Override
        public void updateTile(){
            
            timeScale = WeatherUtil.containsWeather(Weathers.rain) ? rainAccel : 1f;

            if(currentStage < maxStages){
                stageTimer += Time.delta * timeScale;
                
                if (stageTimer >= currStageDuration){ 
                    stageTimer = 0f;
                    currentStage++;

                    randomStageDur();
                }
            }
        }

        @Override
        public void draw(){
            int variant = Mathf.randomSeed(tile.pos(), 0, Math.max(0, variants - 1));
            int stage = Math.min(currentStage, maxStages);

            Draw.rect(stageRegions[stage][variant], x, y, rotdeg());
            drawTeamTop();
            
            if(flashHit){
                if(hit < 0.0001f) return;

                Draw.color(flashColor);
                Draw.alpha(hit * 0.5f);
                Draw.blend(Blending.additive);
                Fill.rect(x, y, tilesize * size, tilesize * size);
                Draw.blend();
                Draw.reset();

                if(!state.isPaused()){
                    hit = Mathf.clamp(hit - Time.delta / 10f);
                }
            }
        }
    }
}