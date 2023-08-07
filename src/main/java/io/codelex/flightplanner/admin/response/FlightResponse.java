package io.codelex.flightplanner.admin.response;

import io.codelex.flightplanner.admin.domain.Airport;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightResponse {
    private Long id;
    private Airport from;
    private Airport to;
    private String company;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public FlightResponse(Long id, String from, String to, String company, Airport departureTime, Airport arrivalTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.company = company;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightResponse that = (FlightResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(company, that.company) && Objects.equals(departureTime, that.departureTime) && Objects.equals(arrivalTime, that.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, company, departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "FlightResponse{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", company='" + company + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
