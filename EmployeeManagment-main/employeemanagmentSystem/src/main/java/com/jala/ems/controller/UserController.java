package com.jala.ems.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jala.ems.model.UserDtls;
import com.jala.ems.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // This method will add the user details in the model attribute if the user is logged in
    @ModelAttribute
    private void userDetails(Model model, Principal principal) {
        String email = principal.getName();
        UserDtls user = userRepo.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        model.addAttribute("user", user);
    }

    // Displays the home page for the logged in user
    @GetMapping("/")
    public String home() {
        return "welcome";
    }

    // Displays the change password page for the logged in user
    @GetMapping("/changePass")
    public String loadChangePassword() {
        return "changePassword";
    }

    // Handles the request to update the password of the logged in user and returns success or failure message
    @PostMapping("/updatePassword")
    public String changePassword(Principal principal, @RequestParam("oldPass") String oldPassword,
                                 @RequestParam("newPass") String newPassword, RedirectAttributes redirectAttributes) {
        String email = principal.getName();

        UserDtls loginUser;

        try {
            loginUser = userRepo.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user details", e);
        }

        boolean passwordsMatch = passwordEncoder.matches(oldPassword, loginUser.getPassword());

        if (passwordsMatch) {
            loginUser.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(loginUser);
            redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Incorrect old password.");
            return "redirect:/user/changePass";
        }

        return "redirect:/user/changePass?success=true";
    }
}
