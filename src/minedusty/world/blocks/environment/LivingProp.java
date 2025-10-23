package minedusty.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Point2;
import arc.util.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.meta.BuildVisibility;
import minedusty.content.DustyEffects;

import static arc.Core.*;

/** Custom Prop class which has rare sprites appear at a chance. */
public class LivingProp extends Block{
	public TextureRegion rareRegion;
	public TextureRegion[] topRegions, centerRegions, shadowRegions;


	public float layer = Layer.blockProp;
	/** Rotates shadow or not specifically*/
	public boolean rotateProp = true;
	public boolean rare = true;
	public float rareChance = 1.0f;
	/** If base sprite sways with the wind. */
	public boolean swayProp = true;
	public float shadowOffset = 0;
	/** Used to fix even sized props (because they have a weird offset for god who know why) */
	public float propOffset = 0;
	public float shadowAlpha = 0.6f;

	
	public LivingProp(String name){
		this(name, 3);
	}

	public LivingProp(String name, int variants){
		super(name);
		this.variants = variants;
		hasShadow = false;
		customShadow = true;
		breakSound = Sounds.plantBreak;
		breakEffect = DustyEffects.breakLily;
		destructible = false;
		targetable = false;
		instantDeconstruct = true;
		buildVisibility = BuildVisibility.sandboxOnly;
		alwaysReplace = true;
		solid = false;
		update = true;
		clipSize = 90;
		underBullets = true;
		createRubble = false;

		// Team stuff
		drawTeamOverlay = false;
		forceTeam = Team.derelict;
		allowDerelictRepair = false; 
	}

	@Override
	public void load(){
		super.load();
		if(variants > 0){
			topRegions = new TextureRegion[variants];
			centerRegions = new TextureRegion[variants];
			shadowRegions = new TextureRegion[variants];

			for(int i = 0; i < variants; i++){
				topRegions[i] = atlas.find(name + "-top" + (i + 1));
				centerRegions[i] = atlas.find(name + "-center" + (i + 1));
				shadowRegions[i] = atlas.find(name + "-shadow" + (i + 1));
			}
		}else{
			variantRegions = new TextureRegion[1];
			variantRegions[0] = atlas.find(name);
			topRegions = new TextureRegion[1];
			topRegions[0] = atlas.find(name + "-top");
			centerRegions = new TextureRegion[1];
			centerRegions[0] = atlas.find(name + "-center");
			shadowRegions = new TextureRegion[1];
			shadowRegions[0] = atlas.find(name + "-shadow");
		}
		rareRegion = atlas.find(name + "-rare");
		region = variantRegions[0];
	}
	
	static Rand rand = new Rand();

	@Override
	public void drawBase(Tile tile){
		rand.setSeed(tile.pos());
		int variation = Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, variantRegions.length - 1));

		float x = tile.worldx(), y = tile.worldy(),
		w = region.width * region.scl(),
		h = region.height * region.scl(),
		rot = Mathf.sin(Time.time + x, 50f, 0.5f) + Mathf.sin(Time.time - y, 65f, 0.9f) + Mathf.sin(Time.time + y - x, 85f, 0.9f),
		scl = 30f, mag = 0.2f;

		//main sprite
		Draw.z(layer);
		
		if(swayProp){
			Draw.rectv(variantRegions[variation], x + propOffset, y + propOffset, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*2 + Time.time, scl, mag) + Mathf.sin(vec.x*2 - Time.time, 70, 0.8f),
				Mathf.cos(vec.x*2 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*2 - Time.time, 50, 0.2f)
				));
		}else{
			Draw.rect(variantRegions[variation], x + propOffset, y + propOffset);
		}
		
		//shadow sprite | if they have one (which they should)
		if(shadowRegions[variation].found()){
			Draw.z(Layer.block - 1);
			if (rotateProp == false){
				rot = 0;
			}
			Draw.color(0f, 0f, 0f, shadowAlpha);
			Draw.rectv(shadowRegions[variation], x + shadowOffset, y + shadowOffset, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*3 + Time.time, scl, mag) + Mathf.sin(vec.x*3 - Time.time, 70, 0.8f),
				Mathf.cos(vec.x*3 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time, 50, 0.2f)
				));
			Draw.color();
		}

		boolean useRare = rare && rand.chance(rareChance);
		//center sprite
		TextureRegion centertoDraw = useRare ? rareRegion : centerRegions[variation];
		if(centertoDraw.found()){
			Draw.z(layer + 1);
			Draw.rectv(centertoDraw, x + propOffset, y + propOffset, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*3 + Time.time, scl, mag) + Mathf.sin(vec.x*2 - Time.time, 70, 0.8f),
				Mathf.cos(vec.x*3 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*2 - Time.time, 50, 0.2f)
				));
		}

		//top sprite | if they have one //Should I make them move funny?
		if(topRegions[variation].found()){
			Draw.z(layer + 2);
			Draw.rect(topRegions[variation], x + propOffset, y + propOffset);
		}
	}

	public class LivingPropBuild extends Building{
		@Override
		public boolean damaged() {
			return false;
		}

		@Override
		public void damage(float damage) {
		}
	}
}
