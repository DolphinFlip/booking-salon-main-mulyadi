package com.booking.service;


import com.booking.models.*;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static PrintService printService = new PrintService();
    private static Scanner input = new Scanner(System.in);

    public static void createReservation(List<Reservation> reservationList){
        printService.showAllCustomer(personList);
        System.out.println("CustomerId untuk reservasi: ");
        String customerId = input.nextLine();

        Customer customer = getCustomerByCustomerId(customerId);

        while (customer == null) {
            System.out.println("CustomerId yang dicari tidak tersedia");
            System.out.println("CustomerId untuk reservasi: ");
            customerId = input.nextLine();
            customer = getCustomerByCustomerId(customerId);
        }

        printService.showAllEmployee(personList);
        System.out.println("EmployeeId untuk reservasi: ");
        String employeeId = input.nextLine();

        Employee employee = getEmployeeByEmployeeId(employeeId);

        while (employee == null) {
            System.out.println("EmployeeId yang dicari tidak tersedia");
            System.out.println("EmployeeId untuk reservasi: ");
            employeeId = input.nextLine();
            employee = getEmployeeByEmployeeId(employeeId);
        }

        List<Service> selectedServices = selectServices();

        String workstage = "In Process";
        int resId=0;
        for (Reservation reservation : reservationList) {
            resId++;
        }
        String reservationId = "Res-" + String.format("%04d", reservationList.size()+1);
        double reservationPrice = calculateReservationPrice(selectedServices, customer);

        Reservation reservation = new Reservation(reservationId, customer, employee, selectedServices,
                workstage);
        reservation.setReservationPrice(reservationPrice);

        reservationList.add(reservation);
        System.out.println("Pembuatan reservasi berhasil");
    }


    public static void editReservationWorkstage(List<Reservation> reservationList){
        printService.showRecentReservation(reservationList);
        System.out.println("Input IdReservasi untuk mengubah workstage: ");
        String reservationId = input.nextLine();

        Reservation reservation = findReservationById(reservationId, reservationList);

        while (reservation == null) {
            System.out.println("Reservasi yang dicari tidak tersedia!");
            System.out.println("Input IdReservasi untuk mengubah workstage: (Ketik Exit untuk keluar)");
            reservationId = input.nextLine();

            if (reservationId.equalsIgnoreCase("Exit"))
                break;

            reservation = findReservationById(reservationId, reservationList);

            if (reservation.getWorkstage().equals("In Process"))
                break;

            System.out.println("Reservasi ini selesai");
        }

        if (reservation != null && !reservationId.equals("Exit")) {
            String newWorkstage = selectedWorkStage();

            reservation.setWorkstage(newWorkstage);
            System.out.println("Workstage berhasil diperbarui!");
        } else if (!reservationId.equals("Exit")) {
            System.out.println("Reservasi tidak tersedia");
        }
    }

    private static List<Service> selectServices() {
        printService.showAvailableService(serviceList);
        List<Service> selectedServices = new ArrayList<>();
        boolean loop = true;

        do {
            System.out.println("Id Service untuk reservasi: (Input Exit untuk berhenti memilih)");
            String serviceIdsInput = input.nextLine();

            if (serviceIdsInput.equals("Exit"))
                break;

            Service service = findServiceById(serviceIdsInput, serviceList);

            if (service == null) {
                System.out.println("Service yang dicari tidak tersedia");
                continue;
            }

            if (!selectedServices.contains(service))
                selectedServices.add(service);
            else
                System.out.println("Service sudah dipilih!");

        } while (loop);

        return selectedServices;
    }

    private static double calculateReservationPrice(List<Service> selectedServices, Customer customer) {
        double totalPrice = selectedServices.stream()
                .mapToDouble(Service::getPrice)
                .sum();

        if (customer != null && customer.getMember() != null) {
            switch (customer.getMember().getMembershipName().toLowerCase()) {
                case "silver":
                    totalPrice *= 0.95;
                    break;
                case "gold":
                    totalPrice *= 0.90;
                    break;
            }
        }

        return totalPrice;
    }

    public static Customer getCustomerByCustomerId(String customerId){
        return personList.stream()
                .filter(person -> person instanceof Customer && person.getId().equals(customerId))
                .map(person -> (Customer) person)
                .findFirst()
                .orElse(null);
    }

    private static Employee getEmployeeByEmployeeId(String employeeId) {
        return personList.stream()
                .filter(person -> person instanceof Employee && person.getId().equals(employeeId))
                .map(person -> (Employee) person)
                .findFirst()
                .orElse(null);
    }
    private static Reservation findReservationById(String reservationId, List<Reservation> reservationList) {
        return reservationList.stream()
                .filter(reservation -> reservation.getReservationId().equals(reservationId))
                .findFirst()
                .orElse(null);
    }

    private static Service findServiceById(String serviceId, List<Service> serviceList) {
        return serviceList.stream()
                .filter(service -> service.getServiceId().equals(serviceId))
                .findFirst()
                .orElse(null);
    }

    public static String selectedWorkStage() {
        int choice = 0;
        String workstage = "";
        System.out.printf("Enter new workstage:\n1. %s\n2. %s\n3. %s\nPilihan: ", "In Process", "Finish",
                "Canceled");
        choice = Integer.valueOf(input.nextLine());
        while (choice > 3 || choice < 1) {
            System.out.println("WorkStage tidak valid. Coba lagi:");
            choice = Integer.valueOf(input.nextLine());
        }
        switch (choice) {
            case 1:
                workstage = "In Process";
                break;
            case 2:
                workstage = "Finish";
                break;
            case 3:
                workstage = "Cancel";
                break;
        }
        return workstage;
    }

    // Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan
}
