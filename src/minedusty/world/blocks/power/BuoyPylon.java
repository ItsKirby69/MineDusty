package minedusty.world.blocks.power;

import static mindustry.Vars.*;

import arc.Core;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.util.*;
import mindustry.Vars;
import mindustry.core.Renderer;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.meta.*;
import minedusty.graphics.DustPalette;
import minedusty.world.meta.DustStat;

/** Custom pylon that has special properties when on water. */
// God forbid if I have to edit this class again
public class BuoyPylon extends PowerPylon{
    public TextureRegion waterRegion;
    public float waterRange = 6f;
    public float landRange = 8.5f;
    public float bobbingMag = 0.1f;

    public BuoyPylon(String name, int maxN){
        super(name, maxN);
        maxNodes = maxN;
        placeableLiquid = true;
    }

    @Override
    public void load(){
        super.load();
        waterRegion = Core.atlas.find(name + "-water");
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.powerRange);
        stats.add(DustStat.powerRanged, "[accent]" + waterRange + "[]-[accent]" + landRange, StatUnit.blocks);
    }

    @Override
    public void init(){
        super.init();

        float range = landRange >= waterRange ? landRange : waterRange;
        clipSize = Math.max(clipSize, range * tilesize);
    }

    public boolean onWater(Tile tile){
        return tile.floor().liquidMultiplier > 1f;
    }

    public float getRangeLength(Tile tile){
        return onWater(tile) ? waterRange : landRange;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Tile tile = world.tile(x, y);

        if(tile == null || !autolink) return;

        float range = onWater(tile) ? waterRange : landRange;

        Lines.stroke(1f);
        Draw.color(Pal.placing);

        Drawf.circles(x * tilesize + offset, y * tilesize + offset, range * tilesize);

        getPotentialLinks(tile, player.team(), other -> {
            Draw.color(laserColor1, Renderer.laserOpacity * 0.5f);
            drawLaser(x * tilesize + offset, y * tilesize + offset, other.x, other.y, size, other.block.size);

            Drawf.square(other.x, other.y, other.block.size * tilesize / 2f + 2f, Pal.place);
        });

        Draw.reset();
    }

    public boolean overlaps(@Nullable Tile src, @Nullable Tile other){
        if(src == null || other == null) return true;
        return Intersector.overlaps(Tmp.cr1.set(src.worldx() + offset, src.worldy() + offset, getRangeLength(other) * tilesize), Tmp.r1.setSize(size * tilesize).setCenter(other.worldx() + offset, other.worldy() + offset));
    }

    @Override
    protected void getPotentialLinks(Tile tile, Team team, Cons<Building> others){
        if(!autolink) return;

        Boolf<Building> valid = other -> other != null && other.tile != tile && other.block.connectedPower && other.power != null &&
            (other.block.outputsPower || other.block.consumesPower || other.block instanceof PowerNode) &&
            overlaps(tile.x * tilesize + offset, tile.y * tilesize + offset, other.tile, getRangeLength(tile) * tilesize) && other.team == team &&
            !graphs.contains(other.power.graph) &&
            !PowerNode.insulated(tile, other.tile) &&
            !(other instanceof PowerNodeBuild obuild && obuild.power.links.size >= ((PowerNode)obuild.block).maxNodes) &&
            !Structs.contains(Edges.getEdges(size), p -> { //do not link to adjacent buildings
                var t = world.tile(tile.x + p.x, tile.y + p.y);
                return t != null && t.build == other;
            });

        tempBuilds.clear();
        graphs.clear();

        //add conducting graphs to prevent double link
        for(var p : Edges.getEdges(size)){
            Tile other = tile.nearby(p);
            if(other != null && other.team() == team && other.build != null && other.build.power != null){
                graphs.add(other.build.power.graph);
            }
        }

        if(tile.build != null && tile.build.power != null){
            graphs.add(tile.build.power.graph);
        }

        var worldRange = getRangeLength(tile) * tilesize;
        var tree = team.data().buildingTree;
        if(tree != null){
            tree.intersect(tile.worldx() - worldRange, tile.worldy() - worldRange, worldRange * 2, worldRange * 2, build -> {
                if(valid.get(build) && !tempBuilds.contains(build)){
                    tempBuilds.add(build);
                }
            });
        }

        tempBuilds.sort((a, b) -> {
            int type = -Boolean.compare(a.block instanceof PowerNode, b.block instanceof PowerNode);
            if(type != 0) return type;
            return Float.compare(a.dst2(tile), b.dst2(tile));
        });

        returnInt = 0;

        tempBuilds.each(valid, t -> {
            if(returnInt ++ < maxNodes){
                graphs.add(t.power.graph);
                others.get(t);
            }
        });
    }

    public static boolean overlapStatic(float srcx, float srcy, Tile other, Block otherBlock, float range){
        return Intersector.overlaps(
            Tmp.cr1.set(srcx, srcy, range), 
            Tmp.r1.setCentered(
                other.worldx() + otherBlock.offset, 
                other.worldy() + otherBlock.offset,
                otherBlock.size * tilesize,
                otherBlock.size * tilesize));
    }

    public static void getNodeLinks(Tile tile, Block block, Team team, Cons<Building> others){
        Boolf<Building> valid = other -> {
            float blockRange = 0f;
            if(block instanceof BuoyPylon pylon){
                blockRange = pylon.getRangeLength(tile);
            } else if (block instanceof PowerNode node){
                blockRange = node.laserRange;
            }
            
            return other != null && other.tile != tile && other.block instanceof PowerNode node &&
                node.autolink &&
                other.power.links.size < node.maxNodes &&
                overlapStatic(other.x, other.y, tile, block, blockRange * tilesize) && other.team == team
                && !graphs.contains(other.power.graph) &&
                !PowerNode.insulated(tile, other.tile) &&
                !Structs.contains(Edges.getEdges(block.size), p -> { //do not link to adjacent buildings
                    var t = world.tile(tile.x + p.x, tile.y + p.y);
                    return t != null && t.build == other;
                });
            };

        tempBuilds.clear();
        graphs.clear();

        //add conducting graphs to prevent double link
        for(var p : Edges.getEdges(block.size)){
            Tile other = tile.nearby(p);
            if(other != null && other.team() == team && other.build != null && other.build.power != null
                && !(block.consumesPower && other.block().consumesPower && !block.outputsPower && !other.block().outputsPower)){
                graphs.add(other.build.power.graph);
            }
        }

        if(tile.build != null && tile.build.power != null){
            graphs.add(tile.build.power.graph);
        }

        var rangeWorld = maxRange * tilesize;
        var tree = team.data().buildingTree;
        if(tree != null){
            tree.intersect(tile.worldx() - rangeWorld, tile.worldy() - rangeWorld, rangeWorld * 2, rangeWorld * 2, build -> {
                if(valid.get(build) && !tempBuilds.contains(build)){
                    tempBuilds.add(build);
                }
            });
        }

        tempBuilds.sort((a, b) -> {
            int type = -Boolean.compare(a.block instanceof PowerNode, b.block instanceof PowerNode);
            if(type != 0) return type;
            return Float.compare(a.dst2(tile), b.dst2(tile));
        });

        tempBuilds.each(valid, t -> {
            graphs.add(t.power.graph);
            others.get(t);
        });
    }

    @Override
    public boolean linkValid(Building tile, Building link, boolean checkMaxNodes){
        if(tile == link || link == null || !link.block.hasPower || !link.block.connectedPower || tile.team != link.team || (sameBlockConnection && tile.block != link.block)) return false;

        // This checks if either node of two nodes which attempt to link are in range, links the nodes
        // For both tile and link range, as well as pylons and power nodes
        float tileRange = (tile.block instanceof BuoyPylon pylonA) ? pylonA.getRangeLength(tile.tileOn()) : tile.block instanceof PowerNode nodeA ? nodeA.laserRange : 0f;
        float linkRange = (link.block instanceof BuoyPylon pylonB) ? pylonB.getRangeLength(link.tileOn()) : link.block instanceof PowerNode nodeB ? nodeB.laserRange : 0f;

        boolean eitherRange = overlaps(tile, link, tileRange * tilesize) || overlaps (link, tile, linkRange * tilesize);
        if(eitherRange){
            if(checkMaxNodes && link.block instanceof PowerNode node){
                return link.power.links.size < node.maxNodes || link.power.links.contains(tile.pos());
            }
            return true;
        }
        return false;
    }

    public class BuoyPylonBuild extends PowerNodeBuild{
        public float effectiveRange;

        @Override
        public void created(){
            super.created();
            updateEffectiveRange();
        }

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();
            updateEffectiveRange();
        }    

        void updateEffectiveRange(){
            effectiveRange = (tile.floor().liquidMultiplier > 1f) ? waterRange : landRange;
        }
        public float range(){
            return effectiveRange;
        }

        @Override
        public void draw() {
            if(effectiveRange == waterRange){
                float x = tile.worldx(), y = tile.worldy(),
                w = waterRegion.width * waterRegion.scl(), h = waterRegion.height * waterRegion.scl();
                float bob = 0.99f + Mathf.sin(Time.time / 40f + x * 0.1f + y * 0.1f) * bobbingMag;
                
                Draw.rect(waterRegion, x, y, w * bob, h * bob);
            } else {
                Draw.rect(block.region, x, y, drawrot());
            }
            drawTeamTop();

            // normal draw code
            if (!Mathf.zero(Renderer.laserOpacity) && !isPayload() && team != Team.derelict) {
                Draw.z(70.0F);
                setupColor(power.graph.getSatisfaction());

                for(int i = 0; i < power.links.size; ++i) {
                    Building link = Vars.world.build(power.links.get(i));
                    if (linkValid(this, link) && (!(link.block instanceof PowerNode) || link.id < id)) {
                        drawLaser(x, y, link.x, link.y, size, link.block.size);
                    }
                }

                Draw.reset();
            }
        }

        @Override
        public void drawSelect(){
            this.block.drawOverlay(this.x, this.y, this.rotation);
            if(!drawRange) return;
            Lines.stroke(1f);
            Draw.color(Pal.accent);
            Drawf.circles(x, y, effectiveRange * tilesize);
            Draw.reset();
        }

        @Override
        public void drawConfigure(){

            Drawf.circles(x, y, tile.block().size * tilesize / 2f + 1f + Mathf.absin(Time.time, 4f, 1f));

            if(drawRange){
                Drawf.circles(x, y, range() * tilesize);

                for(int x = (int)(tile.x - range() - 2); x <= tile.x + range() + 2; x++){
                    for(int y = (int)(tile.y - range() - 2); y <= tile.y + range() + 2; y++){
                        Building link = world.build(x, y);

                        if(link != this && linkValid(this, link, false)){
                            boolean linked = linked(link);

                            if(linked){
                                Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f, Pal.place);
                            }
                        }
                    }
                }

                Draw.reset();
            }else{
                power.links.each(i -> {
                    var link = world.build(i);
                    if(link != null && linkValid(this, link, false)){
                        Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f, Pal.place);
                    }
                });
            }
        }
    }
}
