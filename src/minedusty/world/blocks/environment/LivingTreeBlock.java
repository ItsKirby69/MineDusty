package minedusty.world.blocks.environment;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.entities.*;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.meta.BuildVisibility;
import minedusty.content.*;
import minedusty.graphics.DrawPseudo3D;
import minedusty.utils.EffectHelper;

import static arc.Core.*;
import static mindustry.Vars.tilesize;
import static minedusty.utils.EffectHelper.*;

import arc.Core;

/** Unique TreeBlock class which renders trees having many layers. If toggled, trees will fade when nearby as well as shower falling leaves. */
public class LivingTreeBlock extends Block{
	public TextureRegion trunkShadow;
	public TextureRegion[] trunkRegions, topRegions, middleRegions, centerRegions, backRegions, tallshadRegions, shadowRegions;
	public float shadowOffset = -4f;

	public float baseLayer = Layer.legUnit + 2.5f;
	/** Rotates tree shadow or not. Useful for tall trees with elongated shadows. Ex: Pine trees*/
	public boolean rotateShadow = true;
	/** Massive trees toggle (mainly layering fixes*/
	public boolean tallTree = false;
	/** For fading when near tree. If setting is enabled*/
	public float fadeStart = 50f;
	public float fadeEnd = 15f;
	/** Effects for falling leaves */
	public Effect effect = EffectHelper.fallingLeaves("tree-prop3");
	public float effectRange = 6f;

	public Effect treeBreakEffect;
	public Effect stumpBreakEffect = stumpBreakEffect(90f, 15, 2, "minedusty-tree-bark", 3f, 7f);

	public LivingTreeBlock(String name){
		this(name, 3, "#000000");
	}
	
	public LivingTreeBlock(String name, int variants, String mapcolor){
		super(name);
		this.variants = variants;
		customShadow = true;
		solid = true;
		size = 3;
		clipSize = 120;
		health = size * 500;
		update = true;
		breakSound = destroySound = DustSounds.destroyTree;
		mapColor = Color.valueOf(mapcolor);
		// Temporary setup for effects
		destroyEffect = breakEffect = stumpBreakEffect;
		treeBreakEffect = colorEffect(treeBreakEffect(120f, 45, 4, "minedusty-tree-prop", 3.2f, 23f, Layer.blockOver), mapColor);
		buildVisibility = BuildVisibility.sandboxOnly;
		destructible = true;
		breakable = false;
		alwaysReplace = false;
		createRubble = false;
		targetable = false;
		forceTeam = Team.derelict;
		drawTeamOverlay = false;
		drawCracks = false;
		allowDerelictRepair = false; // Sandbox mode weirdly has the repair available? Vanilla bug
	}

	@Override
	public boolean canBreak(Tile tile){
		// breakable in sandBox or in-game editor
		return Vars.state.rules.infiniteResources || Vars.state.rules.editor;
	}

	@Override
	protected TextureRegion[] icons(){
		return new TextureRegion[]{
			TexExists(backRegions, region),
			region,
			TexExists(centerRegions),
			TexExists(middleRegions),
			TexExists(topRegions)
		};
	}

	public TextureRegion TexExists(TextureRegion[] texs){
		return texs != null && texs.length > 0 && texs[0] != null && texs[0].found() ? texs[0] : Core.atlas.find("clear");
	}

	public TextureRegion TexExists(TextureRegion[] texs, TextureRegion backup){
		return texs != null && texs.length > 0 && texs[0] != null && texs[0].found() ? texs[0] : backup;
	}

	@Override
    public int minimapColor(Tile tile){
        return mapColor.rgba();
    }

	@Override
	public void load(){
		super.load();
		if(variants > 0){
			trunkRegions = new TextureRegion[variants];
			topRegions = new TextureRegion[variants];
			middleRegions = new TextureRegion[variants];
			tallshadRegions = new TextureRegion[variants];
			centerRegions = new TextureRegion[variants];
			backRegions = new TextureRegion[variants];
			shadowRegions = new TextureRegion[variants];

			for(int i = 0; i < variants; i++){
				trunkRegions[i] = atlas.find(name + "-trunk" + (i + 1));
				topRegions[i] = atlas.find(name + "-top" + (i + 1));
				middleRegions[i] = atlas.find(name + "-middle" + (i + 1));
				tallshadRegions[i] = atlas.find(name + "-tallshadow" + (i + 1));
				centerRegions[i] = atlas.find(name + "-center" + (i + 1));
				backRegions[i] = atlas.find(name + "-back" + (i + 1));
				shadowRegions[i] = atlas.find(name + "-shadow" + (i + 1));
			}
		}else{
			variantRegions = new TextureRegion[1];
			variantRegions[0] = atlas.find(name);
			trunkRegions = new TextureRegion[1];
			trunkRegions[0] = atlas.find(name + "-trunk");
			topRegions = new TextureRegion[1];
			topRegions[0] = atlas.find(name + "-top");
			middleRegions = new TextureRegion[1];
			middleRegions[0] = atlas.find(name + "-middle");
			tallshadRegions = new TextureRegion[1];
			tallshadRegions[0] = atlas.find(name + "-tallshadow");
			centerRegions = new TextureRegion[1];
			centerRegions[0] = atlas.find(name + "-center");
			backRegions = new TextureRegion[1];
			backRegions[0] = atlas.find(name + "-back");
			shadowRegions = new TextureRegion[1];
			shadowRegions[0] = atlas.find(name + "-shadow");
		}
		region = variantRegions[0];
		trunkShadow = atlas.find("circle-shadow");
	}

