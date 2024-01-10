package com.booking.models;

import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    //   workStage (In Process, Finish, Canceled)

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
            String workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice(this.services);
        this.workstage = workstage;
    };

    public double calculateReservationPrice(List<Service> selectedServices){
        double totalPrice = 0d;
        for(Service service : selectedServices){
            totalPrice += service.getPrice();
        }
        if (this.customer.getMember().getMembershipName().equals("Silver")) {
            totalPrice *= 0.95;
        } else if (this.customer.getMember().getMembershipName().equals("Gold")) {
            totalPrice *= 0.9;
        }
        return totalPrice;
    }
}
