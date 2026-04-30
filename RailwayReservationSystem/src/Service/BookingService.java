package Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import Data.DataStore;
import Util.PNRGenerator;
import Util.SeatAllocator;
import exceptions.BookingException;
import model.BookingStatus;
import model.Ticket;
import model.Train;
import model.User;

public class BookingService {
    private final static DataStore dataStore = DataStore.getInstance();
    private final static ReentrantLock bookingLock = new ReentrantLock();

    public static Ticket bookTicket(Train train, User user, LocalDate travelDate, int numberOfSeats)
            throws BookingException {
        bookingLock.lock();
        try {
            if (train.getAvailableSeats() < numberOfSeats) {
                throw new BookingException("Only " + train.getAvailableSeats() + " seats available");
            }
            for (int i = 0; i < numberOfSeats; i++) {
                if (!train.bookSeat()) {
                    throw new BookingException("Failed to book seat, try again");
                }
            }
            List<String> seatNumbers = SeatAllocator.allocateSeats(numberOfSeats);
            double totalFare = numberOfSeats * train.getFare();
            String pnr = PNRGenerator.generatePNR();
            Ticket ticket = new Ticket(pnr, train, user, LocalDate.now(), travelDate,
                    seatNumbers, totalFare, BookingStatus.CONFIRMED);
            dataStore.getTickets().put(pnr, ticket);
            return ticket;
        } finally {
            bookingLock.unlock();
        }
    }

    public static boolean cancelTicket(String pnr) {
        Ticket ticket = dataStore.getTickets().get(pnr);
        if (ticket == null || ticket.getStatus() == BookingStatus.CANCELLED) {
            return false;
        }
        // Simplified: cancel only 1 seat per ticket (for demo)
        ticket.getTrain().cancelSeat();
        ticket.setStatus(BookingStatus.CANCELLED);
        return true;
    }

    public  Optional<Ticket> getTicketByPNR(String pnr) {
        return Optional.ofNullable(dataStore.getTickets().get(pnr));
    }

    public List<Ticket> getTicketsByUser(String userId) {
        return dataStore.getTickets().values().stream()
                .filter(t -> t.getUser().getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public  List<Ticket> getPastBookingsByUser(String userId) {
        LocalDate today = LocalDate.now();
        return dataStore.getTickets().values().stream()
                .filter(t -> t.getUser().getUserId().equals(userId))
                .filter(t -> t.getTravelDate().isBefore(today))
                .collect(Collectors.toList());
    }
}