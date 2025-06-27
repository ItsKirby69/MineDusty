package minedusty.world.blocks.environment;

import static arc.Core.settings;

import arc.audio.Sound;
import arc.graphics.*;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Blocks;
import mindustry.entities.*;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.*;
import minedusty.content.DustyEffects;
//import mindustry.Vars.*;

public class TileEffect extends Floor{

	public Block parent = Blocks.air;
	public Color effectColor = Pal.vent;
	public float effectSpacing = 80f;
	public float chance = 0.4f;

	public Effect effect = DustyEffects.airBubble;
	public Sound soundEffect = Sounds.none;
	
	
	public TileEffect(String name){
		super(name);
		variants = 3;
	}

	@Override
	public void drawBase(Tile tile){
		parent.drawBase(tile);

		Mathf.rand.setSeed(tile.pos());
		Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
	}
	
	//updates
	@Override
	public boolean updateRender(Tile tile){
		return true;
	}

	//renders per frame? Checks if air block, randomizes and applies spacing
	@Override
	public void renderUpdate(UpdateRenderState state){
		if(state.tile.block() == Blocks.air) {
			if(state.data == 0f) {
				state.data = Mathf.random(effectSpacing);
			}
			state.data += Time.delta;

			if(state.data >= effectSpacing){
				if(Mathf.random() < chance){
					effect.at(state.tile.worldx(), state.tile.worldy(), effectColor);
					soundEffect.at(state.tile.worldx(), state.tile.worldy(), Mathf.random(0.8f, 1.2f), settings.getInt("dusty-sfx-volume") / 100f);  // Play sound effect
				}
				state.data = 0f;
			}
		}
	}
}

