package minedusty.planets;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.util.noise.Ridged;
import arc.util.noise.Simplex;
import mindustry.game.Schematics;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;

public class TerraPlanetGenerator extends PlanetGenerator{
	Color c1 = Color.valueOf("78c441"), c2 = Color.valueOf("85c441"), out = new Color(),
	oceanColor1 = Color.valueOf("30a9f0"), oceanColor2 = Color.valueOf("0c59a6"),
	poleColor1 = Color.valueOf("d4ecfa"), poleColor2 = Color.valueOf("f7fcff"),
	beaches = Color.valueOf("d9bd7c");
	
	{
		baseSeed = 1;
		defaultLoadout = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZWBJzi9KZeBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQzs3MS00pLS6p1AWp1c1LLS4BqmEEISABAPVFEvQ=");
	}

	@Override
	public void generateSector(Sector sector){
		//nothing yet
	}

	@Override
	public Color getColor(Vec3 position) {
		float height = getHeight(position);
		float depth = Simplex.noise3d(seed, 3, 0.7f, 1f, position.x, position.y, position.z);

		if (Math.abs(position.y) + depth > 1.2f){ // poles
			return poleColor1.write(out).lerp(poleColor2, Mathf.clamp(Mathf.round(depth, 0.25f))).a(0.5f);
		} else if (height + depth > 0.6f){ // peaks
			return c2.write(out);
		} else if (height + depth > 0.4f){ //valleys
			return c1.write(out);
		} else if (height + depth > 0.1f){
			return beaches.write(out);
		}
		return oceanColor1.write(out).lerp(oceanColor2, Mathf.clamp(Mathf.round(depth, 0.25f))).a(0.6f);
	}

	@Override
	public float getHeight(Vec3 position) {
		float pole = Mathf.pow(Math.abs(position.y), 0.6f)/ 3f;
		float depth = Simplex.noise3d(seed, 4, 0.7f, 0.8f, position.x, position.y, position.z);
		float base = rawHeight(position);

		float latitudeFactor = 1 - Math.abs(position.y);
		float ridges = Ridged.noise3d(baseSeed + 3, position.x, position.y, position.z, 6, 0.5f);

		//base += largeScaleTerrain * 0.4 + smallScaleTerrain * 0.6f;
		
		ridges *= latitudeFactor;

		base += pole * 0.4f;
		base -= depth * 1.15f;
		base -= ridges * 0.5f;

		return base < 0.15f ? 0f : Mathf.clamp(base);
	}
	
	float rawHeight(Vec3 position){
        // Noise function for raw height data
        return Simplex.noise3d(seed, 8, 0.7f, 1f, position.x, position.y, position.z);
    }
}
