package minedusty.graphics;

import mindustry.graphics.CacheLayer;

public class DustCacheLayers {
	public static CacheLayer quicksand, bog, mist, prismite;

	public static void load(){
		CacheLayer.add(
			quicksand = new CacheLayer.ShaderLayer(DustShaders.quicksand),
			bog = new CacheLayer.ShaderLayer(DustShaders.bog),
			mist = new CacheLayer.ShaderLayer(DustShaders.mist),
			prismite = new CacheLayer.ShaderLayer(DustShaders.prismite)
		);
	}
}
