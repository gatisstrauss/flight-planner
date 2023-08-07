package io.codelex.flightplanner.repository.database;

import io.codelex.flightplanner.admin.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightsRepositoryDatabase extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE " +
            "LOWER(f.from.airport) LIKE %:searchTerm% OR " +
            "LOWER(f.to.airport) LIKE %:searchTerm% OR " +
            "LOWER(f.company) LIKE %:searchTerm% OR " +
            "f.departureTime = :searchDateTime OR " +
            "f.arrivalTime = :searchDateTime")
    List<Flight> searchFlights(@Param("searchTerm") String searchTerm, @Param("searchDateTime") LocalDateTime searchDateTime);
}
