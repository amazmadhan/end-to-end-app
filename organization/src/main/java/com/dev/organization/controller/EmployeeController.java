package com.dev.organization.controller;

import com.dev.organization.entity.Employee;
import com.dev.organization.exceptions.RecordNotFoundException;
import com.dev.organization.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/health-check") // localhost:8080/employee/health-check
    public String applicationHealthCheck() {
        return "Application is running smoothly as of " + LocalDateTime.now();
    }

    @PostMapping("/addEmp") // localhost:8080/employee/addEmp
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp) {
        Employee employee = employeeService.addEmployee(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @GetMapping("/getEmp/no/{empNo}") // localhost:8080/employee/getEmp/no/602
    public ResponseEntity<Optional<Employee>> getEmployeeByEmpNoUsingPathVariable(@PathVariable Long empNo) {
        Optional<Employee> employee = employeeService.getEmployeeByEmpNoUsingPathVariable(empNo);
        return employee.isPresent() ? ResponseEntity.ok(employee) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/getEmp/name/{firstName}") // localhost:8080/employee/getEmp/name/madhan
    public ResponseEntity<Object> getEmployeeByFirstNameUsingPathVariable(@PathVariable String firstName) {
        try {
            Employee employee = employeeService.getEmployeeByFirstNameUsingPathVariable(firstName);
            return ResponseEntity.ok(employee);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getEmp/no") // localhost:8080/employee/getEmp/no?empNo=602
    public ResponseEntity<Optional<Employee>> getEmployeeByEmpNoUsingRequestParam(@RequestParam(name = "empNo") Long empNo) {
        Optional<Employee> employee = employeeService.getEmployeeByEmpNoUsingRequestParam(empNo);
        return employee.isPresent() ? ResponseEntity.ok(employee) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/getEmp/name") // localhost:8080/employee/getEmp/name?firstName=madhan
    public ResponseEntity<Employee> getEmployeeByFirstNameUsingRequestParam(@RequestParam(name = "firstName") String firstName) {
        Employee employee = employeeService.getEmployeeByFirstNameUsingRequestParam(firstName);
        return ResponseEntity.ok(employee); // NOT_FOUND will be thrown if no employee found. Error message will be triggered with the help of global exception
    }

    @GetMapping("/getAllEmpOrEmpty") // localhost:8080/employee/getAllEmp
    public ResponseEntity<List<Employee>> getAllEmployeesOrEmptyList() {
        List<Employee> employees = employeeService.getAllEmployeesOrEmptyList();
        return !employees.isEmpty() ? ResponseEntity.ok(employees) : ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/getAllEmp") // localhost:8080/employee/getAllEmp
    public ResponseEntity<?> getAllEmployees() {
        List<?> employees = employeeService.getAllEmployees();
        return !employees.isEmpty() ? ResponseEntity.ok(employees) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found");
    }

    @PutMapping("/updateEmp/no/{empNo}") // localhost:8080/employee/updateEmp/no/602
    public ResponseEntity<Employee> updateEmployeeByEmpNo(@PathVariable Long empNo, @RequestBody Employee updateEmployee) {
        Employee updatedEmployee = employeeService.updateEmployeeByEmpNo(empNo, updateEmployee);
        return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/updateEmp/name/{firstName}") // localhost:8080/employee/updateEmp/name/balaji
    public ResponseEntity<Employee> updateEmployeeByFirstName(@PathVariable String firstName, @RequestBody Employee updateEmployee) {
        Employee updatedEmployee = employeeService.updateEmployeeByFirstName(firstName, updateEmployee);
        return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/deleteEmp/no/{empNo}") // localhost:8080/employee/deleteEmp/no/652
    public ResponseEntity<String> deleteEmployeeByEmpNo(@PathVariable Long empNo) {
        boolean empIsDeleted = employeeService.deleteEmployeeByEmpNo(empNo);
        return empIsDeleted ? ResponseEntity.ok("Employee deleted successfully") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with empNo " + empNo + " to delete");
    }

    @DeleteMapping("/deleteEmp/name/{firstName}") // localhost:8080/employee/deleteEmp/name/surr
    public ResponseEntity<String> deleteEmployeeByFirstName(@PathVariable String firstName) {
        boolean empIsDeleted = employeeService.deleteEmployeeByFirstName(firstName);
        return empIsDeleted ? ResponseEntity.ok("Employee deleted successfully") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with firstName " + firstName + " to delete");
    }
}
