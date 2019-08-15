package com.sd.travel.tfl.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.GsonBuilder;
import com.sd.travel.tfl.domain.Forecast;

@Service
public class TflApiService {

    @Value("${tfl.endpoint}")
    private String apiBaseUrl;

    @Value("${tfl.display.stations}")
    private String station;

    private String appId;
    private String appKey;
    private int maxTimeoutInMilliseconds;

    private String stationId;

    @PostConstruct
    void init() {
        stationId = getStation(station);
    }

    public List<Forecast> searchTrains() throws URISyntaxException {
        if (stationId == null)
            throw new UnsatisfiedLinkError(station + " name not exist");
        List<Forecast> listOfTrains = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        Map[] response = restTemplate.getForEntity(apiBaseUrl + "/StopPoint/" + stationId + "/Arrivals", Map[].class)
                .getBody();
        Arrays.stream(response).forEach(item -> listOfTrains
                .add(new GsonBuilder().create().fromJson(new GsonBuilder().create().toJson(item), Forecast.class)));

        return sort(listOfTrains);
    }

    public List<Forecast> searchTrains(String platformId) throws URISyntaxException {
        if (stationId == null)
            throw new UnsatisfiedLinkError(station + " name not exist");
        if (platformId == null || platformId.isEmpty())
            throw new UnsatisfiedLinkError(platformId + " platform not exist");
        List<Forecast> forecasts = searchTrains();
        Map<String, List<Forecast>> platformMap = forecasts.stream()
                .collect(Collectors.groupingBy(Forecast::getPlatformName));
        List<Forecast> trainList = platformMap.get(platformId);
       if (trainList == null || trainList.isEmpty()) {
            List platforms = platformMap.keySet().stream().filter(p -> p.contains(platformId)).collect(Collectors.toList());
            if (!platforms.isEmpty())
                trainList = platformMap.get(platforms.get(0));
       }
       
        return trainList == null ? new ArrayList<>() : trainList;
    }

    public Map<String, List<Forecast>> trainsByPlatform() throws URISyntaxException {
        List<Forecast> forecasts = searchTrains();
        Map<String, List<Forecast>> platformMap = forecasts.stream()
                .collect(Collectors.groupingBy(Forecast::getPlatformName));
        return platformMap;
    }
    
    private String getStation(String stationName) {
        RestTemplate restTemplate = new RestTemplate();
        Map response = restTemplate.getForEntity(apiBaseUrl + "/StopPoint/Search/" + stationName, Map.class).getBody();
        if (response.get("matches") instanceof List) {
            List stations = (List) response.get("matches");
            if (!stations.isEmpty()) {
                Map map = (LinkedHashMap) stations.get(0);
                return map.get("id").toString();
            }
        } 
        return null;
    }

    private List<Forecast> sort(List<Forecast> list) {
        Collections.sort(list, new Comparator<Forecast>() {
            @Override
            public int compare(Forecast o1, Forecast o2) {
                if (o1.getTimeToStation() < o2.getTimeToStation())
                    return -1;
                else if (o1.getTimeToStation() > o2.getTimeToStation())
                    return 1;
                else
                    return 0;
            }
        });
        return list;
    }
}
