package io.codelex.flightplanner.test.service;

import io.codelex.flightplanner.repository.database.AirportsRepositoryDatabase;
import io.codelex.flightplanner.repository.database.FlightsRepositoryDatabase;
import io.codelex.flightplanner.test.service.TestService;

public class TestServiceDatabase implements TestService {
    private FlightsRepositoryDatabase flightsRepositoryDatabase;
    private AirportsRepositoryDatabase airportsRepositoryDatabase;

    public TestServiceDatabase(FlightsRepositoryDatabase flightsRepositoryDatabase, AirportsRepositoryDatabase airportsRepositoryDatabase) {
        this.flightsRepositoryDatabase = flightsRepositoryDatabase;
        this.airportsRepositoryDatabase = airportsRepositoryDatabase;
    }
    public void clearDatabase() {
        flightsRepositoryDatabase.deleteAll();
        airportsRepositoryDatabase.deleteAll();
    }
}
