package minedusty;

import mindustry.world.meta.Attribute;

// WIP likely to be removed
public class DustAttributes {
    public static Attribute chlorophyte;

    public static void load() {
        chlorophyte = Attribute.add("chlorophyte");
    }
}
