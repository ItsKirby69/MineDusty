package minedusty.planets;

import arc.files.Fi;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.PixmapIO;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.util.Log;
import arc.util.noise.Ridged;
import mindustry.Vars;
import mindustry.game.Schematics;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;
import minedusty.utils.Simplex;

/* Useful notes from other gens (don't quote me) --
 * serpulo gens height as usual then uses Blocks arr which
 * has gradients of block pallets (water -> land tiles)
 * and takes mapcolor of block in setColor method
 */

public class TerraPlanetGenerator extends PlanetGenerator{
	float rotation = 50; // degrees to rotate the planet

	Color  out = new Color(), debugColor = Color.valueOf("#ff00ff"),

	divine1 = Color.valueOf("#c32121"), divine2 = Color.valueOf("#e87694"),
	divine3 = Color.valueOf("#5e1010"), divine4 = Color.valueOf("#fcf5f9"),
	divine5 = Color.valueOf("#ffcfe0"),
	valley1 = Color.valueOf("#60b243"), valley2 = Color.valueOf("#478e2d"),
	peaks1 = Color.valueOf("#1c873f"), peaks2 = Color.valueOf("#6db58d"),
	
	Iced1 = Color.valueOf("#afe0cb"), Iced2 = Color.valueOf("#ceebe5"),
	
	oceanColor1 = Color.valueOf("#47ace6"), oceanColor2 = Color.valueOf("#32608d"),
	poleColor1 = Color.valueOf("#d4ecfa"), poleColor2 = Color.valueOf("#ffffff"), 
	poleOcean1 = Color.valueOf("#91a6f9"), poleOcean2 = Color.valueOf("#bfcbf8"),
	stones1 = Color.valueOf("#53516e"), stones2 = Color.valueOf("#afb5c4"),
	basalts1 = Color.valueOf("#415163"), basalts2 = Color.valueOf("#757f8a"),
	beaches1 = Color.valueOf("#ead195"), beaches2 = Color.valueOf("#b48f55"),
	IcedBeach1 = Color.valueOf("#e3e3ff"), IcedBeach2 = Color.valueOf("#C2BFFB"),

	desert1 = Color.valueOf("#6b604d"), desert2 = Color.valueOf("#b8a78c");
	
	//Color[] DivineGrad = {divine3, divine3, divine1, divine1, divine2, divine4, divine5, divine2};
	Color[] DivineGrad = {divine1, divine1, divine5, divine5, divine4, divine2, divine1, divine3};
	//Color[] DivineGrad = {divine2, divine5, divine4, divine2, divine1, divine3, divine3};
	
