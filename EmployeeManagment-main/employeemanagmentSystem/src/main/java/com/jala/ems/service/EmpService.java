package com.jala.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jala.ems.model.Employee;
import com.jala.ems.repository.EmpRepo;

@Service
public class EmpService {

    @Autowired
    private EmpRepo repo;

    // A method to add a new employee to the database
    public boolean addEmp(Employee e) {
        repo.save(e);
        return false;
    }

    // A method to retrieve all employees from the database
    public List<Employee> getAllEmp() {
        return repo.findAll();
    }

    // A method to retrieve an employee by ID from the database
    public Employee getEmpById(int id) {
        Optional<Employee> e = repo.findById(id);
        if (e.isPresent()) {
            return e.get();
        }
        return null;
    }

    // A method to delete an employee by ID from the database
    public void deleteEmp(int id) {
        repo.deleteById(id);
    }

    // A method to retrieve a paginated list of employees from the database
    public Page<Employee> getEmpByPaginate(int currentPage, int size) {
        Pageable p = PageRequest.of(currentPage, size);
        return repo.findAll(p);
    }
}
