package Util;

import java.util.ArrayList;
import java.util.List;

public class SeatAllocator {
    public static List<String> allocateSeats(int numberOfSeats) {
        List<String> seats = new ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            seats.add("S" + i);   // Simplified: in real system, track per train
        }
        return seats;
    }
}