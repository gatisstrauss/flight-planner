package io.codelex.flightplanner.client;

import io.codelex.flightplanner.admin.domain.Airports;
import io.codelex.flightplanner.admin.domain.Flights;
import io.codelex.flightplanner.admin.response.FlightResponse;
import io.codelex.flightplanner.client.request.SearchFlightRequest;
import io.codelex.flightplanner.client.response.SearchFlightResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class ClientFlightsController {

    private ClientFlightsService customerFlightsService;

    public ClientFlightsController(ClientFlightsService customerFlightsService) {
        this.customerFlightsService = customerFlightsService;
    }

    @GetMapping("airports")
    public List<Airports> searchAirport(@RequestParam String search){
        return customerFlightsService.searchAirport(search);
    }

    @GetMapping("flights/{flightId}")
    public FlightResponse getFlightById(@PathVariable String flightId){
        return customerFlightsService.getFlightById(flightId);
    }

    @PostMapping("flights/search")
    public @ResponseBody SearchFlightResponse<Flights> searchFlights(@Valid @RequestBody SearchFlightRequest flight){
        return customerFlightsService.searchFlights(flight);
    }
}
