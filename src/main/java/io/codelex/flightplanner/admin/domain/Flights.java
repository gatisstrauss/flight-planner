package io.codelex.flightplanner.admin.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flights {

    private Airports from;

    private Airports to;

    private String company;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private int id;

    public Flights(Airports from, Airports to,
                   String company, LocalDateTime departureTime, LocalDateTime arrivalTime,
                   int id) {
        this.from = from;
        this.to = to;
        this.company = company;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.id = id;
    }

    public Airports getFrom() {
        return from;
    }

    public void setFrom(Airports from) {
        this.from = from;
    }

    public Airports getTo() {
        return to;
    }

    public void setTo(Airports to) {
        this.to = to;
    }

    public String getCompany() {
        return company;
    }

    public void setCarrier(String carrier) {
        this.company = carrier;
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

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Flights{" +
                "from=" + from +
                ", to=" + to +
                ", company='" + company + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flights flights = (Flights) o;
        return id == flights.id && Objects.equals(from, flights.from) && Objects.equals(to, flights.to) && Objects.equals(company, flights.company) && Objects.equals(departureTime, flights.departureTime) && Objects.equals(arrivalTime, flights.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, company, departureTime, arrivalTime, id);
    }
}
