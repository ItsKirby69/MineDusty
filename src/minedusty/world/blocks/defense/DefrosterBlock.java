package minedusty.world.blocks.defense;

import static mindustry.Vars.tilesize;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Time;
import arc.util.io.*;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import minedusty.graphics.DrawHeatCrafterEff;
import minedusty.world.meta.DustStatUnit;

/** Block that defrosts defense blocks in surrounding area. */
public class DefrosterBlock extends GenericCrafter{
    public int heatOutput = 1;

    public Color baseColor = Color.valueOf("f4a084");

    public DefrosterBlock(String name, int h) {
        super(name);
        
        heatOutput = h;

        drawer = new DrawMulti(
            new DrawDefault(),
            new DrawHeatCrafterEff(){{
                minEfficiency = 0f;
            }}
        );

        size = 1;
        ambientSound = Sounds.loopElectricHum;
        updateEffect = Fx.generatespark;
        updateEffectChance = 0.01f;
        canOverdrive = false;
        emitLight = true;
        lightColor = Color.blue;
        lightRadius = 30f + 15f * size;

        // High priority for enemies to destroy
        flags = EnumSet.of(BlockFlag.generator);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.output, heatOutput, DustStatUnit.thermalHeat);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("heat", (DefrosterBlockBuild entity) -> new Bar(
                () -> "Heat: " + Mathf.round(entity.heat) + " / " + heatOutput, 
                () -> Pal.lightOrange, 
                () -> heatOutput == 0 ? 0f : entity.heat / heatOutput
            ).blink(Color.white)
        );
    }

    public class DefrosterBlockBuild extends GenericCrafterBuild{
        public float heat;

        @Override
        public float efficiencyScale(){
            return efficiency;
        }

        @Override
        public void draw(){
            super.draw();

            float f = 1f - (Time.time / 100f) % 1f;

            Draw.color(baseColor);
            Draw.alpha(1f - (f < 0.93f ? 0f : 1f));
            Lines.stroke((2f * f + 0.2f) * 1.25f * (heat > 0f ? 1f : 0f));
            Lines.square(x, y, Math.min(1f + (1f - f) * size * tilesize / 2f, size * tilesize/2f));

            Draw.reset();
        }
        
        @Override
        public void write(Writes write){
            super.write(write);
            write.f(heat);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            heat = read.f();
        }
    }

}
