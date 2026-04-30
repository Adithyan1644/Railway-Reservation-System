package Data;


import java.util.concurrent.ConcurrentHashMap;

import model.Ticket;
import model.Train;
import model.User;

public class DataStore {
    private static DataStore instance;
    private final ConcurrentHashMap<String, Train> trains = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Ticket> tickets = new ConcurrentHashMap<>();

    private DataStore() {}

    public static DataStore getInstance() {
        if (instance == null) {
            synchronized (DataStore.class) {
                if (instance == null) {
                    instance = new DataStore();
                }
            }
        }
        return instance;
    }

    public ConcurrentHashMap<String, Train> getTrains() { return trains; }
    public ConcurrentHashMap<String, User> getUsers() { return users; }
    public ConcurrentHashMap<String, Ticket> getTickets() { return tickets; }
}