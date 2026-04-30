package model;

import java.time.LocalDate;
import java.util.List;

public class Ticket {
    private final String pnr;
    private final Train train;
    private final User user;
    private final LocalDate bookingDate;
    private final LocalDate travelDate;
    private final List<String> seatNumbers;
    private final double totalFare;
    private BookingStatus status;

    public Ticket(String pnr, Train train, User user, LocalDate bookingDate, LocalDate travelDate,
           List<String> seatNumbers, double totalFare, BookingStatus status) {
        this.pnr = pnr;
        this.train = train;
        this.user = user;
        this.bookingDate = bookingDate;
        this.travelDate = travelDate;
        this.seatNumbers = seatNumbers;
        this.totalFare = totalFare;
        this.status = status;
    }

    public String getPnr() { return pnr; }
    public Train getTrain() { return train; }
    public User getUser() { return user; }
    public LocalDate getTravelDate() { return travelDate; }
    public List<String> getSeatNumbers() { return seatNumbers; }
    public double getTotalFare() { return totalFare; }
    public BookingStatus getStatus() { return status; }

    public void setStatus(BookingStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("PNR: %s | Train: %s | %s | Date: %s | Seats: %s | ₹%.2f | %s",
                pnr, train.getTrainNumber(), user.getName(), travelDate, seatNumbers, totalFare, status);
    }
}