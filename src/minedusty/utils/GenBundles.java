package minedusty.utils;

import arc.files.Fi;
import arc.util.Log;
import mindustry.world.Block;
import minedusty.blocks.*;
import minedusty.content.DustItems;
import minedusty.content.DustLiquids;
import minedusty.content.DustSectors;
import minedusty.content.DustUnitTypes;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Properties;

/** WIP crude bundles generator because im lazy lol */
public class GenBundles {
    
    public static final String MODNAME = "minedusty";
    private static Properties existingBundles;

    // Generates the bundle
    public static void generate(){
        // Output directory (filename included)
        Fi outFile = new Fi("assets/bundles/generated_bundles.properties");
        // The existing bundle to copy already existing properties
        Fi existingFile = new Fi("assets/bundles/bundle.properties");

        loadBundle(existingFile);
        
        // Which classes to make bundles for
        try(PrintWriter out = new PrintWriter(outFile.write(false))) {
            writeCategory(out, "units", DustUnitTypes.class, "unit");
            writeCategory(out, "items", DustItems.class, "item", true);
            writeCategory(out, "fluids", DustLiquids.class, "liquid", true);

            writeCategory(out, "core", DustCore.class);
            writeCategory(out, "plants", DustPlants.class);
            writeCategory(out, "env", DustEnv.class);
            writeCategory(out, "turrets", DustTurret.class, true);
            writeCategory(out, "drills", DustDrills.class);
            writeCategory(out, "Crafters", DustCrafters.class, true);
            writeCategory(out, "distribution", DustDistribution.class, true);
            writeCategory(out, "power", DustPower.class, true);
            writeCategory(out, "defence", DustDefence.class, true);

            writeCategory(out, " Sectors", DustSectors.class, "sector", true);

            Log.info(outFile.path() + " generated successfully.");
        } catch(Exception e){
            Log.err("Error generating bundles: " + e.getMessage());
        }
    }

    private static void loadBundle(Fi existingFile) {
        existingBundles = new Properties();
        if (existingFile.exists()) {
            try {
                existingBundles.load(existingFile.reader());
                Log.info("Loaded " + existingBundles.size() + " existing bundle entries");
            } catch (Exception e) {
                Log.err("Error loading existing bundles: " + e.getMessage());
            }
        }
    }

    private static void writeCategory(PrintWriter out, String header, Class<?> clazz){
        writeCategory(out, header, clazz, "block", false);
    }

    private static void writeCategory(PrintWriter out, String header, Class<?> clazz, boolean description){
        writeCategory(out, header, clazz, "block", description);
    }

    private static void writeCategory(PrintWriter out, String header, Class<?> clazz, String suffix){
        writeCategory(out, header, clazz, suffix, false);
    }

    /**  Actual writing of the bundle keys.
     * 
     * Suffix: is the type of key. For example, blocks like walls etc would be "block".
     * For a block internally named 'rocky-wall' -> block.modid-rocky-wall = 
     * 
     * Items = "item", Block = "block", Units = "unit", Sectors = "sector"
     * 
     * Description: to generate a description field for the class. Items and Sectors usually have this enabled
     * */
    private static void writeCategory(PrintWriter out, String header, Class<?> clazz, String suffix, boolean description) {
        out.println("# " + header);
        for(Field f : clazz.getDeclaredFields()){
            try{
                Object obj = f.get(null);
                if (obj != null) {
                    String b = getObjectName(obj);
                    if(b != null){
                        String baseKey = suffix + "." + b;
                        String nameKey = baseKey + ".name";

                        String existinvgValue = existingBundles.getProperty(nameKey);
                        if(existinvgValue != null && !existinvgValue.trim().isEmpty()){
                            out.println(nameKey + " = " + existinvgValue);
                        } else {
                            out.println(nameKey + " = ");
                        }

                        if (description){
                            String descKey = baseKey + ".description";
                            String existingDesc = existingBundles.getProperty(descKey);
                            if(existingDesc != null && !existingDesc.trim().isEmpty()){
                                out.println(descKey + " = " + existingDesc);
                            } else {
                                out.println(descKey + " = ");
                            }
                        }
                    }
                }
            }catch(IllegalAccessException ignored){}
        }
        out.println();
    }

    private static String getObjectName(Object obj) {
        // Simple name extraction that works for most Mindustry types. Beware for set values that aren't actually valid objects. Like Seq or whatnot.
        // This should automatically get the object's whole internal name, including modid
        try {
            Field nameField = obj.getClass().getField("name");
            Object nameValue = nameField.get(obj);
            if(nameValue instanceof String) return (String)nameValue;
        } catch(Exception e) {
            String toString = obj.toString();
            if(toString.contains(".")){
                return toString.substring(toString.lastIndexOf('.') + 1);
            }
            return toString;
        }
        return null;
    }
}