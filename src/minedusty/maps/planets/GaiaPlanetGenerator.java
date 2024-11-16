package minedusty.maps.planets;

import mindustry.content.Blocks;
import mindustry.game.Schematics;
import mindustry.maps.planet.*;
import mindustry.world.*;
import minedusty.blocks.DustEnv;

public class GaiaPlanetGenerator extends ErekirPlanetGenerator{

	Block[] terrain = {DustEnv.basaltFloor, DustEnv.shoreRock, Blocks.carbonWall};
	{
		baseSeed = 2;
		defaultLoadout = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZWBJzi9KZeBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQzs3MS00pLS6p1AWp1c1LLS4BqmEEISABAPVFEvQ=");
	}
}