	@Override
	public void init() {
		super.init();
	}

	static Rand rand = new Rand();

	@Override
	public void drawBase(Tile tile) {
		float fade = 1f;
		int variation = Mathf.randomSeed(Point2.pack(tile.x, tile.y), 0, Math.max(0, variantRegions.length - 1));

		float timeFactor = tallTree ? 0.3f : 1f;
		if (settings.getBool("dusty-fade-enabled") && (Vars.player.unit() != null && !Vars.player.unit().dead())){
			float fadeOpacity = settings.getInt("dusty-fade-opacity") / 100f;
			float dst;
			float dstMulti = settings.getInt("dusty-fade-dist-multi");

			if(settings.getBool("dusty-toggle-mouse-fade")){
				Vec2 mouse = Core.input.mouseWorld(Core.input.mouseX(), Core.input.mouseY());
				dst = Mathf.dst(mouse.x, mouse.y, tile.worldx(), tile.worldy());
			} else {
				dst = Mathf.dst(Vars.player.unit().x, Vars.player.unit().y, tile.worldx(), tile.worldy());
			}
			fade = Mathf.clamp((dst - (fadeEnd * dstMulti)) / ((fadeStart * dstMulti) - (fadeEnd * dstMulti)), fadeOpacity, 1f);
		}

		rand.setSeed(tile.pos());

		float x = tile.worldx(), y = tile.worldy(),
		rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time* timeFactor + x, 50f, 0.5f) + Mathf.sin(Time.time* timeFactor - y, 65f, 0.9f) + Mathf.sin(Time.time* timeFactor + y - x, 85f, 0.9f),
		rotStatic = Mathf.randomSeed(tile.pos(), 0, 4) * 90,
		w = region.width * region.scl(), h = region.height * region.scl(),
		scl = 30f, mag = 0.2f;

		// Burn color WIP
		/*
		float intensity = ((LivingTreeBlockBuild)tile.build).burnIntensity;
		Color result = Color.white.cpy().lerp(Color.valueOf("#2c2624"), intensity);
		float[] hsv = result.toHsv(new float[3]);
		hsv[1] *= Mathf.clamp(1f - intensity * 3f, 0f, 1f); //saturation
		hsv[2] *= (1f - intensity * 0.3f); //darken
		result.fromHsv(hsv[0], hsv[1], hsv[2]);

		Draw.color(result); 
		*/

