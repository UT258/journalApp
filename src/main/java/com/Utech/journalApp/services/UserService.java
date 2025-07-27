    package com.Utech.journalApp.services;

    import com.Utech.journalApp.Entity.UserEntity;
    import com.Utech.journalApp.Repository.UserRepository;
    import org.apache.catalina.User;
    import org.bson.types.ObjectId;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.Arrays;
    import java.util.List;
    @Service
    public class UserService {
        @Autowired //this is used to inject the UserRepository bean into this class
        private UserRepository userRepository;
        private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        public void saveUser(UserEntity userEntity) {
              //add the encrpted password
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(List.of("USER")); // Set default role as USER

            userRepository.save(userEntity);
        }
        public void deleteUser(ObjectId id) {
            userRepository.deleteById(id);

        }
        // flow of service repostirt
        public UserEntity findById(ObjectId id) {
            return userRepository.findById(id).orElse(null);
        }
        //findbuname
        public UserEntity findByName(String username) {
            return userRepository.findByUserName(username);
            //it is just using the normal method of repository
        }

        public List<UserEntity> getAllUsers() {
            return userRepository.findAll();
        }
    }
