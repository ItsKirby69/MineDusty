package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Point2;
import arc.util.*;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.meta.BuildVisibility;

import static arc.Core.*;

//this and all my other classes for custom props are probably really bad
public class LivingTreeBlock extends Block{
	public TextureRegion[] topRegions, centerRegions, backRegions, shadowRegions;
	public float shadowOffset = -4f;
	public float layer = Layer.power;
	/** Rotates tree shadow or not. Useful for tall trees with elongated shadows. Ex: Pine trees*/
	public boolean rotateShadow = true;

	public LivingTreeBlock(String name){
		this(name, 3);
	}
	
    public LivingTreeBlock(String name, int variants){
        super(name);
		this.variants = variants;
        customShadow = true;
		solid = true;
		clipSize = 120;
		update = true;
		breakSound = Sounds.plantBreak;
		buildVisibility = BuildVisibility.sandboxOnly;
		destructible = true;
		health = size * 1200;
		drawTeamOverlay = false;
		createRubble = false;
		targetable = false;
    }

	@Override
	public void load(){
		super.load();
		if(variants > 0){
			topRegions = new TextureRegion[variants];
			centerRegions = new TextureRegion[variants];
			backRegions = new TextureRegion[variants];
			shadowRegions = new TextureRegion[variants];

			for(int i = 0; i < variants; i++){
				topRegions[i] = atlas.find(name + "-top" + (i + 1));
				centerRegions[i] = atlas.find(name + "-center" + (i + 1));
				backRegions[i] = atlas.find(name + "-back" + (i + 1));
				shadowRegions[i] = atlas.find(name + "-shadow" + (i + 1));
			}
		}else{
			variantRegions = new TextureRegion[1];
			variantRegions[0] = atlas.find(name);
			topRegions = new TextureRegion[1];
			topRegions[0] = atlas.find(name + "-top");
			centerRegions = new TextureRegion[1];
			centerRegions[0] = atlas.find(name + "-center");
			backRegions = new TextureRegion[1];
			backRegions[0] = atlas.find(name + "-back");
			shadowRegions = new TextureRegion[1];
			shadowRegions[0] = atlas.find(name + "-shadow");
		}
		region = variantRegions[0];
	}

	@Override
	public void init() {
		super.init();
		hasShadow = true;
	}

	static Rand rand = new Rand();

	@Override
	public void drawBase(Tile tile) {

		rand.setSeed(tile.pos());

		float x = tile.worldx(), y = tile.worldy(),
		rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time + x, 50f, 0.5f) + Mathf.sin(Time.time - y, 65f, 0.9f) + Mathf.sin(Time.time + y - x, 85f, 0.9f),
		w = region.width * region.scl(), h = region.height * region.scl(),
        scl = 30f, mag = 0.2f;
		
		// Draws main stem/log of tree
		Draw.alpha(1);
		Draw.z(layer + 2);
		Draw.rectv(variantRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, variantRegions.length - 1))], x, y, w, h, rot, vec -> vec.add(
			Mathf.sin(vec.y*3 + Time.time, scl, mag) + Mathf.sin(vec.x*3 - Time.time, 70, 0.8f),
			Mathf.cos(vec.x*3 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time, 50, 0.2f)
			));
		
		//shadow TODO: i think they should be moving as well...
		if (shadowRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, shadowRegions.length - 1))].found()) { 
			Draw.z(layer);
			if (rotateShadow == true){
				Draw.rect(shadowRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, shadowRegions.length - 1))], x + shadowOffset, y + shadowOffset, rot);
			} else {
				Draw.rect(shadowRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, shadowRegions.length - 1))], x + shadowOffset, y + shadowOffset);
			}
		}
		
		//top leaves
		if (topRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, topRegions.length - 1))].found()) {
			Draw.z(layer + 4);
			Draw.rectv(topRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, topRegions.length - 1))], x, y, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*2 + Time.time, scl, mag) + Mathf.sin(vec.x*2 - Time.time, 70, 0.8f),
				Mathf.cos(vec.x*2 + Time.time + 8, scl + 4f, mag * 1.0f) + Mathf.sin(vec.y*2 - Time.time, 50, 0.2f)
				));
		}
		//center leaves
		if (centerRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, centerRegions.length - 1))].found()) {
			Draw.z(layer + 3);
			Draw.rectv(centerRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, centerRegions.length - 1))], x, y, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*2 + Time.time, scl, mag) + Mathf.sin(vec.x*2 - Time.time, 70, 0.8f),
				Mathf.cos(vec.x*2 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*2 - Time.time, 50, 0.2f)
				));
		//them back regions 🗣🗣🗣
		if (backRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, backRegions.length - 1))].found()) {
			Draw.z(layer + 1);
			Draw.rect(backRegions[Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, backRegions.length - 1))], x, y, rot);
		}}
	}

    @Override
    public void drawShadow(Tile tile){}
}
