package com.watcher.app.the_watcher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watcher.app.the_watcher.dto.User;
import com.watcher.app.the_watcher.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public void savePerson(User user){
        userRepo.savePerson(user);
    }

    // public User getUserByUsername(String username){
    //     return userRepo.getUserByUsername(username);
    // }

    public String getPasswordbyUsername(String username){
        return userRepo.getPasswordByUsername(username);
    }

    public void deleteUserByUsername(String username){
        userRepo.deleteUserByUsername(username);
    }

    public boolean userExist(String username){
        return userRepo.userExist(username);

    }

    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

}
