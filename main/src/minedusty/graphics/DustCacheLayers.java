package minedusty.graphics;

import mindustry.graphics.CacheLayer;

public class DustCacheLayers {
	public static CacheLayer quicksand;

	public static void load(){
		CacheLayer.add(
			quicksand = new CacheLayer.ShaderLayer(DustShaders.quicksand)
		);
	}
}
