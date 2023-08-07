package io.codelex.flightplanner.repository.database;

import io.codelex.flightplanner.admin.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportsRepositoryDatabase extends JpaRepository<Airport, Long> {
    @Query("SELECT a FROM Airport a WHERE LOWER(a.airport) LIKE %:searchTerm% OR LOWER(a.city) LIKE %:searchTerm% OR LOWER(a.country) LIKE %:searchTerm%")
    List<Airport> searchAirports(@Param("searchTerm") String searchTerm);
}
