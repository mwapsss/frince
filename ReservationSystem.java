import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// create object class reservation
class Reservation {
    private static int counter = 1;
    private final int reservationNumber;
    private final String name;
    private final int adults;
    private final int children;
    private final String date;
    private final String time;

    // declare object Reservation
    public Reservation(String name, int adults, int children) {
        this.reservationNumber = counter++;
        this.name = name;
        this.adults = adults;
        this.children = children;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy"); // date formatting
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a"); // time formatting

        this.date = now.format(dateFormat);
        this.time = now.format(timeFormat).toLowerCase();
    }

    // method for getting reservation number
    public int getReservationNumber() {
        return reservationNumber;
    }

    // method for getting name
    public String getName() {
        return name;
    }

    // method for getting adults
    public int getAdults() {
        return adults;
    }

    // method for getting children
    public int getChildren() {
        return children;
    }

    // method for getting date
    public String getDate() {
        return date;
    }

    // method for getting time
    public String getTime() {
        return time;
    }

    // // method for computing total
    public int getSubtotal() {
        return (adults * 500) + (children * 300);
    }

    @Override
    public String toString() {
        return reservationNumber + "  " + date + "  " + time + "  " + name +
               "  Adults: " + adults + "  Children: " + children;
    }
}

// main class
public class ReservationSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Reservation> activeReservations = new ArrayList<>(); // created array list with variable activeReservations
    private static final ArrayList<Reservation> allReservations = new ArrayList<>(); // created array list with variable allReservations

    public static void main(String[] args) {
        String choice = "";

        while (choice != "e") {
            System.out.println();
            System.out.println("A. add reservation");
            System.out.println("B. view current reservations");
            System.out.println("C. delete reservation");
            System.out.println("D. generate report");
            System.out.println("F. exit");
            System.out.print("choose an option: ");
            choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "a" -> addReservation();
                case "b" -> viewReservations();
                case "c" -> deleteReservation();
                case "d" -> generateReport();
                case "e" -> {
                    System.out.println("Thank you for using");
                    System.exit(0);
                }
                default -> System.out.println("invalid choice.");
            }
        }
    }

    // method for adding reservation
    public static void addReservation() {
        System.out.println();
        System.out.print("enter name: ");
        String name = scanner.nextLine();

        int adults;
        do {

            //  asks for user how many adults will attend
            System.out.print("enter number of adults (at least 1): ");
            adults = scanner.nextInt();

            // condition to check if below one adult is inputted
            if (adults < 1) {
                System.out.println("there must be at least 1 adult.");
            }

            // loops as long as adult is not less than one
        } while (adults < 1);

        // asks user how many children will come
        System.out.print("enter number of children: ");
        int children = scanner.nextInt();
        scanner.nextLine(); // debuffer

        Reservation newRes = new Reservation(name, adults, children);
        activeReservations.add(newRes); // add following data to array list
        allReservations.add(newRes); // add following data to array list

        System.out.println();
        System.out.println("reservation added!");
        System.out.println(newRes);
    }

    // method for viewing reservations
    public static void viewReservations() {
        System.out.println();

        // check if array list is emptyss
        if (activeReservations.isEmpty()) {
            System.out.println("no active reservations.");
        } else {

            // loops inside the reseravation array list
            for (Reservation res : activeReservations) {
                System.out.println(res);
            }
        }
    }

    // method for deleting a reservation
    public static void deleteReservation() {

        // check if array list is empty
        if (activeReservations.isEmpty()) {
            System.out.println("\nno reservations to delete.");
            return;
        }

        // loops inside the array list and prints out the necessary values
        for (Reservation res : activeReservations) {
            System.out.println(res);
        }

        // asks for the reservation number to delete
        System.out.print("\n enter reservation number to delete: ");
        int num = scanner.nextInt();
        scanner.nextLine(); // debuffer

        Reservation found = null;
        for (Reservation res : activeReservations) {
            if (res.getReservationNumber() == num) {
                found = res;
                break;
            }
        }

        // if variable found is not empty then it deletes it
        if (found != null) {
            activeReservations.remove(found);
            System.out.println("reservation deleted.");
        } else {
            System.out.println("reservation not found.");
        }
    }

    // method for generating report
    public static void generateReport() {
        System.out.println();

        // checks if array list is empty
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

            totalAdults += res.getAdults(); // add to total of adults
            totalChildren += res.getChildren(); // add to total of children
            grandTotal += res.getSubtotal(); // add to total of fee
        }

        System.out.println();
        System.out.println("Total Adults: " + totalAdults);
        System.out.println("Total Children: " + totalChildren);
        System.out.println("Grand Total: ₱" + grandTotal);
    }
}
