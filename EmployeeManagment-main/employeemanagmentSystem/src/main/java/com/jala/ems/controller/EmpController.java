package com.jala.ems.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jala.ems.model.Employee;
import com.jala.ems.service.EmpService;

@Controller
public class EmpController {

    // Injecting EmpService
    @Autowired
    private EmpService service;

    // Mapping for home page
    @GetMapping("/welcome")
    public String showHomePage() {
        return "welcome";
    }

    // Mapping for paginated home page
    @GetMapping("/home")
    public String home(Model m) {
        return findPaginated(0, m);
    }

    // Mapping for add employee form
    @GetMapping("/addemp")
    public String addEmpForm(Model m) {
        // Adding an empty Employee object to the model for form binding
        m.addAttribute("emp", new Employee());
        return "add-emp";
    }

    // Mapping for processing employee registration form
    @PostMapping("/registerr")
    public String empRegister(@ModelAttribute("emp") Employee e, HttpSession session) {
        try {
            // Adding employee using EmpService
            boolean isAdded = service.addEmp(e);
            if (isAdded) {
                session.setAttribute("msg", "Employee Added Successfully..");
            } else {
                session.setAttribute("msg", "Employee Added Successfully..");
            }
        } catch (Exception ex) {
            // Handling exceptions
            if (ex instanceof SQLIntegrityConstraintViolationException) {
                session.setAttribute("msg", "Duplicate Entry: Employee already exists..");
            } else {
                session.setAttribute("msg", "Duplicate Entry: Employee already exists..");
            }
        }
        // Redirecting to paginated home page
        return "redirect:/home";
    }

    // Mapping for deleting an employee by ID
    @GetMapping("/delete/{id}")
    public String deleteEmp(@PathVariable int id, Model model) {
        // Deleting employee using EmpService
        service.deleteEmp(id);
        model.addAttribute("msg", "Employee Data Deleted Successfully..");
        // Redirecting to paginated home page
        return "redirect:/home";
    }

    // Mapping for editing an employee by ID
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model m) {
        // Getting employee by ID using EmpService
        Employee e = service.getEmpById(id);
        // Adding employee object to the model for form binding
        m.addAttribute("emp", e);
        return "edit";
    }

    // Mapping for processing employee update form
    @PostMapping("/update")
    public String updateEmp(@ModelAttribute("emp") Employee e, HttpSession session) {
        // Updating employee using EmpService
        service.addEmp(e);
        session.setAttribute("msg", "Employee Data Updated Successfully..");
        // Redirecting to paginated home page
        return "redirect:/home";
    }

    
 // Mapping for paginated home page with page number
    @GetMapping("/page/{pageno}")
    public String findPaginated(@PathVariable int pageno, Model m) {

        Page<Employee> empList = service.getEmpByPaginate(pageno, 3);
        m.addAttribute("emp", empList);
        m.addAttribute("currentPage", pageno);
        m.addAttribute("totalPages", empList.getTotalPages());
        m.addAttribute("totalItems", empList.getTotalElements());
        return "home";
    }

    



}

