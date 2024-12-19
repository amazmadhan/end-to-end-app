package com.dev.organization.service;

import com.dev.organization.entity.Employee;
import com.dev.organization.exceptions.RecordNotFoundException;
import com.dev.organization.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeByEmpNoUsingPathVariable(Long empNo) {
        return employeeRepository.findById(empNo);
    }

    public Optional<Employee> getEmployeeByEmpNoUsingRequestParam(Long empNo) {
        return employeeRepository.findById(empNo);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new RecordNotFoundException("No Employees are found: " + employees);
        }
        return employees;
    }

    public Employee updateEmployeeByEmpNo(Long empNo, Employee updatedEmployee) {
        // Validate the provided empNo
        if (empNo == null || empNo <= 0) {
            throw new IllegalArgumentException("EmpNo must not be null or negative.");
        }

        // Fetch the existing employee
        Optional<Employee> existingOptionalEmployee = employeeRepository.findById(empNo);
        if (existingOptionalEmployee.isEmpty()) {
            throw new EntityNotFoundException("Employee with empNo " + empNo + " not found.");
        }

        Employee existingEmployee = existingOptionalEmployee.get();

        // Updating employee fields
        if (updatedEmployee.getFirstName() != null && !updatedEmployee.getFirstName().trim().isEmpty()) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
        }
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setBirthDate(updatedEmployee.getBirthDate());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setHireDate(updatedEmployee.getHireDate());
        return employeeRepository.save(existingEmployee);
    }

    public Employee deleteEmployeeByEmpNo(Long empNo) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empNo);
        if (employeeOptional.isEmpty()) {
            throw new RecordNotFoundException("Employee not found with empNo to delete: " + empNo);
        }

        // Get the employee from Optional
        Employee employee = employeeOptional.get();
        employeeRepository.delete(employee);
        return employee;
    }
}
