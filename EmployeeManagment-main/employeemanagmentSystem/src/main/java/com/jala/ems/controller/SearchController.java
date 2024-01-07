package com.jala.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jala.ems.model.Employee;
import com.jala.ems.repository.EmpRepo;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private EmpRepo empRepo;

    // This method searches for employees by name and email and returns the search results page with a list of matching employees
    @GetMapping("/search")
    public String search(@RequestParam("name") String name,
                         @RequestParam("email") String email,
                         Model model) {
        List<Employee> employees = empRepo.findByNameAndEmail(name, email);
        model.addAttribute("employees", employees);
        return "searchResults";
    }

    // This inner class handles the request for the search employee page
    @Controller
    public class PageController {
        
        // Displays the search employee page
        @GetMapping("/view")
        public String searchPage() {
            return "searchEmployee";

        }
    }
}
