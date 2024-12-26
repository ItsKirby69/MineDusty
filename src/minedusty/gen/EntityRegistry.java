package minedusty.gen;

import arc.func.Func;
import arc.func.Prov;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap;
import arc.util.Structs;
import mindustry.gen.EntityMapping;
import mindustry.gen.Entityc;

@SuppressWarnings("unchecked")
public final class EntityRegistry {
    private static final ObjectIntMap<Class<? extends Entityc>> ids = new ObjectIntMap<>();

    private static final ObjectMap<String, Prov<? extends Entityc>> map = new ObjectMap<>();

    private EntityRegistry() {
        throw new AssertionError();
    }

    public static <T extends Entityc> Prov<T> get(Class<T> type) {
        return get(type.getCanonicalName());
    }

    public static <T extends Entityc> Prov<T> get(String name) {
        return (Prov<T>)map.get(name);
    }

    public static <T extends Entityc> void register(String name, Class<T> type,
            Prov<? extends T> prov) {
        map.put(name, prov);
        ids.put(type, EntityMapping.register(name, prov));
    }

    public static <T, E extends Entityc> T content(String name, Class<E> type,
            Func<String, ? extends T> create) {
        if(type.getName().startsWith("mindustry.gen.")) {
            var prov = Structs.find(EntityMapping.idMap, p -> p != null && p.get().getClass().equals(type));
            EntityMapping.nameMap.put("minedusty-" + name, prov);
        } else {
            EntityMapping.nameMap.put("minedusty-" + name, get(type));
        }
        return create.get(name);
    }

    public static int getID(Class<? extends Entityc> type) {
        return ids.get(type, -1);
    }

    public static void register() {
        register("minedusty.gen.MechUnit", MechUnit.class, MechUnit::create);
    }
}
