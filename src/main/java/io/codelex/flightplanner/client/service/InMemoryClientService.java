package io.codelex.flightplanner.client.service;
import io.codelex.flightplanner.admin.domain.Airport;
import io.codelex.flightplanner.admin.domain.Flight;
import io.codelex.flightplanner.admin.response.FlightResponse;
import io.codelex.flightplanner.client.request.SearchFlightRequest;
import io.codelex.flightplanner.client.response.SearchFlightResponse;
import io.codelex.flightplanner.repository.memory.InMemoryFlightsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InMemoryClientService implements ClientService {

    private InMemoryFlightsRepository inMemoryFlightsRepository;

    public InMemoryClientService(InMemoryFlightsRepository inMemoryFlightsRepository) {
        this.inMemoryFlightsRepository = inMemoryFlightsRepository;
    }

    public List<Airport> searchAirport(String airportSearchQuery){
        return inMemoryFlightsRepository.searchAirport(airportSearchQuery);
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = inMemoryFlightsRepository.getFlightById(flightId);

        String departureTime = flightFromDatabase.getDepartureTime().toString();
        String arrivalTime = flightFromDatabase.getArrivalTime().toString();
        int flightIdInt = Math.toIntExact(flightFromDatabase.getId());

        return new FlightResponse(
                flightFromDatabase.getFrom(),
                flightFromDatabase.getTo(),
                flightFromDatabase.getCompany(),
                departureTime,
                arrivalTime,
                flightIdInt
        );
    }

    public SearchFlightResponse<Flight> searchFlights(SearchFlightRequest flight) {
        return inMemoryFlightsRepository.searchFlights(flight);
    }
}

