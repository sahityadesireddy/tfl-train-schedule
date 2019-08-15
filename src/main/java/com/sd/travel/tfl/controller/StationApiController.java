package com.sd.travel.tfl.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sd.travel.tfl.domain.Forecast;
import com.sd.travel.tfl.service.TflApiService;

@RestController
@RequestMapping("/api")
public class StationApiController {

    @Autowired
    TflApiService tflApiService;

    @GetMapping(value="/trains")
    public List<Forecast> searchTrains() throws URISyntaxException {
        return tflApiService.searchTrains();
    }
    
    @RequestMapping(value = "/trains/{platformId}", method = RequestMethod.GET)
    public List<Forecast> searchTrainsByPlatform(@PathVariable String platformId) throws URISyntaxException {
        return tflApiService.searchTrains(platformId);
    }
    
    @RequestMapping(value = "/trainsByPlatform", method = RequestMethod.GET)
    public Map<String, List<Forecast>> searchTrainsByPlatform() throws URISyntaxException {
        return tflApiService.trainsByPlatform();
    }
    
    
    @ExceptionHandler(UnsatisfiedLinkError.class)
    public ResponseEntity<Object> fdgfg(UnsatisfiedLinkError ex) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

}
