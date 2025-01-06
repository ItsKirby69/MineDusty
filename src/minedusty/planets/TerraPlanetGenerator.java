package minedusty.planets;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.util.noise.Ridged;
import arc.util.noise.Simplex;
import mindustry.game.Schematics;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;

/* Useful notes from other gens (don't quote me) --
 * serpulo gens height as usual then uses Blocks arr which
 * has gradients of block pallets (water -> land tiles)
 * and takes mapcolor of block in setColor method
 */

public class TerraPlanetGenerator extends PlanetGenerator{
	Color  out = new Color(),
	divine1 = Color.valueOf("fffafa"), divine2 = Color.valueOf("c32121"),
	valley1 = Color.valueOf("60b243"), valley2 = Color.valueOf("478e2d"),
	peaks1 = Color.valueOf("1c873f"), peaks2 = Color.valueOf("124c28"),
	oceanColor1 = Color.valueOf("47ace6"), oceanColor2 = Color.valueOf("32608d"),
	poleColor1 = Color.valueOf("d4ecfa"), poleColor2 = Color.valueOf("ffffff"), poleOcean1 = Color.valueOf("91a6f9"), poleOcean2 = Color.valueOf("bfcbf8"),
	beaches1 = Color.valueOf("ead195"), beaches2 = Color.valueOf("b48f55"),
	basalts1 = Color.valueOf("71777e"), basalts2 = Color.valueOf("373d43");
	
	{
		baseSeed = 3;
		defaultLoadout = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZWBJzi9KZeBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQzs3MS00pLS6p1AWp1c1LLS4BqmEEISABAPVFEvQ=");
	}

	@Override
	public void generateSector(Sector sector){
		//nothing yet
	}

	//this is really really really really really really really really jank (i think)
	@Override
	public Color getColor(Vec3 position) {
		float height = getHeight(position);
		float depth = Simplex.noise3d(baseSeed, 4, 0.7f, 0.8f, position.x, position.y, position.z);
		float divineSpread = Simplex.noise3d(baseSeed, 5, 0.5f, 0.4f, position.x, position.y, position.z);

		if (Math.abs(position.y) + depth > 1.4f){ // poles
			return poleColor1.write(out).lerp(poleColor2, Mathf.clamp(Mathf.round(depth, 0.25f))).a(0.5f);
		} else if (Math.abs(position.y) + depth > 1.25f && height < 0.15){ // ocean pole waters
			return poleOcean1.write(out).lerp(poleOcean2, Mathf.clamp(Mathf.round(depth, 0.2f))).a(0.5f);
		} else if (height > 0.45f && Math.abs(position.y) < 0.97){ // peaks
			return peaks1.write(out).lerp(peaks2, Mathf.clamp(Mathf.round(depth, 0.25f))).a(0.5f);
		} else if (height > 0.25f && Math.abs(position.y) < 0.92){ //valleys
			if (divineSpread > 0.55f) {
				if (divineSpread % 0.1f < 0.5f) {
					return divine1.write(out).lerp(divine2, 0.0f).a(0.5f);
				} else {
					return divine1.write(out).lerp(divine2, 1.0f).a(0.5f);
				}
			}
			return valley1.write(out).lerp(valley2, Mathf.clamp(Mathf.round(depth, 0.25f))).a(0.5f);
		} else if (height > 0f){ //beaches/shores
			if (Math.abs(position.y) < 0.5){
				return beaches1.write(out).lerp(beaches2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(0.5f);
			} else {
				return basalts1.write(out).lerp(basalts2, Mathf.clamp(Mathf.round(depth, 0.35f))).a(0.5f);
			}
		} else { //ocean
			return oceanColor1.write(out).lerp(oceanColor2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(0.6f);
		}
	}

	@Override
	public float getHeight(Vec3 position) {
		float pole = Mathf.pow(Math.abs(position.y), 0.65f)/ 3f;
		float depth = Simplex.noise3d(baseSeed, 4, 0.7f, 0.8f, position.x, position.y, position.z);
		float base = rawHeight(position);
		
		float latitudeFactor = 1 - Math.abs(position.y);
		float ridges = Ridged.noise3d(baseSeed - 2, position.x, position.y, position.z, 8, 0.5f);
		
		//actual processing of terrain

		ridges *= latitudeFactor;

		base += pole * 1.2f;
		base -= depth * 1.2f;
		base -= ridges * 0.5f;

		return base <= 0.1f ? 0f : Mathf.clamp(base); // clamps ocean to be flat
	}
	
	float rawHeight(Vec3 position){
        // Noise function for raw height data
        return Simplex.noise3d(baseSeed, 8, 0.7f, 1f, position.x, position.y, position.z);
    }
}