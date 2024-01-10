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
    public static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    private static int lastResId = 1;
    public static void createReservation(){
        Customer customer = inputCustomer();
        Employee employee = inputEmployee();
        List<Service> selectedServices = inputServicesList();
        Reservation newReservation =createNewReservation(customer,employee,selectedServices);
        reservationList.add(newReservation);
    }

    private static Reservation createNewReservation(Customer inputCustomer, Employee inputEmployee, List<Service> inputListService){
        Reservation newReservation = new Reservation();
        newReservation.setReservationId("Res-" + String.format("%03d", lastResId));
        newReservation.setCustomer(inputCustomer);
        newReservation.setEmployee(inputEmployee);
        newReservation.setServices(inputListService);
        newReservation.setReservationPrice(newReservation.calculateReservationPrice(inputListService));
        newReservation.setWorkstage("In Process");
        lastResId++;
        System.out.println("Booking Berhasil!");
        System.out.println("Total Biaya Booking : Rp." + newReservation.calculateReservationPrice(inputListService));
        return newReservation;
    }

    private static Customer inputCustomer(){
        String inputCustomerId = "";
        Customer selectedCustomer = null;
        do{
            PrintService.showAllCustomer(personList);
            System.out.print("\nSilahkan Masukkan Customer Id: ");
            inputCustomerId = input.nextLine();
            selectedCustomer = findCustomerById(inputCustomerId);
            if(selectedCustomer!=null){
                return selectedCustomer;
            }
            else{
                System.out.print("Customer Id tidak ditemukan\n");
            }
        } while(selectedCustomer==null || !ValidationService.isNull(inputCustomerId));
        return selectedCustomer;
    }

    private static Customer findCustomerById(String customerId){
        for(Person person: personList){
            if(person instanceof Customer){
                Customer selectedCustomer = (Customer)person;
                if(selectedCustomer.getId().equals(customerId)){
                    return selectedCustomer;
                }
            }
        }
        return null;
    }

    private static Employee inputEmployee(){
        String inputEmployeeId = "";
        Employee selectedEmployee = null;
        do{
            PrintService.showAllEmployee(personList);
            System.out.print("\nSilahkan Masukkan Employee Id: ");
            inputEmployeeId = input.nextLine();
            selectedEmployee = findEmployeeById(inputEmployeeId);
            if(selectedEmployee!=null){
                return selectedEmployee;
            }
            else{
                System.out.print("Employee Id tidak ditemukan\n");
            }
        } while(selectedEmployee==null || !ValidationService.isNull(inputEmployeeId));
        return selectedEmployee;
    }

    private static Employee findEmployeeById(String employeeId){
        for(Person person: personList){
            if(person instanceof Employee){
                Employee selectedEmployee = (Employee)person;
                if(selectedEmployee.getId().equals(employeeId)){
                    return selectedEmployee;
                }
            }
        }
        return null;
    }

    private static List<Service> inputServicesList(){
        List<Service> selectedServicesList = new ArrayList();
        String inputServiceId = "";
        String inputKonfirmasi = "";
        PrintService.showAllServices(serviceList);
        boolean loop = true;
        do{
            System.out.print("\nSilahkan Masukkan Service Id: \n");
            inputServiceId = input.nextLine();
            if(findServiceById(inputServiceId)!=null && !ValidationService.isNull(inputServiceId)){
                selectedServicesList.add(findServiceById(inputServiceId));
                System.out.print("\nIngin pilih service yang lain (Y/T)?: \n");
                inputKonfirmasi = input.nextLine();
                switch(inputKonfirmasi.toUpperCase()){
                    case "Y": loop = true; break;
                    case "T": loop = false; break;
                }
            }
            else{
                System.out.print("Service Id tidak ditemukan\n");
            }
        } while(loop);
        return selectedServicesList;
    }

    private static Service findServiceById(String serviceId){
        for(Service selectedService : serviceList){
            if(selectedService.getServiceId().equals(serviceId)){
                return selectedService;
            }
        }
        return null;
    }

    public static void editReservationWorkstage(){
        String inputReservationId = "";
        Reservation selectedReservation = null;
        String inputWorkstage = "";
        PrintService.showRecentReservation(reservationList);
        boolean loop = true;

        System.out.print("\nSilahkan Masukkan Reservation Id: \n");
        inputReservationId = input.nextLine();
        selectedReservation = findReservationById(inputReservationId);
        if(findReservationById(inputReservationId)!=null && !ValidationService.isNull(inputReservationId)){
            do {
                System.out.print("\nPilih Workstage (In Process / Finish / Canceled / 0 untuk keluar) : \n");
                inputWorkstage = input.nextLine();
                if (inputWorkstage.equalsIgnoreCase("In Process") || inputWorkstage.equalsIgnoreCase("Finish") || inputWorkstage.equalsIgnoreCase("Canceled") && !inputWorkstage.equalsIgnoreCase("0")) {
                    selectedReservation.setWorkstage(inputWorkstage);
                    loop = false;
                } else if (inputWorkstage.equals("0")) {
                    loop = false;
                } else {
                    System.out.print("Input perintah tidak valid\n");
                }
            } while(loop);
        }
        else{
            System.out.print("Reservation yang dicari tidak tersedia\n");
        }
    }


    private static Reservation findReservationById(String resId){
        for(Reservation selectedReservation : reservationList){
            if(selectedReservation.getReservationId().equals(resId)){
                return selectedReservation;
            }
        }
        return null;
    }

    // Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan
}
