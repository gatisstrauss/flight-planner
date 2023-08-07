package io.codelex.flightplanner.client.service;

import io.codelex.flightplanner.admin.domain.Airport;
import io.codelex.flightplanner.admin.domain.Flight;
import io.codelex.flightplanner.admin.request.FlightRequest;
import io.codelex.flightplanner.admin.response.FlightResponse;
import io.codelex.flightplanner.client.request.SearchFlightRequest;
import io.codelex.flightplanner.client.response.SearchFlightResponse;
import io.codelex.flightplanner.repository.database.AirportsRepositoryDatabase;
import io.codelex.flightplanner.repository.database.FlightsRepositoryDatabase;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.codelex.flightplanner.admin.service.AdminServiceDatabase.logger;

public class ClientServiceDatabase implements ClientService{
    private final FlightsRepositoryDatabase flightsRepositoryDatabase;
    private final AirportsRepositoryDatabase airportsRepositoryDatabase;

    public ClientServiceDatabase(FlightsRepositoryDatabase flightsRepositoryDatabase, AirportsRepositoryDatabase airportsRepositoryDatabase) {
        this.flightsRepositoryDatabase = flightsRepositoryDatabase;
        this.airportsRepositoryDatabase = airportsRepositoryDatabase;
    }


    public List<Airport> searchAirport(String airportSearchQuery) {
        String formattedSearchQuery = airportSearchQuery.trim().toLowerCase();
        return airportsRepositoryDatabase.searchAirports("%" + formattedSearchQuery + "%");
    }

    public FlightResponse getFlightById(String flightId) {
        Optional<Flight> foundFlight = flightsRepositoryDatabase.findById((long) Integer.parseInt(flightId));

        if (foundFlight.isPresent()) {
            Flight flight = foundFlight.get();

            return createFlightResponse(
                    flight.getFrom(),
                    flight.getTo(),
                    flight.getCompany(),
                    flight.getDepartureTime().toString(),
                    flight.getArrivalTime().toString(),
                    Math.toIntExact(flight.getId())
            );
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no flight with the given id.");
    }

    @Override
    public SearchFlightResponse<Flight> searchFlights(SearchFlightRequest flight) {
        searchFlightsResponse<Flight> result = new SearchedFlightsResponse<>(0, 0, new ArrayList<>());

        if (flight.getFrom().equals(flight.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid search data. From and To airports are equal.");
        }

        DateTimeFormatter flightDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Flight> foundFlights = flightsRepositoryDatabase.findAll().stream()
                .filter(fl -> flight.getFrom().equals(fl.getFrom().getAirport()) &&
                        flight.getTo().equals(fl.getTo().getAirport()) &&
                        fl.getDepartureTime().format(flightDateFormatter).equals(flight.getDepartureDate()))
                .collect(Collectors.toList());

        result.setTotalItems(foundFlights.size());
        result.setItems(foundFlights);
        result.setPage(foundFlights.size() / 10);
        return result;
    }


    @Transactional
    public synchronized FlightResponse saveFlight(FlightRequest flightRequest) {
        Flight flightToSave = mapToFlight(flightRequest);

        Airport fromAirport = flightToSave.getFrom();
        Airport toAirport = flightToSave.getTo();

        if (!airportsRepositoryDatabase.existsById(fromAirport.getId())) {
            airportsRepositoryDatabase.save(fromAirport);
            logger.info("Airport added to database: " + fromAirport);
        }

        if (!airportsRepositoryDatabase.existsById(toAirport.getId())) {
            airportsRepositoryDatabase.save(toAirport);
            logger.info("Airport added to database: " + toAirport);
        }

        Flight savedFlight = flightsRepositoryDatabase.save(flightToSave);
        logger.info("Flight saved in database: " + savedFlight);

        return createFlightResponse(
                savedFlight.getFrom(),
                savedFlight.getTo(),
                savedFlight.getCompany(),
                formatLocalDateTimeToString(savedFlight.getDepartureTime()),
                formatLocalDateTimeToString(savedFlight.getArrivalTime()),
                Math.toIntExact(savedFlight.getId())
        );
    }


    @Transactional
    public String deleteFlight(String flightId) {
        flightsRepositoryDatabase.deleteById((long) Integer.parseInt(flightId));
        return "Flight with id: " + flightId + " removed from the database.";
    }

    private FlightResponse createFlightResponse(Airport from, Airport to, String company, String departureTime, String arrivalTime, int flightId) {
        return new FlightResponse(from, to, company, departureTime, arrivalTime, flightId);
    }
}

