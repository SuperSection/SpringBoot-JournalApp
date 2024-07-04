package com.supersection.journalApp.service;

import com.supersection.journalApp.enitity.UserEntity;
import com.supersection.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean saveNewUser (UserEntity user) {
        try {
//            if (findByUsername(user.getUsername()) != null) {
//                logger.error("Username already taken for a USER.");
//                return false;
//            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e){
            logger.info("Saving new user Failed");
            logger.warn("Saving new user Failed");
            logger.error("Failed to register for {}", user.getUsername());
            logger.debug("Saving new user Failed");
            logger.trace("Saving new user Failed");
            return false;
        }
    }

    public void saveChangedPassword (UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveUser (UserEntity user) {
        userRepository.save(user);
    }

    public boolean saveAdminUser(UserEntity user) {
        UserEntity existingUser = findByUsername(user.getUsername());
        if (existingUser != null) {
            logger.error("Username already taken for an ADMIN.");
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(user);
        return true;
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
