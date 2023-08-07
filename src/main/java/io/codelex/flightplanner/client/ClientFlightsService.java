package io.codelex.flightplanner.client;

import io.codelex.flightplanner.FlightRepository;
import io.codelex.flightplanner.admin.domain.Airports;
import io.codelex.flightplanner.admin.domain.Flights;
import io.codelex.flightplanner.admin.response.FlightResponse;
import io.codelex.flightplanner.client.request.SearchFlightRequest;
import io.codelex.flightplanner.client.response.SearchFlightResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientFlightsService {

    private FlightRepository flightsRepository;

    public ClientFlightsService(FlightRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public List<Airports> searchAirport(String airportSearchQuery){
        return flightsRepository.searchAirport(airportSearchQuery);
    }

    public FlightResponse getFlightById(String flightId) {
        Flights flightFromDatabase = flightsRepository.getFlightById(flightId);

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

    public SearchFlightResponse<Flights> searchFlights(SearchFlightRequest flight) {
        return flightsRepository.searchFlights(flight);
    }
}
