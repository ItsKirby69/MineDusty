package minedusty.world.blocks.defense;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.world.consumers.ConsumeItemFlammable;

/** Block that defrosts defense blocks in surrounding area. */
public class SolidDefroster extends DefrosterBlock{
    public float warmupSpeed = 0.005f;

    public SolidDefroster(String name, int h) {
        super(name, h);
        ambientSound = Sounds.loopSmelter;
    }

    public class SolidDefrosterBuild extends DefrosterBlockBuild{
        public float burnTime, warmup;

        @Override
        public void updateTile(){

            if(burnTime <= 0f && shouldConsume()){

                var cons = findConsumer(c -> c instanceof ConsumeItemFlammable);

                if(cons instanceof ConsumeItemFlammable flam){
                    var item = flam.getConsumed(this);

                    if(item != null){
                    float multi = flam.efficiencyMultiplier(this);

                    consume();
                    burnTime = craftTime * multi;
                    }
                }
            }

            if(burnTime > 0f){
                burnTime -= Time.delta;
            }
            boolean burning = burnTime > 0f;

            warmup = Mathf.approachDelta(warmup, burning ? 1f : 0f, warmupSpeed);

            heat = warmup * heatOutput;
        }

        @Override
        public float efficiencyScale(){
            return heat / heatOutput;
        }

        @Override
        public void drawLight(){
            Drawf.light(x, y, lightRadius * efficiencyScale(), baseColor, 0.7f * efficiencyScale());
        }

        @Override
        public void display(Table table) {
            super.display(table);

            table.row();
            table.table(t -> {
                t.label(() -> {
                    return "Heat: " + heat;
                }).left().growX();
            }).growX();
        }
    }
}
