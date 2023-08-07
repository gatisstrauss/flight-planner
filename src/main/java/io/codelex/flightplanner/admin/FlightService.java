package io.codelex.flightplanner.admin;

import io.codelex.flightplanner.FlightRepository;
import io.codelex.flightplanner.admin.domain.Flights;
import io.codelex.flightplanner.admin.request.FlightRequest;
import io.codelex.flightplanner.admin.response.FlightResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private FlightRepository flightRepository;
    private Logger logger = LoggerFactory.getLogger(FlightService.class);


    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightResponse getFlightById(String flightId) {
        Flights flightFromDatabase = flightRepository.getFlightById(flightId);

        String departureTime = flightFromDatabase.getDepartureTime().toString();
        String arrivalTime = flightFromDatabase.getArrivalTime().toString();
        int flightIdInt = flightFromDatabase.getId();

        return new FlightResponse(
                flightFromDatabase.getFrom(),
                flightFromDatabase.getTo(),
                flightFromDatabase.getCompany(),
                departureTime,
                arrivalTime,
                flightIdInt
        );
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        return flightRepository.saveFlight(flightRequest);
    }

    public String deleteFlight(String flightId) {
        return flightRepository.deleteFlight(flightId);
    }

    public void validateRequest(FlightRequest flightRequest, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        boolean flightAlreadyExists = flightRepository.getFlights().stream().anyMatch(fl -> (fl.getFrom().equals(flightRequest.getFrom()) &&
                fl.getTo().equals(flightRequest.getTo()) &&
                fl.getCompany().equals(flightRequest.getCompany()) &&
                fl.getDepartureTime().isEqual(departureTime) &&
                fl.getArrivalTime().isEqual(arrivalTime)));

        if (flightRequest.getFrom().getAirport().trim().equalsIgnoreCase(flightRequest.getTo().getAirport().trim())) {
            logger.error("Trying to add a flight with the same departure and arrival airport.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure airport is the same as arrival.");
        }

        if (flightAlreadyExists) {
            logger.error("Flight you are trying to add already exists in the database.");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This flight already exists in the database.");
        }

        if (arrivalTime.isBefore(departureTime) || arrivalTime.isEqual(departureTime)) {
            logger.error("Incorrect arrival and departure dates. Arrival time is the same or before departure.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect arrival and departure dates.");
        }
    }
}
