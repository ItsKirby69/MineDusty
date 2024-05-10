package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Point2;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

import static arc.Core.*;

public class LivingTreeBlock extends TreeBlock{

	public TextureRegion[] topRegions, centerRegions, backRegions, shadowRegions;
	
	public float layer = Layer.power;

	public LivingTreeBlock(String name){
		this(name, 3);
	}

    public LivingTreeBlock(String name, int variants){
        super(name);
		this.variants = variants;
		hasShadow = true;
        customShadow = true;
		solid = true;
		clipSize = 90f;
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

	static Rand rand = new Rand();

	@Override
	public void drawBase(Tile tile) {
		rand.setSeed(tile.pos());
		int sprite = variant(tile.x, tile.y);

		float x = tile.worldx(), 
		y = tile.worldy(),
		rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time + x, 50f, 0.5f) + Mathf.sin(Time.time - y, 65f, 0.9f) + Mathf.sin(Time.time + y - x, 85f, 0.9f),
		w = region.width * region.scl(),
		h = region.height * region.scl(),
        scl = 30f, mag = 0.2f;
		
		// Draws main stem/log of tree
		Draw.z(layer + 3);
		Draw.rectv(variantRegions[sprite], x, y, w, h, rot, vec -> vec.add(
			Mathf.sin(vec.y*3 + Time.time, scl, mag) + Mathf.sin(vec.x*3 - Time.time, 70, 0.8f),
			Mathf.cos(vec.x*3 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time, 50, 0.2f)
			));
		
		//shadow
		if (shadowRegions[sprite].found()) {
			Draw.z(layer + 1);
			Draw.rect(shadowRegions[sprite], x + shadowOffset, y + shadowOffset, rot);
		}
		//top leaves
		if (topRegions[sprite].found()) {
			Draw.z(layer + 4);
			Draw.rect(topRegions[sprite], x, y, rot);
		}
		//center leaves
		if (centerRegions[sprite].found()) {
			Draw.z(layer + 5);
			Draw.rectv(centerRegions[sprite], x, y, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*2 + Time.time, scl, mag) + Mathf.sin(vec.x*2 - Time.time, 70, 0.8f),
				Mathf.cos(vec.x*2 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*2 - Time.time, 50, 0.2f)
				));
		//them back regions :speaking_head: :speaking_head: :speaking_head: 
		if (backRegions[sprite].found()) {
			Draw.z(layer + 2);
			Draw.rect(backRegions[sprite], x, y, rot);
		}}
	}

	public int variant(int x, int y){
		return Mathf.randomSeed(Point2.pack(x, y), 0, Math.max(0, variantRegions.length - 1));
	}

    @Override
    public void drawShadow(Tile tile){}
}