		if(!((LivingTreeBlockBuild)tile.build).stump){
			Draw.alpha(fade);
			Draw.z(baseLayer);
			Draw.rectv(variantRegions[variation], x, y, w, h, rot, vec -> vec.add(
				Mathf.sin(vec.y*3 + Time.time* timeFactor, scl, mag) + Mathf.sin(vec.x*3 - Time.time* timeFactor, 70, 0.8f),
				Mathf.cos(vec.x*3 + Time.time* timeFactor + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time* timeFactor, 50, 0.2f)
				));

			// Back leaves, behind base layer 🗣🗣🗣
			if (backRegions[variation].found()) {
				Draw.z(baseLayer - 1f);
				Draw.alpha(fade);
				Draw.rect(backRegions[variation], x, y, rot);
			}

			// Center leaves. Also just means those leaves from the edgemost of the tree branches.
			if (centerRegions[variation].found()) {
				float height = 0.0015f * size;
				float drawX = DrawPseudo3D.xHeight(x, height);
				float drawY = DrawPseudo3D.yHeight(y, height);

				Draw.z(baseLayer + 1);
				Draw.alpha(fade);
				Draw.rectv(centerRegions[variation], drawX, drawY, w, h, rot, vec -> vec.add(
					Mathf.sin(vec.y*2 + Time.time * timeFactor, scl, mag) + Mathf.sin(vec.x*2 - Time.time* timeFactor, 70, 0.8f),
					Mathf.cos(vec.x*2 + Time.time * timeFactor + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*2 - Time.time* timeFactor, 50, 0.2f)
					));
			}

			// Middle leaves in between Center leaves and the Top leaves.
			if (middleRegions[variation].found()) {
				float height = 0.002f * size;
				float drawX = DrawPseudo3D.xHeight(x, height);
				float drawY = DrawPseudo3D.yHeight(y, height);

				Draw.z(tallTree ? Layer.flyingUnitLow + 1.5f : baseLayer + 2f);
				Draw.alpha(fade);
				Draw.rectv(middleRegions[variation], drawX, drawY, w, h, rot, vec -> vec.add(
					Mathf.sin(vec.y*2 + Time.time * timeFactor, scl, mag) + Mathf.sin(vec.x*2 - Time.time* timeFactor, 55, 0.9f),
					Mathf.cos(vec.x*2 + Time.time * timeFactor + 8, scl + 6f, mag * 1.0f) + Mathf.sin(vec.y*2 - Time.time* timeFactor, 50, 0.2f)
					));
			}

			// Shadow mask for the massive trees.
			if (tallshadRegions[variation].found()) {			
				Draw.z(Layer.flyingUnitLow + 1f);
				Draw.alpha(1f);
				Draw.rectv(tallshadRegions[variation], x, y, w, h, rot, vec -> vec.add(
					Mathf.sin(vec.y*3 + Time.time* timeFactor, scl, mag) + Mathf.sin(vec.x*3 - Time.time* timeFactor, 70, 0.8f),
					Mathf.cos(vec.x*3 + Time.time* timeFactor + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time* timeFactor, 50, 0.2f)
					));
			}

			// Top leaves. Massive trees would have this above flying units.
			if (topRegions[variation].found()) {
				float height = 0.003f * size;
				float drawX = DrawPseudo3D.xHeight(x, height);
				float drawY = DrawPseudo3D.yHeight(y, height);

				Draw.z(tallTree ? Layer.flyingUnitLow + 2f : baseLayer + 4f);
				Draw.alpha(fade);
				Draw.rectv(topRegions[variation], drawX, drawY, w, h, rot, vec -> vec.add(
					Mathf.sin(vec.y*2 + Time.time* timeFactor, scl, mag) + Mathf.sin(vec.x*2 - Time.time* timeFactor, 70, 0.8f),
					Mathf.cos(vec.x*2 + Time.time* timeFactor + 8, scl + 4f, mag * 1.4f) + Mathf.sin(vec.y*2 - Time.time* timeFactor, 50, 0.2f)
					));
				
			}
			Draw.color();
			Draw.alpha(1f);

			//Shadow above ground units
			if (shadowRegions[variation].found()) { 
				Draw.z(baseLayer - 2f);
				Draw.alpha(1f);
				if (rotateShadow == true){
					Draw.rect(shadowRegions[variation], x + shadowOffset, y + shadowOffset, rot);
				} else {
					Draw.rect(shadowRegions[variation], x + shadowOffset, y + shadowOffset);
				}
			}
		}

		// Trunk below base layer
		if (trunkRegions[variation].found()) {
			Draw.z(Layer.blockProp - 0.5f);
			Draw.alpha(1f);
			Draw.rect(trunkRegions[variation], x, y, rotStatic);
		}

		if(((LivingTreeBlockBuild)tile.build).stump){
			Draw.z(Layer.block - 1);
			Draw.color(0f,0f,0f, BlockRenderer.shadowColor.a - 0.2f);
			Draw.rect(trunkShadow, tile.drawx(), tile.drawy(), size * tilesize, size * tilesize);
			Draw.color();
		}

		// effects
		if(Vars.state.isPaused()) return; // Particles stack when paused for some reason
		int effectChance = settings.getInt("dusty-falling-density");
		if(settings.getBool("dusty-falling-leaves-enabled") && Mathf.chanceDelta((effectChance * 0.001f) * size * (tallTree ? 3f : 1f))){
			effect.at(
				tile.worldx() + Mathf.range(effectRange) * size,
				tile.worldy() + Mathf.range(effectRange) * size,
				mapColor
			);
		}
	}

	@Override
	public void drawShadow(Tile tile){}

	public class LivingTreeBlockBuild extends Building{
		public boolean stump = false;
		float burnIntensity = 0f;
		float stumpHealth = 0;

		@Override
		public void updateTile(){
			boolean nearFire = Groups.fire.contains(f -> Mathf.dst(f.x, f.y, x, y) < (size * Vars.tilesize * 2f));
			if(nearFire){
				damage(Time.delta * 2f);
				burnIntensity = Mathf.clamp(burnIntensity + Time.delta * 0.001f, 0f, 1f);
				if(Mathf.chanceDelta(0.008f)) {
					Tile nearby = Vars.world.tile(tile.x + Mathf.range(3),tile.y + Mathf.range(3));
					if(nearby != null && nearby.build != null) {
						Fires.create(nearby);
					}
				}
			}else{
				burnIntensity = Mathf.clamp(burnIntensity - Time.delta * 0.0005f, 0f, 1f);
			}
		}

		//prevents the tree from breaking during wave shockwave
		@Override
		public void damage(float amount){
			if(amount >= 9e7f){
				return;
			}
			super.damage(amount);
		}

		@Override
		public void created(){
			super.created();
			stumpHealth = block.health * 2;
			destroyEffect = breakEffect = stumpBreakEffect;
		}

		@Override
		public void killed(){
			if(!stump){
				stump = true;
				health = stumpHealth;
				clampHealth();

				((LivingTreeBlock)block).treeBreakEffect.at(x, y, (float)(8 * block.size));
				if (!Vars.headless) this.playDestroySound();
				return;
			}
			
			super.killed();
		}
	}
}
