package com.Utech.journalApp.services;

import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(UserEntity userEntity) {
        if (userEntity.getPassword() != null && !isBcryptHash(userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }
        if (userEntity.getRoles() == null || userEntity.getRoles().isEmpty()) {
            userEntity.setRoles(List.of("USER"));
        }
        userRepository.save(userEntity);
    }

    private boolean isBcryptHash(String password) {
        return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
    }

    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }

    public UserEntity findById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity findByName(String username) {
        return userRepository.findByUserName(username);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
