package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Point2;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.meta.BuildVisibility;

import static arc.Core.*;

public class LivingProp extends Block{
	public TextureRegion[] variantRegions, topRegions, centerRegions, shadowRegions;
	public TextureRegion region;

	public float layer = Layer.blockProp;
	
	public LivingProp(String name){
		this(name, 2);
	}

	public LivingProp(String name, int variants){
		super(name);
		this.variants = variants;
		hasShadow = true;
		customShadow = true;
		breakSound = Sounds.plantBreak;
		breakEffect = Fx.breakProp;
		destructible = false;
		targetable = false;
		instantDeconstruct = true;
		buildVisibility = BuildVisibility.sandboxOnly;
		alwaysReplace = true;
		solid = false;
		update = false;
		underBullets = true;
	}

	@Override
	public void load(){
		super.load();
		if(variants > 0){
			variantRegions = new TextureRegion[variants];
			topRegions = new TextureRegion[variants];
			shadowRegions = new TextureRegion[variants];

			for(int i = 0; i < variants; i++){
				variantRegions[i] = atlas.find(name + (i + 1));
				topRegions[i] = atlas.find(name + "-top" + (i + 1));
				shadowRegions[i] = atlas.find(name + "-shadow" + (i + 1));
			}
		}else{
			variantRegions = new TextureRegion[1];
			variantRegions[0] = atlas.find(name);
			topRegions = new TextureRegion[1];
			topRegions[0] = atlas.find(name + "-top");
			shadowRegions = new TextureRegion[1];
			shadowRegions[0] = atlas.find(name + "-shadow");
		}
		region = variantRegions[0];
	}
	
	static Rand rand = new Rand();

	@Override
	public void drawBase(Tile tile){
		rand.setSeed(tile.pos());
		int sprite = variant(tile.x, tile.y);
		
		float x = tile.worldx(), y = tile.worldy(),
		w = region.width * region.scl(),
		h = region.height * region.scl(),
		rot = Mathf.sin(Time.time + x, 50f, 0.5f) + Mathf.sin(Time.time - y, 65f, 0.9f) + Mathf.sin(Time.time + y - x, 85f, 0.9f),
		scl = 30f, mag = 0.2f; //Mathf.randomSeed(tile.pos(), 0, 4)

		//main sprite
		Draw.z(layer + 2);
		Draw.rectv(variantRegions[sprite], x, y, w, h, rot, vec -> vec.add(
			Mathf.sin(vec.y*3 + Time.time, scl, mag) + Mathf.sin(vec.x*3 - Time.time, 70, 0.8f),
			Mathf.cos(vec.x*3 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time, 50, 0.2f)
			));
		
		//shadow sprite | if they have one (which they should)
		if(shadowRegions[sprite].found()){
			Draw.z(layer + 1);
			Draw.rectv(shadowRegions[sprite], x, y, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*3 + Time.time, scl, mag) + Mathf.sin(vec.x*3 - Time.time, 70, 0.8f),
				Mathf.cos(vec.x*3 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time, 50, 0.2f)
				));
		}

		//top sprite | if they have one //Should I make them move funny?
		if(topRegions[sprite].found()){
			Draw.z(layer + 3);
			Draw.rect(topRegions[sprite], x, y);
		}
	}

	public int variant(int x, int y){
		return Mathf.randomSeed(Point2.pack(x, y), 0, Math.max(0, variantRegions.length - 1));
	}
}
