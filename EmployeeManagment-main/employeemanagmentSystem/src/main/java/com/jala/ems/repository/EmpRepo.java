package com.jala.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jala.ems.model.Employee;

@Repository
public interface EmpRepo extends JpaRepository<Employee, Integer> {

    // A custom query method to find an employee by name and email
    List<Employee> findByNameAndEmail(String name, String email);

}
