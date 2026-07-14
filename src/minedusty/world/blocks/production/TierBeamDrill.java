package minedusty.world.blocks.production;

import static mindustry.Vars.*;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.*;
import mindustry.content.Items;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.production.BeamDrill;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.meta.*;
import minedusty.world.blocks.production.TierBeamDrill.BoostEntry;
import minedusty.world.meta.DustStat;


/** A beam drill with capabilities to mine higher tiered ores (when boosted). Same as {@link TierDrill} */
public class TierBeamDrill extends BeamDrill{
    public TextureRegion darktopRegion, gemRegion;
    public ObjectMap<Item, Item> tierMap = new ObjectMap<>();

    public Seq<BoostEntry> boostLiquids = new Seq<>();

    public TierBeamDrill(String name){
        super(name);
        blockedItems = Seq.with(Items.beryllium, Items.thorium);
    }

    /** A boost... entry... */
    public static class BoostEntry {
        /** Suffix for laser textures */
        public String suffix;
        public Liquid liquid;
        public float amount;

        public Color heatColor = null, sparkColor = null;

        public TextureRegion laser = null, laserEnd = null, laserCenter = null;
        
        public ConsumeLiquid consumer;
        
        public BoostEntry(Liquid liquid, float amount){
            this.liquid = liquid;
            this.amount = amount;
        }

        public BoostEntry heatColor(Color c){
            this.heatColor = c;
            return this;
        }

        public BoostEntry sparkColor(Color c){
            this.sparkColor = c;
            return this;
        }

        public BoostEntry laser(String suffif){
            this.suffix = suffif;
            return this;
        }
    }

