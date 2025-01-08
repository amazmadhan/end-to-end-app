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

    public Employee getEmployeeByFirstNameUsingPathVariable(String firstName) {
        Employee employee = employeeRepository.findByFirstName(firstName);
        if (employee == null) {
            throw new RecordNotFoundException("Employee not found with first name: " + firstName);
        }
        return employee;
    }

    public Optional<Employee> getEmployeeByEmpNoUsingRequestParam(Long empNo) {
        return employeeRepository.findById(empNo);
    }

    public Employee getEmployeeByFirstNameUsingRequestParam(String firstName) {
        Employee employee = employeeRepository.findByFirstName(firstName);
        if (employee == null) {
            throw new RecordNotFoundException("Employee not found with first name: " + firstName); // global exception will get triggered if there is no employee
        }
        return employee;
    }

    public List<Employee> getAllEmployeesOrEmptyList() {
        return employeeRepository.findAll(); // Return empty list if no employees are found
    }

    public List<?> getAllEmployees() {
        return employeeRepository.findAll(); // Return empty list if no employees are found
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

    public Employee updateEmployeeByFirstName(String firstName, Employee updatedEmployee) {

        // Validate the provided firstName
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name must not be null or empty.");
        }

        // Fetch the existing employee
        Employee existingEmployee = employeeRepository.findByFirstName(firstName);
        if (existingEmployee == null) {
            throw new RecordNotFoundException("Employee not found with first name to update: " + firstName);
        }

        // Updating employee fields
        if (updatedEmployee.getFirstName() != null && !updatedEmployee.getFirstName().trim().isEmpty()) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
        }
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setBirthDate(updatedEmployee.getBirthDate());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setHireDate(updatedEmployee.getHireDate());

        // Update fields selectively. Ensuring only non-null values are updated
//        if (updatedEmployee.getFirstName() != null && !updatedEmployee.getFirstName().trim().isEmpty()) {
//            existingEmployee.setFirstName(updatedEmployee.getFirstName());
//        }
//        if (updatedEmployee.getLastName() != null) {
//            existingEmployee.setLastName(updatedEmployee.getLastName());
//        }
//        if (updatedEmployee.getBirthDate() != null) {
//            existingEmployee.setBirthDate(updatedEmployee.getBirthDate());
//        }
//        if (updatedEmployee.getGender() != null) {
//            existingEmployee.setGender(updatedEmployee.getGender());
//        }
//        if (updatedEmployee.getHireDate() != null) {
//            existingEmployee.setHireDate(updatedEmployee.getHireDate());
//        }

        return employeeRepository.save(existingEmployee);
    }

    public boolean deleteEmployeeByEmpNo(Long empNo) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empNo);
        if (employeeOptional.isEmpty()) {
            return false;
        }
        // Get the employee from Optional
        Employee employee = employeeOptional.get();
        employeeRepository.delete(employee);
        return true;
    }

    public boolean deleteEmployeeByFirstName(String firstName) {
        Employee employee = employeeRepository.findByFirstName(firstName);
        if (employee == null) {
            return false;
        }
        employeeRepository.delete(employee);
        return true;
    }
}
