package io.codelex.flightplanner.client.service;

import io.codelex.flightplanner.admin.domain.Airport;
import io.codelex.flightplanner.admin.domain.Flight;
import io.codelex.flightplanner.admin.response.FlightResponse;
import io.codelex.flightplanner.client.request.SearchFlightRequest;
import io.codelex.flightplanner.client.response.SearchFlightResponse;

import java.util.List;

public interface ClientService {
    List<Airport> searchAirport(String airportSearchQuery);

    FlightResponse getFlightById(String flightId);
    SearchFlightResponse<Flight> searchFlights(SearchFlightRequest flight);
}
