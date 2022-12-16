package com.example.bilabonnement.services;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.User;
import com.example.bilabonnement.models.UserRole;
import com.example.bilabonnement.repositories.UserRepository;

import java.util.List;
import java.util.Objects;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public User login(String username, String password) {
        // Får en bruger af deres username fra database
        User user = userRepository.getUserByUserName(username);

        if (user == null) {
            return null;
        }

        if (Objects.equals(user.getPassword(), password)) {
            return user;
        } else {
            return null;
        }
    }

    //Opretter en bruger
    public User createUser(String username, String password, int roleID, int locationID) {

        User newUser = new User(-1,
                username,
                password,
                UserRole.values()[roleID],
                locationID);

        return userRepository.create(newUser);
    }


    public User getUser(int id) {
        return userRepository.getCarByID(id);
    }


    public List<User> getAllUsers() {
        return userRepository.getAllCars();
    }


    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }


    public void updateUser(int id, String username, int roleID, int locationID) {
        User user = userRepository.getCarByID(id);
        user.setUsername(username);
        user.setRole(UserRole.values()[roleID]);
        user.setLocationId(locationID);
        userRepository.update(user);
    }


    public void updateUser(int id, String username, String password, int roleID, int locationID) {

        User user = userRepository.getCarByID(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.values()[roleID]);
        user.setLocationId(locationID);

        userRepository.update(user);
    }
}
