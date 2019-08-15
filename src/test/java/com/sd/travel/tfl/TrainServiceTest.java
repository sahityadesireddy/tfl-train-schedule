package com.sd.travel.tfl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sd.travel.tfl.domain.Forecast;
import com.sd.travel.tfl.service.TflApiService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("servicetest")
public class TrainServiceTest {

    private static final double DELTA = 1e-15;
    
    @Autowired
    private TflApiService trainService;

      @Test
    public void testAllTrains() throws URISyntaxException {
          List<Forecast> trains = trainService.searchTrains();
        assertNotNull(trains);
        assertTrue(!trains.isEmpty());
    }

      @Test(expected=UnsatisfiedLinkError.class)
      public void testTrainsByEmptyPlatfrm() throws URISyntaxException {
            List<Forecast> trains = trainService.searchTrains("");
          assertNotNull(trains);
          assertTrue(trains.isEmpty());
      }

      @Test
      public void testTrainsByPlatfrm() throws URISyntaxException {
            List<Forecast> trains = trainService.searchTrains("Platform 2");
          assertNotNull(trains);
          assertTrue(!trains.isEmpty());
      }
      
      @Test
      public void testTrainsByPlatform() throws URISyntaxException {
            Map<String, List<Forecast>> trains = trainService.trainsByPlatform();
          assertNotNull(trains);
          assertTrue(trains.size()>= 2);
      }
      
}
