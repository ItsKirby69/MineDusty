package minedusty.world.blocks.power;

import static mindustry.Vars.*;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Tmp;
import arc.util.io.*;
import mindustry.gen.Building;
import mindustry.gen.Tex;
import mindustry.gen.Unit;
import mindustry.graphics.*;
import mindustry.input.Placement;
import mindustry.world.*;
import mindustry.world.meta.*;
import minedusty.graphics.DrawFire;

import static arc.Core.*;

public class LanternBlock extends Block{
    public TextureRegion topRegion;
    public Color dimColor = Color.valueOf("#cf9635");
    public Color brightColor = Color.valueOf("#ffeaa4");
    public DrawFire fire = new DrawFire();

    public float minRadius = 45f;
    public float maxRadius = 180f;

    public float powerUse = 25f/60f;

    public LanternBlock(String name){
        super(name);

        hasPower = true;
        update = true;
        configurable = true;
        saveConfig = true;
        swapDiagonalPlacement = true;
        consumePowerDynamic((LanternBuild build) -> build.brightnessScale * powerUse);

        config(Float.class, (LanternBuild tile, Float value) -> {
            tile.brightnessScale = value;
        });
    }

    @Override
    public void init(){
        lightRadius = maxRadius*2.5f;
        lightClipSize = Math.max(lightClipSize, lightRadius * 3f);
        emitLight = true;

        super.init();
    }

    @Override
    public void load(){
        super.load();
        topRegion = atlas.find(name + "-top");
        fire.load(this);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.powerUse, powerUse * 60f, StatUnit.powerSecond);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, minRadius, Tmp.c1.set(Pal.placing).a(0.5f));
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, maxRadius, Pal.placing);
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation){
        var placeRadius2 = Mathf.pow(maxRadius * 0.5f / tilesize, 2f) * 3;
        Placement.calculateNodes(points, this, rotation, (point, other) -> point.dst2(other) <= placeRadius2);
    }

    @Override
    public int minimapColor(Tile tile){
        var build = (LanternBuild)tile.build;
        if(build == null) return 0;

        Color col = Tmp.c1.set(dimColor).lerp(brightColor, Mathf.clamp(build.efficiency * build.brightnessScale));;
        return col.rgba8888();
    }

    public class LanternBuild extends Building{
        public float brightnessScale = 0.5f;
        public float smoothTime = 1f;
    
        @Override
        public void configured(Unit player, Object value){
            super.configured(player, value);

            if(!headless) renderer.minimap.update(tile);
        }

        @Override
        public void draw(){
            super.draw();
            fire.draw(this);
        }

        @Override
        public void drawLight(){
            float radius = Mathf.lerp(minRadius, maxRadius, brightnessScale);

            Color col = Tmp.c1.set(dimColor).lerp(brightColor, Mathf.clamp(efficiency * brightnessScale));
            Drawf.light(x, y, radius * Math.min(smoothTime, 2f), col, (efficiency >= 0.001f ? 0.2f + 0.8f * brightnessScale * efficiency : 0f));
        }


        @Override
        public void updateTile(){
            smoothTime = Mathf.lerpDelta(smoothTime, timeScale, 0.1f);
        }

        @Override
        public void buildConfiguration(Table table){
            table.table(t -> {
                t.setBackground(Tex.pane);
                t.slider(0f, 1f, 0.125f, brightnessScale, value -> {
                    configure(value);
                }).width(160f);
            });

        }

        @Override
        public Float config(){
            return brightnessScale;
        }

        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(this == other){
                deselect();
                return false;
            }

            return true;
        }
        @Override
        public void write(Writes write){
            super.write(write);
            write.f(brightnessScale);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            brightnessScale = read.f();
        }
    }
    
}
