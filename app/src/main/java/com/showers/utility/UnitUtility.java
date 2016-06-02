package com.showers.utility;

/**
 * Created by vikrant on 01/6/16.
 */
public class UnitUtility {


    public static float toCelcius(float temp) {
        return (int) Math.round((temp - 32) / 1.8);
    }

    public static int toFar(float temp) {
        return (int) ((temp - 273.15) * 1.8 + 32);
    }

    public static int toKMH(float val) {
        return (int) Math.round(val * 0.2778);
    }
    public static String speedUnit(String unit){
        if(unit != null && unit.equalsIgnoreCase("mph")){
            return "mph";
        }else{
            return "kmh";
        }
    }
    public static String tempUnit(String unit){
        if(unit != null && unit.equalsIgnoreCase("F")){
            return "°";
        }else{
            return "F";
        }
    }
}
