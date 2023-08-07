package io.codelex.flightplanner.admin;

import io.codelex.flightplanner.admin.domain.Flight;
import io.codelex.flightplanner.admin.request.FlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
public class FlightRequestValidationService {

    public void validateRequest(List<Flight> flights, FlightRequest flightRequest) {
        boolean flightAlreadyExists = flights.stream().anyMatch(fl -> (fl.getFrom().equals(flightRequest.getFrom()) &&
                fl.getTo().equals(flightRequest.getTo()) &&
                fl.getCompany().equals(flightRequest.getCompany()) &&
                fl.getDepartureTime().isEqual(departureTime) &&
                fl.getArrivalTime().isEqual(arrivalTime)));

        if (flightRequest.getFrom().equals(flightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure airport is the same as arrival.");
        }

        if (flightAlreadyExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This flight already exists in the database.");
        }

        if (arrivalTime.isBefore(departureTime) || arrivalTime.isEqual(departureTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect arrival and departure dates.");
        }
    }
}
