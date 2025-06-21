package com.Utech.journalApp.services;

import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.Repository.UserRepository;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired //this is used to inject the UserRepository bean into this class
    private UserRepository userRepository;
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);

    }
    // flow of service repostirt
    public UserEntity findById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
