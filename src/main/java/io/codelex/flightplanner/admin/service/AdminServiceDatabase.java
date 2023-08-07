package io.codelex.flightplanner.admin.service;


import io.codelex.flightplanner.admin.FlightRequestValidationService;
import io.codelex.flightplanner.admin.domain.Airport;
import io.codelex.flightplanner.admin.domain.Flight;
import io.codelex.flightplanner.admin.request.FlightRequest;
import io.codelex.flightplanner.admin.response.FlightResponse;
import io.codelex.flightplanner.repository.database.AirportsRepositoryDatabase;
import io.codelex.flightplanner.repository.database.FlightsRepositoryDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class AdminServiceDatabase implements AdminService {
    private FlightsRepositoryDatabase flightsRepositoryDatabase;
    private AirportsRepositoryDatabase airportsRepositoryDatabase;
    private FlightRequestValidationService flightRequestValidationService;

    public static final Logger logger = LoggerFactory.getLogger(AdminServiceDatabase.class);

    public AdminServiceDatabase(FlightsRepositoryDatabase flightsRepositoryDatabase,
                                AirportsRepositoryDatabase airportsRepositoryDatabase,
                                FlightRequestValidationService flightRequestValidationService) {
        this.flightsRepositoryDatabase = flightsRepositoryDatabase;
        this.airportsRepositoryDatabase = airportsRepositoryDatabase;
        this.flightRequestValidationService = flightRequestValidationService;
    }

    public FlightResponse getFlightById(String flightId) {
        Optional<Flight> foundFlight = flightsRepositoryDatabase.findById(Long.parseLong(flightId));

        if (foundFlight.isPresent()) {
            Flight flightFromDatabase = foundFlight.get();
            String departureDateTime = formatLocalDateTimeToString(flightFromDatabase.getDepartureTime());
            String arrivalDateTime = formatLocalDateTimeToString(flightFromDatabase.getArrivalTime());

            return new FlightResponse(
                    flightFromDatabase.getId(),
                    flightFromDatabase.getCompany(),
                    departureDateTime,
                    arrivalDateTime,
                    flightFromDatabase.getFrom(),
                    flightFromDatabase.getTo()
            );
        }

        logger.info("Failed to find flight with id: " + flightId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no flight with the given id.");
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        List<Flight> flightsFromDatabase = flightsRepositoryDatabase.findAll();
        flightRequestValidationService.validateRequest(flightsFromDatabase, flightRequest);

        Airport fromAirport = flightRequest.getFrom();
        Airport toAirport = flightRequest.getTo();

        if (!airportsRepositoryDatabase.exists(Example.of(fromAirport))) {
            airportsRepositoryDatabase.save(fromAirport);
            logger.info("Airport added to database: " + fromAirport);
        }

        if (!airportsRepositoryDatabase.exists(Example.of(toAirport))) {
            airportsRepositoryDatabase.save(toAirport);
            logger.info("Airport added to database: " + toAirport);
        }

        LocalDateTime departureDateTime = LocalDateTime.parse(flightRequest.getDepartureTime());
        LocalDateTime arrivalDateTime = LocalDateTime.parse(flightRequest.getArrivalTime());

        int lastId = (int) flightsFromDatabase.stream()
                .mapToLong(Flight::getId)
                .max()
                .orElse(0);

        int nextId = lastId + 1;

        Flight flightToSave = new Flight(
                nextId,
                flightRequest.getCompany(),
                departureDateTime,
                arrivalDateTime,
                fromAirport,
                toAirport
        );

        Flight savedFlight = flightsRepositoryDatabase.save(flightToSave);

        String formattedDepartureDateTime = departureDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String formattedArrivalDateTime = arrivalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return new FlightResponse(
                savedFlight.getId(),
                savedFlight.getCompany(),
                formattedDepartureDateTime,
                formattedArrivalDateTime,
                savedFlight.getFrom(),
                savedFlight.getTo()
        );
    }

    public String deleteFlight(String flightId) {
        flightsRepositoryDatabase.deleteById(Long.parseLong(flightId));
        logger.info("Flight with id: " + flightId + " removed from database.");
        return "Flight with id: " + flightId + " removed from database.";
    }

    private String formatLocalDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
