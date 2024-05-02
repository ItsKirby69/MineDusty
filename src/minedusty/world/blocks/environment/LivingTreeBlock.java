package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

import static arc.Core.atlas;

public class LivingTreeBlock extends TreeBlock{
	public int sprites = 2;

	public TextureRegion topRegion, centerRegion, shadowRegion;

    public LivingTreeBlock(String name){
        super(name);
		hasShadow = true;
        customShadow = true;
		solid = true;
		clipSize = 90f;
		breakSound = Sounds.plantBreak;
    }

	@Override
	public void load(){
		super.load();
		topRegion = atlas.find(name + "-top");
		centerRegion = atlas.find(name + "-center");
		shadowRegion = atlas.find(name + "-shadow");
	}

	@Override
	public void drawBase(Tile tile) {

		//int sprite = Mathf.randomSeed(id, 0, sprites - 1);

		float x = tile.worldx(), 
		y = tile.worldy(),
		rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time + x, 50f, 0.5f) + Mathf.sin(Time.time - y, 65f, 0.9f) + Mathf.sin(Time.time + y - x, 85f, 0.9f),
		w = region.width * region.scl(), 
		h = region.height * region.scl(),
        scl = 30f, mag = 0.2f;
		
		// Draws main stem/log of tree
		Draw.z(Layer.power + 1);
		Draw.rectv(region, x, y, w, h, rot, vec -> vec.add(
			Mathf.sin(vec.y*3 + Time.time, scl, mag) + Mathf.sin(vec.x*3 - Time.time, 70, 0.8f),
			Mathf.cos(vec.x*3 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time, 50, 0.2f)
			));
		
		//shadow
		if (shadowRegion.found()) {
			Draw.rect(shadowRegion, tile.worldx(), tile.worldy(), rot);
		}
		//top leaves
		if (topRegion.found()) {
			Draw.z(Layer.power + 1);
			Draw.rect(topRegion, tile.worldx(), tile.worldy(), rot);
		}
		//center leaves
		if (centerRegion.found()) {
			Draw.z(Layer.power + 2);
			Draw.rectv(centerRegion, x, y, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*2 + Time.time, scl, mag) + Mathf.sin(vec.x*2 - Time.time, 70, 0.8f),
				Mathf.cos(vec.x*2 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*2 - Time.time, 50, 0.2f)
				));
			//Draw.rect(centerRegion, tile.worldx(), tile.worldy(), rot);
		}
	}
    @Override
    public void drawShadow(Tile tile){}
}
