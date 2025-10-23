package minedusty.utils;

import arc.files.Fi;
import arc.struct.Seq;
import arc.util.Log;
import minedusty.blocks.*;
import minedusty.content.*;
import minedusty.planets.DustPlanets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

// For importing Misc sections from the bundle to the generated bundle
class BundleSection {
    String header;
    Seq<String> rawLines = new Seq<>();
    Properties entries = new Properties();
}

/** Generates the bundles as the file listed in existingFile */
public class GenBundles {
    
    public static final String MODNAME = "minedusty";
    private static Properties existingBundles;

    private static Seq<BundleSection> preservedSections = new Seq<>();

    private static Seq<String> headerList = new Seq<>();

    // Generates the bundle
    public static void generate(){
        // Output directory (filename included)
        Fi outFile = new Fi("assets/bundles/generated_bundles.properties");
        // The existing bundle to copy already existing properties
        Fi existingFile = new Fi("assets/bundles/bundle.properties");

        loadBundle(existingFile);
        loadBundlePreserving(existingFile);
        headerList.clear();
        
        // Which classes to make bundles for
        try(PrintWriter out = new PrintWriter(outFile.write(false))) {
            if(outFile.exists()){
                outFile.delete();
            }
            writeCategory(out, "planets", DustPlanets.class, "planet", true);
            writeCategory(out, "weathers", DustWeathers.class, "weather");
            writeCategory(out, "sectors", DustSectors.class, "sector", true);

            writeCategory(out, "units", DustUnitTypes.class, "unit");
            writeCategory(out, "items", DustItems.class, "item", true, true);
            writeCategory(out, "fluids", DustLiquids.class, "liquid", true);

            writeCategory(out, "core", DustCore.class);
            writeCategory(out, "plants", DustPlants.class);
            writeCategory(out, "env", DustEnv.class);
        
            writeCategory(out, "drills", DustDrills.class, true);
            writeCategory(out, "crafters", DustCrafters.class, true);
            writeCategory(out, "distribution", DustDistribution.class, true);
            writeCategory(out, "power", DustPower.class, true);
            writeCategory(out, "defence", DustDefence.class, true);
            writeCategory(out, "turrets", DustTurret.class, true);

            // For misc lines
            out.println("# MISC STUFF");

            for(BundleSection section : preservedSections){
                if(section.header == null) continue;

                // My poor attempt at making this automatic
                String normalizeHeader = section.header.trim();
                if(normalizeHeader.startsWith("#")) normalizeHeader = normalizeHeader.substring(1).trim();
                normalizeHeader = normalizeHeader.toLowerCase();
                
                if(headerList.contains(normalizeHeader)) continue;
            
                if(section.header != null) out.println(section.header);
                for(String line : section.rawLines) out.println(line);
                // out.println();
            }

            Log.info(outFile.path() + " generated successfully.");
        } catch(Exception e){
            Log.err("Error generating bundles: " + e.getMessage());
        }
    }

    /** Loads misc bundle lines (like settings etc) in categories */
    private static void loadBundlePreserving(Fi existingFile){
        preservedSections.clear();

        if(!existingFile.exists()) return;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(existingFile.read()))){
            String line;
            BundleSection current = new BundleSection();

            while((line = reader.readLine()) != null){
                String trimmed = line.trim();

                // Header line
                if(trimmed.startsWith("#")){
                    if(current.header != null || !current.rawLines.isEmpty()){
                        preservedSections.add(current);
                        current = new BundleSection();
                        current.rawLines = new Seq<>();
                        current.entries = new Properties();
                    }
                    current.header = line;
                    continue;
                }

                // The key and values
                if(trimmed.contains("=")){
                    String key = trimmed.substring(0, trimmed.indexOf('=')).trim();
                    String value = trimmed.substring(trimmed.indexOf('=') + 1).trim();

                    StringBuilder fullValue = new StringBuilder(value);
                    current.rawLines.add(line);

                    // Handle multi-line continuation
                    while(value.endsWith("\\")){
                        String next = reader.readLine();
                        if(next == null) break;
                        fullValue.append("\n").append(next);
                        current.rawLines.add(next);
                        value = next.trim();
                    }

                    current.entries.setProperty(key, value.toString());
                } else {
                    current.rawLines.add(line);

                    while(line.trim().endsWith("\\")){
                        String next = reader.readLine();
                        if(next == null) break;
                        current.rawLines.add(next);
                        line = next;
                    }
                }
                
            }
            if(current.header != null || !current.rawLines.isEmpty()){
                preservedSections.add(current);
            }
            Log.info("Loaded @ sections from bundle", preservedSections.size);
        }catch(Exception e){
            Log.err("Error loading existing bundle: @", e.getMessage());
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
        writeCategory(out, header, clazz, "block", false, false);
    }

    private static void writeCategory(PrintWriter out, String header, Class<?> clazz, boolean description){
        writeCategory(out, header, clazz, "block", description, false);
    }

    private static void writeCategory(PrintWriter out, String header, Class<?> clazz, String suffix){
        writeCategory(out, header, clazz, suffix, false, false);
    }

    private static void writeCategory(PrintWriter out, String header, Class<?> clazz, String suffix, boolean description){
        writeCategory(out, header, clazz, suffix, description, false);
    }


    /**  Actual writing of the bundle keys.
     * 
     * Suffix: is the type of key. For example, blocks like walls etc would be "block".
     * For a block internally named 'rocky-wall' -> block.modid-rocky-wall = 
     * 
     * Items = "item", Block = "block", Units = "unit", Sectors = "sector"
     * 
     * Description: to generate a description field for the class. Items and Sectors usually have this enabled
     * Details: For more descriptive field for the class. Used for lore bits in items.
     * */
    private static void writeCategory(PrintWriter out, String header, Class<?> clazz, String suffix, boolean description, boolean details) {
        headerList.add(header);
        
        out.println("# " + header);
        for(Field f : clazz.getDeclaredFields()){
            try{
                Object obj = f.get(null);
                if(obj == null) continue;

                String b = getObjectName(obj);
                if(b == null) continue;

                String baseKey = suffix + "." + b;
                String nameKey = baseKey + ".name";

                String existinvgValue = existingBundles.getProperty(nameKey);
                if(existinvgValue != null && !existinvgValue.trim().isEmpty()){
                    out.println(nameKey + " = " + existinvgValue);
                } else {
                    out.println(nameKey + " = ");
                }
                // Could technically be a custom object but ehh
                if (description) writeKey(out, baseKey + ".description");
                if (details) writeKey(out, baseKey + ".details");

            }catch(IllegalAccessException ignored){}
        }
        out.println();
    }

    private static void writeKey(PrintWriter out, String key){
        String value = existingBundles.getProperty(key);
        out.println(key + " = " + (value != null ? value : ""));
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