	{
		baseSeed = 2;
		defaultLoadout = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZWBJzi9KZeBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQzs3MS00pLS6p1AWp1c1LLS4BqmEEISABAPVFEvQ=");
	}
	// Preview highmap
	{
		int size = 256*4;
		Pixmap pixmap = new Pixmap(size, size);
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				
				float nx = (x / (float)(size - 1)) * 2f - 1f;
				float ny = (y / (float)(size - 1)) * 2f - 1f;
				Vec3 pos = new Vec3(nx, ny, 1f).nor();

				float h = testHeight(pos);
				float g = Mathf.clamp(h);
				Color c = new Color(g, g, g, 1f);

				pixmap.setRaw(x, y, c.rgba8888());
			}
		}

		Fi ouputt = Vars.tmpDirectory.child("terra-height.png");
		PixmapIO.writePng(ouputt, pixmap);
		pixmap.dispose();
		Log.info("Saved terra-height.png to " + ouputt.absolutePath());
	}
	
	@Override
	public void generateSector(Sector sector){
		//nothing yet
	}

	/** Rotates the whole planet. In degrees of course */
	Vec3 rotateY(Vec3 position, float degrees) {
		float radians = Mathf.degRad * degrees;
		float cos = Mathf.cos(radians);
		float sin = Mathf.sin(radians);
		
		return new Vec3(
			position.x * cos - position.z * sin,
			position.y,
			position.x * sin + position.z * cos
		);
	}

	public float testHeight(Vec3 position) {
		position = rotateY(position, 30);
		Vec3 pos = new Vec3(position).scl(4.5f);
		float divineVoronoi = Simplex.noise3d(baseSeed-2, 2, 0.2, 0.13, pos.x, pos.y, pos.z);
		float b = divineVoronoi;
		
		float base = 0f;
		base += b;

		return base;
	}
	//  PlanetDialog.debugSelect = true
	//this is really really really really really really really really really jank (i think)
	@Override
	public Color getColor(Vec3 position) {
		position = rotateY(position, rotation);
		Vec3 pos = new Vec3(position).scl(4.5f);
		
		float height = rawHeight(position);
		float depth = Simplex.noise3d(baseSeed, 4, 0.7f, 0.8f, position.x, position.y, position.z);
		float mountains = Ridged.noise3d(baseSeed, pos.x, pos.y, pos.z, 5, 0.3f);
		float IceTex = Ridged.noise3d(baseSeed+2, pos.x, pos.y, pos.z, 5, 0.65f);
		float IceWaterTex = Ridged.noise3d(baseSeed+2, pos.x, pos.y, pos.z, 4, 0.6f);
		
		float pole = 1f - Math.abs(position.y);
		float basalts = Ridged.noise3d(baseSeed-1, pos.x, pos.y, pos.z, 4, 0.25f);
		
		float desertBiome = Simplex.noise3d(baseSeed+3, 4, 0.7f, 0.76f, position.x*0.5f, position.y*0.5f, position.z*0.5f);

		float divineMask = Simplex.noise3d(baseSeed-2, 2, 0.2, 0.13, pos.x, pos.y, pos.z);
		float divineBiome = Simplex.noise3d(baseSeed-2, 5, 0.4f, 0.55f, position.x, position.y, position.z);
		float divineVoronoi = Simplex.voronoi3d(baseSeed-1, 3, 0, 1.1, pos.x, pos.y, pos.z);
		
		// For deserts
		if ((desertBiome * depth )> 0.32 && height < 0.42f && height > waterLevel + 0.1f && Math.abs(position.y) < 0.37){
			if (getSlope(position, 0.065f) > 0.074f){
				return desert1.write(out).lerp(desert2, Mathf.clamp(Mathf.round(desertBiome, 0.22f))).a(0.8f);
			}
			return beaches1.write(out).lerp(beaches2, height * 1.5f);
		}

		// For the Divine Faction
		if (divineBiome * (pole * 0.4f) * divineMask > 0.1){
			if (height > waterLevel){
				// Beaches
				if (height < waterLevel + 0.08f){
					return divine2.write(out).lerp(divine1, Mathf.clamp(Mathf.round(divineBiome, 0.15f))).a(0.6f);
				// Valleys
				} else {
					float t = divineVoronoi / (1.0f);
					t = Mathf.clamp(Mathf.round(t, 0.1f));

					int index = (int) (t * (DivineGrad.length - 1));
					float segmentT = (t * (DivineGrad.length - 1)) % 1.0f;

					return DivineGrad[index].write(out)
					.lerp(DivineGrad[Math.min(index + 1, DivineGrad.length - 1)], segmentT).a(0.5f);
				}
			// Stained waters.
			} else {
				return divine1.write(out).lerp(divine3, Mathf.clamp(Mathf.round(Mathf.clamp(Math.abs(height)) * 6.5f, 0.25f))).a(3f);
			}
		}

		// Normal biomes
		if(height > waterLevel){
			// Ice caps
			if(Math.abs(position.y) + height > 1.2f){
				return poleColor1.write(out).lerp(poleColor2, Mathf.clamp(Mathf.round(height, 0.25f))).a(0.5f);
			} else if (Math.abs(position.y) > 0.77 && mountains * Math.abs(position.y) > 0.51f){		
				return stones1.write(out).lerp(stones2, Mathf.clamp(Mathf.round(mountains, 0.35f))).a(0.8f);

			} else if (getSlope(position, 0.07f) > 0.07f && height > 0.29f){
				return poleColor1.write(out).lerp(poleColor2, Mathf.clamp(Mathf.round(height, 0.25f))).a(0.5f);
			
			// Pine forests
			} else if (height > 0.4f && height > waterLevel + 0.1f && Math.abs(position.y) > 0.3){
				return peaks1.write(out).lerp(peaks2, Mathf.clamp(Mathf.round(height, 0.35f)));
			} else if (mountains > 0.67f){
				return peaks1.write(out).lerp(peaks2, Mathf.clamp(Mathf.round(height, 0.2f)));
			
			/// Beaches
			} else if (height < waterLevel + 0.1f){
				if (basalts + (Math.abs(position.y) * 0.4f) > 0.28){
					return basalts1.write(out).lerp(basalts2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(0.2f);
				}
				if (pole + height > 0.3) {
					return beaches1.write(out).lerp(beaches2, Mathf.clamp(Mathf.round(height, 0.15f))).a(0.5f);
				// Ice beaches close to the pole
				} else if (IceTex < 0.5 && pole < 0.25){
					return IcedBeach1.write(out).lerp(IcedBeach2, Mathf.clamp(Mathf.round(IceTex, 0.3f))).a(0.5f);
				}
			/// Valleys
			} else if (pole + height < 0.3){
				// Iced up valleys
				if (IceTex < 0.45){
					return Iced1.write(out).lerp(Iced2, Mathf.clamp(Mathf.round(depth, 0.15f)));
				}
			}
			return valley1.write(out).lerp(valley2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(0.5f);
		}
		// Oceans
		if(Math.abs(position.y) + IceWaterTex > 0.65 && pole < 0.25){
			return poleOcean1.write(out).lerp(poleOcean2, Mathf.clamp(Mathf.round(IceWaterTex, 0.35f))).a(0.6f);	
		}
		return oceanColor1.write(out).lerp(oceanColor2, Mathf.clamp(Mathf.round(Mathf.clamp(Math.abs(height)) * 6.5f, 0.25f))).a(0.6f);	
	}

	float waterOffset = -0.16f;
	float waterLevel = 0f;
	float terrainMulti = 1.51f;

	@Override
	public float getHeight(Vec3 position) {
		position = rotateY(position, rotation);
		float height = rawHeight(position) * terrainMulti;
		return Math.max(height, waterLevel);//base <= 0.03f ? 0f : Mathf.clamp(base); // clamps ocean to be flat
	}

	float rawHeight(Vec3 position){
		Vec3 pos = new Vec3(position).scl(4.5f);

		float pole = Mathf.pow(Math.abs(position.y), 0.65f)/ 3f;
		float poleMask = Mathf.pow(Math.abs(position.y), 4f);

		float mountains = Ridged.noise3d(baseSeed, pos.x, pos.y, pos.z, 5, 0.3f);
		float glaciers = Ridged.noise3d(baseSeed+1, position.x, position.y, position.z, 4, 0.5f);
		float base = (Mathf.pow(Simplex.noise3d(baseSeed, 6, 0.55f, 0.1, pos.x, pos.y, pos.z), 2.3f) + waterOffset) / (1f + waterOffset);
		
		float latitudeFactor = 1f - Math.abs(position.y);

		// //actual processing of terrain
		mountains *= latitudeFactor;
		glaciers *= poleMask * 0.8f;
		if (base > waterLevel - 0.1f){
			base += mountains * 0.23;
		}

		base += pole * 0.4f;
		base += glaciers * 0.3f;
		
		return base;
    }

	float getSlope(Vec3 position, float sampleRadius) {
		float center = getHeight(position);
		Vec3[] samples = {
			position.cpy().add(sampleRadius, 0, 0),
			position.cpy().add(-sampleRadius, 0, 0),
			position.cpy().add(0, 0, sampleRadius),
			position.cpy().add(0, 0, -sampleRadius)
		};
		
		float slopeSum = 0f;
		for(Vec3 sample : samples) {
			slopeSum += Math.abs(center - getHeight(sample));
		}
		return slopeSum / samples.length;
	}
}
