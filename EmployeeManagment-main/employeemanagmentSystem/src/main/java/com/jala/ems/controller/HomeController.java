package com.jala.ems.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jala.ems.model.UserDtls;
import com.jala.ems.repository.UserRepository;
import com.jala.ems.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    // This method will add the user details in the model attribute if the user is logged in
    @ModelAttribute
    private void userDetails(Model m, Principal p) {
        if (p != null) {
            String email = p.getName();
            UserDtls user = userRepo.findByEmail(email);

            m.addAttribute("user", user);
        }
    }

    // Displays the login page
    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    // Displays the registration page
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // Creates a new user and returns either success or failure message
    @PostMapping("/createUser")
    public String createuser(@ModelAttribute UserDtls user, HttpSession session) {

        boolean f = userService.checkEmail(user.getEmail());

        if (f) {
            session.setAttribute("msg", "Email Id already exists");
        } else {
            UserDtls userDtls = userService.createUser(user);
            if (userDtls != null) {
                session.setAttribute("msg", "Register Successfully");
            } else {
                session.setAttribute("msg", "Something went wrong on the server");
            }
        }

        return "redirect:/register";
    }
    
    // Displays the forgot password page
    @GetMapping("/loadforgotPassword")
    public String loadForgotPassword() {
        return "forgot_password";
    }
    
    // Displays the reset password page with the user id as a path variable
    @GetMapping("/loadresetPassword/{id}")
    public String loadResetPassword(@PathVariable int id, Model m) {
        m.addAttribute("id", id);
        return "reset_password";
    }
    
    // Handles the forgot password request and redirects to reset password page if credentials are correct
    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String email, @RequestParam String mobileNum, HttpSession session) {          
        UserDtls user = userRepo.findByEmailAndMobileNumber(email, mobileNum);
        
        if(user !=null) {
            return "redirect:/loadresetPassword/" + user.getId();
        } else {
            session.setAttribute("error", "Invalid Email & Mobile Number");
            return "forgot_password";
        }
    }
    
    // Resets the password for the given user id and returns success or failure message
    @PostMapping("/changePassword")
    public String resetPassword(@RequestParam String psw, @RequestParam Integer id, HttpSession session) {
        UserDtls user = userRepo.findById(id).get();
        String encryptPsw= passwordEncoder.encode(psw);
        user.setPassword(encryptPsw);
        
        UserDtls updateUser = userRepo.save(user);
        
        if(updateUser !=null) {
            session.setAttribute("msg", "Password Updated");
        }
        
        return "login";
    }
}
