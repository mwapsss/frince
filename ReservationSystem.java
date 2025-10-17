import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Reservation {
    private static int counter = 1;
    private final int reservationNumber;
    private final String name;
    private final int adults;
    private final int children;
    private final String date;
    private final String time;

    public Reservation(String name, int adults, int children) {
        this.reservationNumber = counter++;
        this.name = name;
        this.adults = adults;
        this.children = children;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");

        this.date = now.format(dateFormat);
        this.time = now.format(timeFormat).toLowerCase();
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public String getName() {
        return name;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getSubtotal() {
        return (adults * 500) + (children * 300);
    }

    @Override
    public String toString() {
        return reservationNumber + "  " + date + "  " + time + "  " + name +
               "  Adults: " + adults + "  Children: " + children;
    }
}

public class ReservationSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Reservation> activeReservations = new ArrayList<>();
    private static final ArrayList<Reservation> allReservations = new ArrayList<>();

    public static void main(String[] args) {
        char choice = 'a';

        while (choice != 'f') {
            System.out.println();
            System.out.println("[a] add reservation");
            System.out.println("[b] view current reservations");
            System.out.println("[c] delete reservation");
            System.out.println("[d] generate report");
            System.out.println("[f] exit");
            System.out.print("choose an option: ");
            choice = scanner.next().charAt(0);
            scanner.nextLine();

            switch (choice) {
                case 'a' -> addReservation();
                case 'b' -> viewReservations();
                case 'c' -> deleteReservation();
                case 'd' -> generateReport();
                case 'f' -> exitSystem();
                default -> System.out.println("invalid choice.");
            }
        }
    }

    public static void addReservation() {
        System.out.println();
        System.out.print("enter name: ");
        String name = scanner.nextLine();

        int adults;
        do {
            System.out.print("enter number of adults (at least 1): ");
            adults = scanner.nextInt();
            if (adults < 1) {
                System.out.println("there must be at least 1 adult.");
            }
        } while (adults < 1);

        System.out.print("enter number of children: ");
        int children = scanner.nextInt();
        scanner.nextLine();

        Reservation newRes = new Reservation(name, adults, children);
        activeReservations.add(newRes);
        allReservations.add(newRes);

        System.out.println();
        System.out.println("reservation added!");
        System.out.println(newRes);
    }

    public static void viewReservations() {
        System.out.println();
        if (activeReservations.isEmpty()) {
            System.out.println("no active reservations.");
        } else {
            for (Reservation res : activeReservations) {
                System.out.println(res);
            }
        }
    }

    public static void deleteReservation() {
        System.out.println();
        if (activeReservations.isEmpty()) {
            System.out.println("no reservations to delete.");
            return;
        }

        for (Reservation res : activeReservations) {
            System.out.println(res);
        }

        System.out.println();
        System.out.print("enter reservation number to delete: ");
        int num = scanner.nextInt();
        scanner.nextLine();

        Reservation found = null;
        for (Reservation res : activeReservations) {
            if (res.getReservationNumber() == num) {
                found = res;
                break;
            }
        }

        if (found != null) {
            activeReservations.remove(found);
            System.out.println("reservation deleted.");
        } else {
            System.out.println("reservation not found.");
        }
    }

    public static void generateReport() {
        System.out.println();
        if (allReservations.isEmpty()) {
            System.out.println("no reservations found.");
            return;
        }

        int totalAdults = 0;
        int totalChildren = 0;
        int grandTotal = 0;

        System.out.printf("%-5s %-15s %-10s %-15s %-10s %-10s %-10s\n",
                "No.", "Date", "Time", "Name", "Adults", "Children", "Subtotal");

        for (Reservation res : allReservations) {
            System.out.printf("%-5d %-15s %-10s %-15s %-10d %-10d ₱%-10d\n",
                    res.getReservationNumber(), res.getDate(), res.getTime(),
                    res.getName(), res.getAdults(), res.getChildren(), res.getSubtotal());

            totalAdults += res.getAdults();
            totalChildren += res.getChildren();
            grandTotal += res.getSubtotal();
        }

        System.out.println();
        System.out.println("Total Adults: " + totalAdults);
        System.out.println("Total Children: " + totalChildren);
        System.out.println("Grand Total: ₱" + grandTotal);
    }

    public static void exitSystem() {
        System.out.println();
        System.out.println("Thank you!");
    }
}

