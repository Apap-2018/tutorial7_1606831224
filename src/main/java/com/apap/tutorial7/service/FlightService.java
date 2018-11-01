package com.apap.tutorial7.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.FlightModel;

/**
 * FlightService
 */
public interface FlightService {
    FlightModel addFlight(FlightModel flight);
    
    void deleteByFlightNumber(String flightNumber);

    Optional<FlightModel> getFlightDetailByFlightNumber(String flightNumber);

	FlightModel getFlightDetailById(long flightId);

	void deleteById(FlightModel flight);

	List<FlightModel> findAll();

	
}