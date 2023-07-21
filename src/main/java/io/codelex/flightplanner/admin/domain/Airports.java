package io.codelex.flightplanner.admin.domain;

import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Airports {
    @NotNull
    @NotEmpty
    private String country;
    @NotNull
    @NotEmpty
    private String city;
    @NotNull
    @NotEmpty
    private String airport;

    public Airports(@NotNull String country, @NotNull String city, @NotNull String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public String toString() {
        return "Airports{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", airport='" + airport + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airports airports = (Airports) o;
        return Objects.equals(country, airports.country) && Objects.equals(city, airports.city) && Objects.equals(airport, airports.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}
