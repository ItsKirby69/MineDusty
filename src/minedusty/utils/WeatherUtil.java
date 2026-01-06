package minedusty.utils;

import arc.util.Log;
import mindustry.type.Weather;
import mindustry.type.Weather.WeatherEntry;

import static mindustry.Vars.*;


/** Weather related utilities. */
public class WeatherUtil {
    /** Returns true if given any of the given weathers are active. */

    public static boolean activeWeather(Weather... weathers){
        if (!state.isGame() || state.rules == null || state.rules.weather == null){
            return false;
        }

        try {
            for (WeatherEntry entry : state.rules.weather){
                for (Weather w : weathers){
                    if (entry.weather.isActive() && entry.weather == w){
                        return true;
                    }
                }

            }
            return false;
        } catch (Exception e){
            Log.err("Error checking weather: " + e.getMessage());
            return false;
        }
    }
}
