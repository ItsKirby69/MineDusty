package minedusty;

import mindustry.world.meta.Attribute;

public class DustAttributes {
    public static Attribute chlorophyte, salt, crystal, turf;

    public static void load() {
        chlorophyte = Attribute.add("chlorophyte");
        salt = Attribute.add("salt");
        crystal = Attribute.add("crystal");

        turf = Attribute.add("turf");
    }
}
