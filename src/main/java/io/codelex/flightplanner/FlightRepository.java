package io.codelex.flightplanner;

import io.codelex.flightplanner.admin.AdminValidationsService;
import io.codelex.flightplanner.admin.domain.Airports;
import io.codelex.flightplanner.admin.domain.Flights;
import io.codelex.flightplanner.admin.request.FlightRequest;
import io.codelex.flightplanner.admin.response.FlightResponse;
import io.codelex.flightplanner.client.request.SearchFlightRequest;
import io.codelex.flightplanner.client.response.SearchFlightResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class FlightRepository {
    private List<Flights> flights = new ArrayList<>();
    private Map<String, Airports> allAirports = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(FlightRepository.class);


    public Flights getFlightById(String flightId) {
        Optional<Flights> optionalFlight = flights.stream()
                .filter(flight -> flightId.equals(String.valueOf(flight.getId())))
                .findFirst();

        if (optionalFlight.isPresent()) {
            logger.info("Flight with id: " + flightId + " was found.");
            return optionalFlight.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no flight with the given id.");
        }
    }

    public synchronized FlightResponse saveFlight(FlightRequest flightRequest) {
        LocalDateTime departureDateTime = LocalDateTime.parse(flightRequest.getDepartureTime());
        LocalDateTime arrivalDateTime = LocalDateTime.parse(flightRequest.getArrivalTime());

        int lastId = flights.stream()
                .mapToInt(Flights::getId)
                .max()
                .orElse(0);

        int nextId = lastId + 1;

        Flights flightToSave = new Flights(
                flightRequest.getFrom(),
                flightRequest.getTo(),
                flightRequest.getCompany(),
                departureDateTime,
                arrivalDateTime,
                nextId
        );

        flights.add(flightToSave);
        logger.info("Flight added to database: " + flightToSave);

        addAirports(flightToSave);

        String departureDateTimeString = departureDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String arrivalDateTimeString = arrivalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return new FlightResponse(
                flightRequest.getFrom(),
                flightRequest.getTo(),
                flightRequest.getCompany(),
                departureDateTimeString,
                arrivalDateTimeString,
                flightToSave.getId()
        );
    }

    public synchronized void clearFlights() {
        logger.info("Flights cleared.");
        flights.clear();
        allAirports.clear();
    }

    private synchronized void addAirports(Flights flight) {
        StringBuilder airportFrom = new StringBuilder();
        airportFrom.append(flight.getFrom().getCountry()).append(" ");
        airportFrom.append(flight.getFrom().getCity()).append(" ");
        airportFrom.append(flight.getFrom().getAirport());

        StringBuilder airportTo = new StringBuilder();
        airportTo.append(flight.getTo().getCountry()).append(" ");
        airportTo.append(flight.getTo().getCity()).append(" ");
        airportTo.append(flight.getTo().getAirport());

        allAirports.put(airportFrom.toString(), flight.getFrom());
        allAirports.put(airportTo.toString(), flight.getTo());

        logger.info("Airport data from flight added to airports database.");
    }

    public synchronized String deleteFlight(String flightId) {
        boolean removed = flights.removeIf(fl -> flightId.equals(String.valueOf(fl.getId())));
        if (removed) {
            logger.info("Flight with id: " + flightId + " removed from database.");
            return "Flight with id: " + flightId + " removed from database.";
        } else {
            logger.info("Flight for deletion with id: " + flightId + " was not found.");
            return "Flight for deletion with id: " + flightId + " was not found.";
        }
    }

    public List<Airports> searchAirport(String airportSearchQuery) {
        List<Airports> foundAirports = new ArrayList<>();
        for (Map.Entry<String, Airports> entry : allAirports.entrySet()) {
            if (entry.getKey().toLowerCase().trim().contains(airportSearchQuery.toLowerCase().trim())) {
                foundAirports.add(entry.getValue());
            }
        }
        logger.info("Using search query: " + airportSearchQuery + " found airports: " + foundAirports);
        return foundAirports;
    }

    public SearchFlightResponse<Flights> searchFlights(SearchFlightRequest flight) {
        SearchFlightResponse result = new SearchFlightResponse(0, 0, new ArrayList<Flights>());

        if (flight.getFrom().equals(flight.getTo())) {
            logger.error("Tried to search flight with invalid data, flight from: " + flight.getFrom() + " flight to: " + flight.getTo());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid search data. From and To airports are equal.");
        }
        logger.info("Searched flight is " + flight);

        DateTimeFormatter flightDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Flights> foundFlights = flights.stream().filter(fl -> flight.getFrom().equals(fl.getFrom().getAirport()) &&
                flight.getTo().equals(fl.getTo().getAirport()) &&
                fl.getDepartureTime().format(flightDateFormatter).equals(flight.getDepartureDate())).toList();

        result.setTotalItems(foundFlights.size());
        result.setItems(foundFlights);
        result.setPage(foundFlights.size() / 10);
        return result;
    }

    public List<Flights> getFlights() {
        return flights;
    }

}
