package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.model.PilotModel;
import com.apap.tutorial7.rest.PilotDetail;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.FlightService;
import com.apap.tutorial7.service.PilotService;

/**
 * FlightController
 */
@RestController
@RequestMapping(value = "/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private PilotService pilotService;

    @PostMapping(value = "/add")
    private FlightModel add(@RequestBody FlightModel flight) {
        return flightService.addFlight(flight);
    }


    @GetMapping(value = "/view/{flightNumber}")
    private FlightModel view(@PathVariable(value = "flightNumber") String flightNumber) {
        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        return archive;
    }
    
    @GetMapping(value = "/all")
    private List<FlightModel> viewall() {
        return flightService.findAll();
    }
    

    @DeleteMapping(value = "/{flightID}")
    private String delete(@PathVariable(value = "flightID") long flightId) {
        FlightModel flight = null;
        try  {
        	flight = flightService.getFlightDetailById(flightId);
        }
        finally {
        	if (flight == null) {
    			return "couldn't find flight";
    		}
    		
        	flightService.deleteById(flight);
        }
        return "flight has been deleted";
    }

    @PutMapping(value = "/update/{flightID}")
    private String update(@PathVariable(value = "flightID") long flightId, 
    		@RequestParam(value="destination") Optional<String> destination,
    		@RequestParam(value="origin") Optional<String> origin,
    		@RequestParam(value="time") Optional<Date> time) {
        FlightModel flight = null;
        try  {
        	flight = flightService.getFlightDetailById(flightId);
        }
        finally {
        	if (flight == null) {
    			return "couldn't find flight";
    		}
    		
    		if (destination.isPresent()) {
    			flight.setDestination(destination.get());
    		}
    		
    		if (origin.isPresent()) {
    			flight.setOrigin(origin.get());
    		}
    		
    		if (time.isPresent()) {
    			flight.setTime(time.get());
    		}
        }
		return "flight update success";
        
    }
    
    @Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate rest2() {
		return new RestTemplate();
	}
	
	@GetMapping(value="/status/{kota}")
	public String getKota(@PathVariable("kota") String kota) throws Exception {
		String path = Setting.amadeusIndo + "&term=" + kota;
		return restTemplate.getForEntity(path, String.class).getBody();
	}

	
}