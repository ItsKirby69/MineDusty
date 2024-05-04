package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Point2;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

import static arc.Core.atlas;

import arc.Core;

public class LivingTreeBlock extends TreeBlock{

	public TextureRegion[] variantRegions;
	public TextureRegion[] topRegions;
	public TextureRegion[] centerRegions;
	public TextureRegion[] shadowRegions;

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
			variantRegions = new TextureRegion[variants];
			topRegions = new TextureRegion[variants];
			centerRegions = new TextureRegion[variants];
			shadowRegions = new TextureRegion[variants];

			for(int i = 0; i < variants; i++){
				variantRegions[i] = atlas.find(name + (i + 1));
				topRegions[i] = atlas.find(name + "-top" + (i + 1));
				centerRegions[i] = atlas.find(name + "-center" + (i + 1));
				shadowRegions[i] = atlas.find(name + "-shadow" + (i + 1));
			}
		}else{
			variantRegions = new TextureRegion[1];
			variantRegions[0] = atlas.find(name);
			topRegions = new TextureRegion[1];
			topRegions[0] = atlas.find(name + "-bot");
			centerRegions = new TextureRegion[1];
			centerRegions[0] = atlas.find(name + "-center");
			shadowRegions = new TextureRegion[1];
			shadowRegions[0] = atlas.find(name + "-shadow");
		}
		region = variantRegions[0];
	}

	@Override
	public void drawBase(Tile tile) {
		Mathf.rand.setSeed(tile.pos());
		int sprite = variant(tile.x, tile.y);

		float x = tile.worldx(), 
		y = tile.worldy(),
		rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time + x, 50f, 0.5f) + Mathf.sin(Time.time - y, 65f, 0.9f) + Mathf.sin(Time.time + y - x, 85f, 0.9f),
		w = variantRegions[sprite].width * variantRegions[sprite].scl(),
		h = variantRegions[sprite].height * variantRegions[sprite].scl(),
        scl = 30f, mag = 0.2f;
		
		// Draws main stem/log of tree
		Draw.z(Layer.power + 1);
		Draw.rectv(variantRegions[sprite], x, y, w, h, rot, vec -> vec.add(
			Mathf.sin(vec.y*3 + Time.time, scl, mag) + Mathf.sin(vec.x*3 - Time.time, 70, 0.8f),
			Mathf.cos(vec.x*3 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time, 50, 0.2f)
			));
		
		//shadow
		if (shadowRegions[sprite].found()) {
			Draw.rect(shadowRegions[sprite], tile.worldx(), tile.worldy(), rot);
		}
		//top leaves
		if (topRegions[sprite].found()) {
			Draw.z(Layer.power + 1);
			Draw.rect(topRegions[sprite], tile.worldx(), tile.worldy(), rot);
		}
		//center leaves
		if (centerRegions[sprite].found()) {
			Draw.z(Layer.power + 1);
			Draw.rectv(centerRegions[sprite], x, y, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*2 + Time.time, scl, mag) + Mathf.sin(vec.x*2 - Time.time, 70, 0.8f),
				Mathf.cos(vec.x*2 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*2 - Time.time, 50, 0.2f)
				));
			//Draw.rect(centerRegion, tile.worldx(), tile.worldy(), rot);
		}
	}

	public int variant(int x, int y){
		return Mathf.randomSeed(Point2.pack(x, y), 0, Math.max(0, variantRegions.length - 1));
	}

    @Override
    public void drawShadow(Tile tile){}
}
