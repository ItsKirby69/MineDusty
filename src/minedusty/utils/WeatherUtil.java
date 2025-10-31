package minedusty.utils;

import arc.util.Log;
import mindustry.type.Weather;
import mindustry.type.Weather.WeatherEntry;

import static mindustry.Vars.*;


/** Weather related utilities. */
public class WeatherUtil {
    /** For checking current weathers in a sector/map */

    public static boolean containsWeather(Weather weather){
        if (!state.isGame() || state.rules == null || state.rules.weather == null){
            return false;
        }

        try {
            for (WeatherEntry entry : state.rules.weather){
                if (entry.weather == weather){
                    return true;
                }
            }
            return false;
        } catch (Exception e){
            Log.err("Error checking weather: " + e.getMessage());
            return false;
        }
    }
}
