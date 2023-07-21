package io.codelex.flightplanner.admin;


import io.codelex.flightplanner.FlightRepository;
import io.codelex.flightplanner.admin.domain.Flights;
import io.codelex.flightplanner.admin.request.FlightRequest;
import io.codelex.flightplanner.admin.response.FlightResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin-api")
public class FlightController {

    private FlightService flightService;
    private AdminValidationsService adminValidationsService;

    public FlightController(FlightService flightService, AdminValidationsService adminValidationsService) {
        this.flightService = flightService;
        this.adminValidationsService = adminValidationsService;
    }

    @GetMapping("/flights/{flightId}")
    public FlightResponse getFlightById(@PathVariable String flightId) {
        return flightService.getFlightById(flightId);
    }

    @PostMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse saveFlight(@Valid @RequestBody FlightRequest flightRequest) {
        LocalDateTime departureTime = LocalDateTime.parse(flightRequest.getDepartureTime());
        LocalDateTime arrivalTime = LocalDateTime.parse(flightRequest.getArrivalTime());

        flightService.validateRequest(flightRequest, departureTime, arrivalTime);

        return flightService.saveFlight(flightRequest);
    }


    @DeleteMapping("/flights/{flightId}")
    public String deleteFlight(@PathVariable String flightId) {
        return flightService.deleteFlight(flightId);
    }
}