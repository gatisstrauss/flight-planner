package io.codelex.flightplanner.client;

import io.codelex.flightplanner.admin.domain.Airport;
import io.codelex.flightplanner.admin.domain.Flight;
import io.codelex.flightplanner.admin.response.FlightResponse;
import io.codelex.flightplanner.client.request.SearchFlightRequest;
import io.codelex.flightplanner.client.response.SearchFlightResponse;
import io.codelex.flightplanner.client.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("airports")
    public List<Airport> searchAirport(@RequestParam String search) {
        return clientService.searchAirport(search);
    }

    @GetMapping("flights/{flightId}")
    public FlightResponse getFlightById(@PathVariable String flightId) {
        return clientService.getFlightById(flightId);
    }

    @PostMapping("flights/search")
    public @ResponseBody SearchFlightResponse<Flight> searchFlights(@Valid @RequestBody SearchFlightRequest flight) {
        return clientService.searchFlights(flight);
    }
}

