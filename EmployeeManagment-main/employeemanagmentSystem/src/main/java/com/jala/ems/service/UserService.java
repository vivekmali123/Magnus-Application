package com.jala.ems.service;

import com.jala.ems.model.UserDtls;

public interface UserService {

    // A method to create a new user
    public UserDtls createUser(UserDtls user);

    // A method to check if a user with the given email already exists
    public boolean checkEmail(String email);

}
