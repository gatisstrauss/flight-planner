package io.codelex.flightplanner.test;

import io.codelex.flightplanner.FlightRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestController {

    private final FlightRepository flightRepository;

    public TestController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @PostMapping("/clear")
    public void clearFlights() {
        flightRepository.clearFlights();
    }
}

