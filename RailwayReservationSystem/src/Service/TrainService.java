package Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Data.DataStore;
import exceptions.TrainNotFoundException;
import model.Train;

public class TrainService {
	
	private final DataStore datastore = DataStore.getInstance();
	
	public List<Train> searchTrain(String source, String destination){
		return datastore.getTrains().values().stream()
				.filter(t->t.getSource().equalsIgnoreCase(source))
				.filter(t->t.getDestination().equalsIgnoreCase(destination))
				.filter(t->t.getAvailableSeats()>0)
				.collect(Collectors.toList());
	}
	
	public List<Train> filterTrains(Predicate<Train> predicate) {
        return datastore.getTrains().values().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
	
	public Train getTrainByNumber(String trainNumber) throws TrainNotFoundException {
        Train train = datastore.getTrains().get(trainNumber);
        if (train == null) {
            throw new TrainNotFoundException("Train number " + trainNumber + " not found");
        }
        return train;
    }
	
	  public Optional<Train> findTrainByNumber(String trainNumber) {
	        return Optional.ofNullable(datastore.getTrains().get(trainNumber));
	    }

}
