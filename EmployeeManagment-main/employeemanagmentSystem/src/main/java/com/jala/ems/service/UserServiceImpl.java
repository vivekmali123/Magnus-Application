package com.jala.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jala.ems.model.UserDtls;
import com.jala.ems.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    // A method to create a new user with encrypted password and default role
    @Override
    public UserDtls createUser(UserDtls user) {

        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        return userRepo.save(user);
    }

    // A method to check if a user with the given email already exists in the database
    @Override
    public boolean checkEmail(String email) {

        return userRepo.existsByEmail(email);
    }

}
