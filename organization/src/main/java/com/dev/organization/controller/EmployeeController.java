package com.dev.organization.controller;

import com.dev.organization.entity.Employee;
import com.dev.organization.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/health-check")
    public String applicationHealthCheck() {
        return "Application is working at the date and time of " + LocalDateTime.now();
    }

    @PostMapping("/addEmp")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/getEmp/{empNo}")
    public Optional<Employee> getEmployeeByEmpNoUsingPathVariable(@PathVariable Long empNo) {
        return employeeService.getEmployeeByEmpNoUsingPathVariable(empNo);
    }

    @GetMapping("/getEmp")
    public Optional<Employee> getEmployeeByEmpNoUsingRequestParam(@RequestParam(name = "empNo") Long empNo) {
        return employeeService.getEmployeeByEmpNoUsingRequestParam(empNo);
    }

    @GetMapping("/getAllEmp")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/updateEmp/{empNo}")
    public Employee updateEmployeeByEmpNo(@PathVariable Long empNo, @RequestBody Employee updateEemployee) {
        return employeeService.updateEmployeeByEmpNo(empNo, updateEemployee);
    }

    @DeleteMapping("/deleteEmp/{empNo}")
    public String deleteEmployeeByEmpNo(@PathVariable Long empNo) {
        employeeService.deleteEmployeeByEmpNo(empNo);
        return "Employee deleted successfully";
    }
}
