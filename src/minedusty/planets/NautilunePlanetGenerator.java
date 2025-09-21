package minedusty.planets;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import mindustry.game.Schematics;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;
import minedusty.utils.Simplex;

public class NautilunePlanetGenerator extends PlanetGenerator{
    Color out = new Color(), o1 = Color.valueOf("#5057a6"), o2 = Color.valueOf("#f26b83");

    {
		defaultLoadout = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZWBJzi9KZeBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQzs3MS00pLS6p1AWp1c1LLS4BqmEEISABAPVFEvQ=");
	}

    @Override
    public void generateSector(Sector sector){
        //no bases
    }

	@Override
	public float getHeight(Vec3 position) {
		return 0f;
	}

    @Override
    public Color getColor(Vec3 position){
        float depth = Simplex.noise3d(seed, 2, 0.56, 1.7f, position.x, position.y, position.z) / 2f;
        return o1.write(out).lerp(o2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(0.6f);
    }
    // public void getColor(Vec3 position, Color out){
    //     float depth = Simplex.noise3d(seed, 2, 0.56, 1.7f, position.x, position.y, position.z) / 2f;
    //     out.set(o1).lerp(o2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(0.6f);
    // }
}
