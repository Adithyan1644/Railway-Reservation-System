package App;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Data.DataStore;
import Service.BookingService;
import Service.TrainService;
import Service.UserService;
import exceptions.BookingException;
import exceptions.TrainNotFoundException;
import model.Ticket;
import model.Train;
import model.User;



public class MainApp {
    private static final TrainService trainService = new TrainService();
    private static final UserService userService = new UserService();
    private static final BookingService bookingService = new BookingService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData();
        BookingService bookingService = new BookingService();
        System.out.println("===== Railway Reservation System =====");
        while (true) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> searchTrains();
                case 2 -> registerUser();
                case 3 -> bookTicket();
                case 4 -> cancelTicket();
                case 5 -> viewTicket();
                case 6 -> viewUserTickets();
                case 7 -> viewPastBookings();
                case 8 -> {
                    System.out.println("Thank you for using the system!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n1. Search Trains");
        System.out.println("2. Register User");
        System.out.println("3. Book Ticket");
        System.out.println("4. Cancel Ticket");
        System.out.println("5. View Ticket by PNR");
        System.out.println("6. View My Tickets (All)");
        System.out.println("7. View Past Bookings");
        System.out.println("8. Exit");
        System.out.print("Enter choice: ");
    }

    private static void searchTrains() {
        System.out.print("Enter Source: ");
        String src = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String dest = scanner.nextLine();
        List<Train> trains = trainService.searchTrain(src, dest);
        if (trains.isEmpty()) {
            System.out.println("No trains found for this route.");
        } else {
            trains.forEach(System.out::println);
        }
    }

    private static void registerUser() {
        System.out.print("User ID: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        User user = userService.registerUser(id, name, email, phone);
        System.out.println("User registered: " + user);
    }

    private static void bookTicket() {
        System.out.print("Enter Train Number: ");
        String trainNo = scanner.nextLine();
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Number of seats: ");
        int seats = Integer.parseInt(scanner.nextLine());
        System.out.print("Travel Date (yyyy-mm-dd): ");
        LocalDate travelDate = LocalDate.parse(scanner.nextLine());

        try {
            Train train = trainService.getTrainByNumber(trainNo);
            Optional<User> userOpt = userService.findUserById(userId);
            if (userOpt.isEmpty()) {
                System.out.println("User not found. Please register first.");
                return;
            }
            Ticket ticket = BookingService.bookTicket(train, userOpt.get(), travelDate, seats);
            System.out.println("Booking successful!");
            System.out.println(ticket);
        } catch (TrainNotFoundException | BookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    private static void cancelTicket() {
        System.out.print("Enter PNR: ");
        String pnr = scanner.nextLine();
        boolean cancelled = BookingService.cancelTicket(pnr);
        if (cancelled) {
            System.out.println("Ticket cancelled successfully.");
        } else {
            System.out.println("PNR not found or already cancelled.");
        }
    }

    private static void viewTicket() {
        System.out.print("Enter PNR: ");
        String pnr = scanner.nextLine();
        Optional<Ticket> ticket = bookingService.getTicketByPNR(pnr);
        if (ticket.isPresent()) {
            System.out.println(ticket.get());
        } else {
            System.out.println("Ticket not found.");
        }
    }

    private static void viewUserTickets() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        List<Ticket> tickets = bookingService.getTicketsByUser(userId);
        if (tickets.isEmpty()) {
            System.out.println("No tickets found for this user.");
        } else {
            tickets.forEach(System.out::println);
        }
    }

    private static void viewPastBookings() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        List<Ticket> pastTickets = bookingService.getPastBookingsByUser(userId);
        if (pastTickets.isEmpty()) {
            System.out.println("No past bookings found for this user.");
        } else {
            System.out.println("=== Past Bookings ===");
            pastTickets.forEach(System.out::println);
        }
    }

    private static void loadSampleData() {
        DataStore ds = DataStore.getInstance();
        ds.getTrains().put("12345", new Train("12345", "Shatabdi Exp", "Delhi", "Mumbai",
                LocalTime.of(6, 0), LocalTime.of(14, 0), 100, 1250.0));
        ds.getTrains().put("67890", new Train("67890", "Duronto Exp", "Kolkata", "Chennai",
                LocalTime.of(20, 30), LocalTime.of(12, 0), 80, 1500.0));
        ds.getTrains().put("11111", new Train("11111", "Rajdhani Exp", "Delhi", "Howrah",
                LocalTime.of(16, 0), LocalTime.of(8, 0), 120, 1800.0));
        System.out.println("Sample trains loaded.");
    }
}