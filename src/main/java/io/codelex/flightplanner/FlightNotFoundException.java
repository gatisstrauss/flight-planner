package io.codelex.flightplanner;

public class FlightNotFoundException extends Throwable {
    public FlightNotFoundException(String message) {
        super(message);
    }
}
