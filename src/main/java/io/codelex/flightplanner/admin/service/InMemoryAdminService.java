package io.codelex.flightplanner.admin.service;

import io.codelex.flightplanner.admin.domain.Flight;
import io.codelex.flightplanner.admin.request.FlightRequest;
import io.codelex.flightplanner.admin.response.FlightResponse;

import io.codelex.flightplanner.repository.memory.InMemoryFlightsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class InMemoryAdminService implements AdminService {

    private InMemoryFlightsRepository inMemoryFlightsRepository;

    public InMemoryAdminService(InMemoryFlightsRepository inMemoryFlightsRepository) {
        this.inMemoryFlightsRepository = inMemoryFlightsRepository;
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = inMemoryFlightsRepository.getFlightById(flightId);

        LocalDateTime departureDateTime = flightFromDatabase.getDepartureTime();
        LocalDateTime arrivalDateTime = flightFromDatabase.getArrivalTime();

        return new FlightResponse(
                flightFromDatabase.getId(),
                flightFromDatabase.getCompany(),
                departureDateTime.toString(),
                arrivalDateTime.toString(),
                flightFromDatabase.getFrom(),
                flightFromDatabase.getTo()
        );
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        return inMemoryFlightsRepository.saveFlight(flightRequest);
    }

    public String deleteFlight(String flightId) {
        return inMemoryFlightsRepository.deleteFlight(flightId);
    }

    private String formatLocalDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

}
