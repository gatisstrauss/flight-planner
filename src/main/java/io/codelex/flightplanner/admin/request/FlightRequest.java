package io.codelex.flightplanner.admin.request;

import io.codelex.flightplanner.admin.domain.Airport;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class FlightRequest {
    @NotNull
    private Airport from;

    @NotNull
    private Airport to;

    @NotEmpty
    private String company;

    @NotEmpty
    private String departureTime;

    @NotEmpty
    private String arrivalTime;

    public FlightRequest(@NotNull Airport from, @NotNull Airport to, String company, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.company = company;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightRequest that = (FlightRequest) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(company, that.company) && Objects.equals(departureTime, that.departureTime) && Objects.equals(arrivalTime, that.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, company, departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "FlightRequest{" +
                "from=" + from +
                ", to=" + to +
                ", company='" + company + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}
