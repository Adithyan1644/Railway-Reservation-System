package model;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Train {
	
	 private final String trainNumber;
	    private final String name;
	    private final String source;
	    private final String destination;
	    private final LocalTime departureTime;
	    private final LocalTime arrivalTime;
	    private final int totalSeats;
	    private final AtomicInteger availableSeats;
	    private final double fare;
	    
	    public Train (String trainNumber, String name, String source, String destination, LocalTime departureTime,
	    		      LocalTime arrivalTime, int totalSeats, double fare) {
					this.trainNumber=trainNumber;
					this.name=name;
					this.source=source;
					this.destination=destination;
					this.departureTime=departureTime;
					this.arrivalTime=arrivalTime;
					this.totalSeats=totalSeats;
					this.availableSeats = new AtomicInteger(totalSeats);
					this.fare=fare;
	
}
	    
	    public String getTrainNumber() { return trainNumber; }
	    public String getName() { return name; }
	    public String getSource() { return source; }
	    public String getDestination() { return destination; }
	    public LocalTime getDepartureTime() { return departureTime; }
	    public LocalTime getArrivalTime() { return arrivalTime; }
	    public int getTotalSeats() { return totalSeats; }
	    public int getAvailableSeats() { return availableSeats.get(); }
	    public double getFare() { return fare; }
	    
	    
	    public boolean bookSeat() {
	        int current;
	        do {
	            current = availableSeats.get();
	            if (current <= 0) return false;
	        } while (!availableSeats.compareAndSet(current, current - 1));
	        return true;
	    }
	    
	    public void cancelSeat() {
	        availableSeats.incrementAndGet();
	    }

		@Override
		public String toString() {
			return String.format("%s | %s | %s → %s | Dep: %s | Arr: %s | ₹%.2f | Seats: %d/%d",
	                trainNumber, name, source, destination, departureTime, arrivalTime,
	                fare, getAvailableSeats(), totalSeats);
		}


}
        
       

