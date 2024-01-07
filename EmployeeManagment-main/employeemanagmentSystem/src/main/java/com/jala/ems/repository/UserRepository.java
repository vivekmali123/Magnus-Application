package com.jala.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jala.ems.model.UserDtls;

@Repository
public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    // A custom query method to check if a user exists with the given email address
    public boolean existsByEmail(String email);

    // A custom query method to find a user by email address
    public UserDtls findByEmail(String email);

    // A custom query method to find a user by email address and mobile number combination
    public UserDtls findByEmailAndMobileNumber(String email, String mobileNum);
}
