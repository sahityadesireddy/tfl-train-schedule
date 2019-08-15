package com.sd.travel.tfl.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

/**
 * A collector of all properties
 */
public class Forecast {

    public  String id;
    public  String platformName;
    public  String lineName;
    public  String destinationName;
    public  String stationName;
    public  String direction;
    public  String vehicleId;
    public  Date expectedArrival;
    public  String naptanId;
    public  Integer timeToStation;
    public  String modeName;
    
    private Date asDate(String text){
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(text);
        } catch (ParseException e) {
        
        }
        return null;
    }
    
    public Integer getTimeToStation() {
        return timeToStation/60;
    }
    
    public String getId() {
        return id;
    }

    public String getPlatformName() {
        return platformName;
    }

}
