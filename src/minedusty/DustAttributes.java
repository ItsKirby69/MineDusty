package minedusty;

import mindustry.world.meta.Attribute;

public class DustAttributes {
    public static Attribute chlorophyte, salt;

    public static void load() {
        chlorophyte = Attribute.add("chlorophyte");
        salt = Attribute.add("salt");
    }
}
