package minedusty.planets;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.util.noise.Ridged;
import mindustry.game.Schematics;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;
import minedusty.utils.Simplex;

public class NautilunePlanetGenerator extends PlanetGenerator{
    Color out = new Color(), 
    o1 = Color.valueOf("#5057a6"), o2 = Color.valueOf("#f26b83"),
    i1 = Color.valueOf("#bcb9c6ff"), i2 = Color.valueOf("#a09bafff");

    {
		defaultLoadout = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZWBJzi9KZeBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQzs3MS00pLS6p1AWp1c1LLS4BqmEEISABAPVFEvQ=");
	}

    @Override
    public void generateSector(Sector sector){
        //no bases
    }

	@Override
	public float getHeight(Vec3 position) {
        float cliffs = Ridged.noise3d(baseSeed, position.x, position.y, position.z, 5, 0.6f);
        cliffs = cliffs * 1.4f - 0.65f;
        if (cliffs < 0) return 0f;
		return cliffs;
	}

    @Override
    public void getColor(Vec3 position, Color out){
        float depth = Simplex.noise3d(seed, 2, 0.56, 1.7f, position.x, position.y, position.z) / 2f;

        if (getHeight(position) > 0){
            out.set(i1).lerp(i2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(1f);
            return;
        }
        out.set(o1).lerp(o2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(0.6f);
    }
}
