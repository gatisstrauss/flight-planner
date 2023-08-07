package io.codelex.flightplanner.admin.service;

import io.codelex.flightplanner.admin.request.FlightRequest;
import io.codelex.flightplanner.admin.response.FlightResponse;

public interface AdminService {
    FlightResponse getFlightById(String flightId);
    FlightResponse saveFlight(FlightRequest flightRequest);
    String deleteFlight(String flightId);
}
