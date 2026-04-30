package Service;

import java.util.Optional;

import Data.DataStore;
import model.User;

public class UserService {
    private final DataStore dataStore = DataStore.getInstance();

    public User registerUser(String userId, String name, String email, String phone) {
        User user = new User(userId, name, email, phone);
        dataStore.getUsers().put(userId, user);
        return user;
    }

    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(dataStore.getUsers().get(userId));
    }
}