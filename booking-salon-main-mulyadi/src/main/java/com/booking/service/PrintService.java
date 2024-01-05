package com.booking.service;

import java.util.List;
import java.util.stream.Collectors;

import com.booking.models.*;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public String printServices(List<Service> serviceList){
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public void showRecentReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
    }



    public void showAllCustomer(List<Person> personList){
        int num=1;
        System.out.println("|No| Id     |     Name   |  Address  |  Member   |  Wallet");
        System.out.println("====================================================");
        for (Person person : personList) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                System.out.printf("%d | %s | %10s | %10s | %10s | %10.0f\n", num, customer.getId(), customer.getName(),
                        customer.getAddress(), customer.getMember().getMembershipName(),
                        customer.getWallet());
                num++;
            }
        }
    }

    public void showAllEmployee(List<Person> personList){
        int num = 1;
        System.out.println("No| Id    |    Name    |  Address   | Experience");
        System.out.println("==================================================");
        for (Person person : personList) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                System.out.printf("%d | %s | %10s | %10s | %10d\n", num, employee.getId(),
                        employee.getName(), employee.getAddress(),
                        employee.getExperience());
                num++;
            }
        }
    }

    public void showHistoryReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.println("No| Id  |  Customer |  Employee  | Services  |   Price  |   Workstage");
        System.out.println("================================================================");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("%d | %s | %s | %s | %s | %.2f %s\n", num, reservation.getReservationId(),
                        reservation.getCustomer().getName(), reservation.getEmployee().getName(),
                        printServices(reservation.getServices()), reservation.getReservationPrice(),
                        reservation.getWorkstage());
                num++;
            }
        }
    }

    public void showAvailableService(List<Service> serviceList) {
        int num = 1;
        System.out.println("No|  Id  |  Name  | Price");
        System.out.println("=============================");
        for (Service service : serviceList) {
            System.out.printf("%d %s %s %.0f\n", num, service.getServiceId(),
                    service.getServiceName(), service.getPrice());
            num++;
        }
    }
}