    public BoostEntry addBoost(Liquid liquid, float amount){
        BoostEntry entry = new BoostEntry(liquid, amount);
        boostLiquids.add(entry);

        return entry;
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, topRegion, gemRegion};
    }
    
    @Override
    public void setStats(){
        super.setStats();
        if(!boostLiquids.isEmpty()){
            stats.add(DustStat.boostItems, t -> {
                for(ObjectMap.Entry<Item, Item> entry : tierMap){
                    Item raw = entry.key;
                    Item boosted = entry.value;
                    DustStat.boostedItems(raw, boosted).display(t);
                    t.row();
                }
            });
            stats.replace(Stat.booster, t -> {
                for(BoostEntry entry : boostLiquids){
                    StatValues.speedBoosters("{0}" + StatUnit.timesSpeed.localized(), 
                        entry.amount, optionalBoostIntensity, true, entry.consumer::consumes).display(t);
                    t.row();
                }
            });
        }
    }
    
    @Override
        public void setBars(){
            super.setBars();

            for(BoostEntry entry : boostLiquids){
                removeBar("liquid-" + entry.liquid.name);
            }

            addLiquidBar((TierBeamDrillBuild build) -> {
                BoostEntry boost = build.storedBoostEntry();
                return boost == null ? null : boost.liquid;
            });
        }

    @Override
    public void init(){
        for(BoostEntry entry : boostLiquids){
            entry.consumer = consumeLiquid(entry.liquid, entry.amount);
            entry.consumer.booster = true;
            entry.consumer.optional = true;
        }
        super.init();
    }

    @Override
    public void load(){
        super.load();
        for(BoostEntry entry : boostLiquids){
            if(entry.suffix != null){
                entry.laser = Core.atlas.find(name + "-beam-boost-" + entry.suffix, laserBoost);
                entry.laserEnd = Core.atlas.find(name + "-beam-boost-end-" + entry.suffix, laserEndBoost);
                entry.laserCenter = Core.atlas.find(name + "-beam-boost-center-" + entry.suffix, laserCenterBoost);
            }
        }
        darktopRegion = Core.atlas.find(name + "-darktop");
        gemRegion = Core.atlas.find(name + "-gem");
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(plan.rotation > 1 ? darktopRegion : topRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
        Draw.rect(gemRegion, plan.drawx(), plan.drawy());
    }

    public class TierBeamDrillBuild extends BeamDrillBuild{
        protected BoostEntry activeBoost = null;

        protected BoostEntry resolveActiveBoost(){
            for(BoostEntry entry : boostLiquids){
                if(entry.consumer.efficiency(this) > 0f) return entry;
            }
            return null;
        }

        // Store only one liquid at a time because bars look uglyyy
        protected BoostEntry storedBoostEntry(){
            for(BoostEntry entry : boostLiquids){
                if(liquids.get(entry.liquid) > 0.001f) return entry;
            }
            return null;
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            BoostEntry stored = storedBoostEntry();
            if(stored != null && stored.liquid != liquid){
                return false;   
            }
            return super.acceptLiquid(source, liquid);
        }
    
        @Override
        public void onProximityUpdate(){
            updateLasers();
            updateFacing();
        }

        @Override
        public void drawSelect(){
            drawItemSelection(resolveDisplayItem());
        }

        public Item resolveDisplayItem(){
            for(Tile tile : facing){
                if(tile == null) continue;

                Item wallDrop = tile.wallDrop();
                if(wallDrop != null) return resolveTierDrop(wallDrop);
            }
            return lastItem;
        }

        public Item resolveTierDrop(Item Drop){
            boolean isBoosted = resolveActiveBoost() != null;
            return isBoosted && tierMap.containsKey(Drop) ? tierMap.get(Drop) : Drop;
        }

        @Override
        public void updateTile(){
            if(lasers[0] == null) updateLasers();

            warmup = Mathf.approachDelta(warmup, Mathf.num(efficiency > 0), 1f / 60f);

            updateFacing();

            activeBoost = resolveActiveBoost();
            float boost = activeBoost != null ? 1f : 0f;

            float multiplier = Mathf.lerp(1f, optionalBoostIntensity, boost);
            float drillTime = getDrillTime(lastItem);
            boostWarmup = Mathf.lerpDelta(boostWarmup, boost, 0.1f);
            lastDrillSpeed = (facingAmount * multiplier * timeScale) / drillTime * efficiency;

            time += edelta() * multiplier;

            if(time >= drillTime){
                for(Tile tile : facing){
                    if(tile == null) continue;
                    Item wallDrop = tile.wallDrop();

                    if(items.total() < itemCapacity && wallDrop != null){
                        Item tierDrop = resolveTierDrop(wallDrop);
                        items.add(tierDrop, 1);
                        produced(tierDrop);
                        lastItem = tierDrop;
                    }
                }
                time %= drillTime;
            }

            if(timer(timerDump, dumpTime / timeScale)){
                dump();
            }
        }

        protected Color activeHeatColor(){
            return activeBoost != null && activeBoost.heatColor != null ? activeBoost.heatColor : heatColor;
        }

        protected Color activeSparkColor(){
            return activeBoost != null && activeBoost.sparkColor != null ? activeBoost.sparkColor : sparkColor;
        }

        protected TextureRegion activeLaser(){
            return activeBoost != null && activeBoost.laser != null ? activeBoost.laser : laserBoost;
        }
        protected TextureRegion activeLaserEnd(){
            return activeBoost != null && activeBoost.laserEnd != null ? activeBoost.laserEnd : laserEndBoost;
        }
        protected TextureRegion activeLaserCenter(){
            return activeBoost != null && activeBoost.laserCenter != null ? activeBoost.laserCenter : laserCenterBoost;
        }

        @Override
        public void draw(){
            Draw.rect(block.region, x, y);
            Draw.rect(rotation > 1 ? darktopRegion : topRegion, x, y, rotdeg());
            Draw.rect(gemRegion, x, y);
            Draw.reset();

            if(isPayload()) return;

            var dir = Geometry.d4(rotation);
            int ddx = Geometry.d4x(rotation + 1), ddy = Geometry.d4y(rotation + 1);

            for(int i = 0; i < size; i++){
                Tile face = facing[i];
                if(face != null){
                    Item drop = face.wallDrop();

                    if(drop == null) continue;
                    Point2 p = lasers[i];
                    float lx = face.worldx() - (dir.x/2f)*tilesize, ly = face.worldy() - (dir.y/2f)*tilesize;

                    float width = (laserWidth + Mathf.absin(Time.time + i*5 + (id % 9)*9, glowScl, pulseIntensity)) * warmup;

                    Draw.z(Layer.power - 1);
                    Draw.mixcol(glowColor, Mathf.absin(Time.time + i*5 + id*9, glowScl, glowIntensity));
                    if(Math.abs(p.x - face.x) + Math.abs(p.y - face.y) == 0){
                        Draw.scl(width);

                        if(boostWarmup < 0.99f){
                            Draw.alpha(1f - boostWarmup);
                            Draw.rect(laserCenter, lx, ly);
                        }

                        if(boostWarmup > 0.01f){
                            Draw.alpha(boostWarmup);
                            Draw.rect(activeLaserCenter(), lx, ly);
                        }

                        Draw.scl();
                    }else{
                        float lsx = (p.x - dir.x/2f) * tilesize, lsy = (p.y - dir.y/2f) * tilesize;

                        if(boostWarmup < 0.99f){
                            Draw.alpha(1f - boostWarmup);
                            Drawf.laser(laser, laserEnd, lsx, lsy, lx, ly, width);
                        }

                        if(boostWarmup > 0.001f){
                            Draw.alpha(boostWarmup);
                            Drawf.laser(activeLaser(), activeLaserEnd(), lsx, lsy, lx, ly, width);
                        }
                    }
                    Draw.color();
                    Draw.mixcol();

                    Draw.z(Layer.effect);
                    Lines.stroke(warmup);
                    rand.setState(i, id);
                    Color col = drop.color;
                    Color spark = Tmp.c3.set(activeSparkColor()).lerp(activeHeatColor(), boostWarmup);
                    for(int j = 0; j < sparks; j++){
                        float fin = (Time.time / sparkLife + rand.random(sparkRecurrence + 1f)) % sparkRecurrence;
                        float or = rand.range(2f);
                        Tmp.v1.set(sparkRange * fin, 0).rotate(rotdeg() + rand.range(sparkSpread));

                        Draw.color(spark, col, fin);
                        float px = Tmp.v1.x, py = Tmp.v1.y;
                        if(fin <= 1f) Lines.lineAngle(lx + px + or * ddx, ly + py + or * ddy, Angles.angle(px, py), Mathf.slope(fin) * sparkSize);
                    }
                    Draw.reset();
                }
            }

            if(glowRegion.found()){
                Draw.z(Layer.blockAdditive);
                Draw.blend(Blending.additive);
                Draw.color(Tmp.c1.set(heatColor).lerp(activeHeatColor(), boostWarmup), warmup * (heatColor.a * (1f - heatPulse + Mathf.absin(heatPulseScl, heatPulse))));
                Draw.rect(glowRegion, x, y, rotdeg());
                Draw.blend();
                Draw.color();
            }

            Draw.blend();
            Draw.reset();
        }
    }
    
}
