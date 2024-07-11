package minedusty.maps.planets;

import mindustry.content.Blocks;
import mindustry.game.Schematics;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.world.*;
import minedusty.content.DustBlocks;

public class GaiaPlanetGenerator extends ErekirPlanetGenerator{

	Block[] terrain = {DustBlocks.basaltFloor, DustBlocks.shoreRock, Blocks.carbonWall};
	{
		baseSeed = 2;
		defaultLoadout = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZWBJzi9KZeBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQzs3MS00pLS6p1AWp1c1LLS4BqmEEISABAPVFEvQ=");
	}
}
