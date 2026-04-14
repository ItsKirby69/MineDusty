package minedusty.utils;

import java.io.*;
import java.util.*;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.ui.*;

// Omaloon my beloved (Modified like Aquarion)
public class IconLoader {
public static void loadIcons(){
        Properties iconProperties = new Properties();
        try(Reader reader = Vars.tree.get("icons/minedusty-icons.properties").reader(512)){
            iconProperties.load(reader);
        }catch(Exception e){
            return;
        }

        for(Map.Entry<Object, Object> entry : iconProperties.entrySet()){
            String codePointStr = (String)entry.getKey();
            String[] valueParts = ((String)entry.getValue()).split("\\|");
            if(valueParts.length < 2){
                continue;
            }

            try{
                int codePoint = Integer.parseInt(codePointStr);
                String textureName = valueParts[1];
                TextureRegion region = Core.atlas.find(textureName);

                // Register the icon using built-in mindustry method
                Fonts.registerIcon(valueParts[0], textureName, codePoint, region);


            }catch(Exception ignored){
            }
        }
    }
}
