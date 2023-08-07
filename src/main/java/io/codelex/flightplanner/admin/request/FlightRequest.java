package io.codelex.flightplanner.admin.request;

import io.codelex.flightplanner.admin.domain.Airports;

import java.util.Objects;

public class FlightRequest {
    private Airports from;

    private Airports to;

    private String company;

    private String departureTime;

    private String arrivalTime;

    private int id;

    public FlightRequest(Airports from, Airports to, String company, String departureTime, String arrivalTime, int id) {
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

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FlightRequest{" +
                "from=" + from +
                ", to=" + to +
                ", company='" + company + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightRequest that = (FlightRequest) o;
        return id == that.id && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(company, that.company) && Objects.equals(departureTime, that.departureTime) && Objects.equals(arrivalTime, that.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, company, departureTime, arrivalTime, id);
    }

}
