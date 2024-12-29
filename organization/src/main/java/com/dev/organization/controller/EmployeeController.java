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

    @GetMapping("/health-check") // localhost:8080/employee/health-check
    public String applicationHealthCheck() {
        return "Application is running smoothly as of " + LocalDateTime.now();
    }

    @PostMapping("/addEmp") // localhost:8080/employee/addEmp
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/getEmp/no/{empNo}") // localhost:8080/employee/getEmp/no/602
    public Optional<Employee> getEmployeeByEmpNoUsingPathVariable(@PathVariable Long empNo) {
        return employeeService.getEmployeeByEmpNoUsingPathVariable(empNo);
    }

    @GetMapping("/getEmp/name/{firstName}") // localhost:8080/employee/getEmp/name/madhan
    public Employee getEmployeeByFirstNameUsingPathVariable(@PathVariable String firstName) {
        return employeeService.getEmployeeByFirstNameUsingPathVariable(firstName);
    }

    @GetMapping("/getEmp/no") // localhost:8080/employee/getEmp/no?empNo=602
    public Optional<Employee> getEmployeeByEmpNoUsingRequestParam(@RequestParam(name = "empNo") Long empNo) {
        return employeeService.getEmployeeByEmpNoUsingRequestParam(empNo);
    }

    @GetMapping("/getEmp/name") // localhost:8080/employee/getEmp/name?firstName=madhan
    public Employee getEmployeeByFirstNameUsingRequestParam(@RequestParam(name = "firstName") String firstName) {
        return employeeService.getEmployeeByFirstNameUsingRequestParam(firstName);
    }

    @GetMapping("/getAllEmp") // localhost:8080/employee/getAllEmp
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/updateEmp/no/{empNo}") // localhost:8080/employee/updateEmp/no/602
    public Employee updateEmployeeByEmpNo(@PathVariable Long empNo, @RequestBody Employee updateEemployee) {
        return employeeService.updateEmployeeByEmpNo(empNo, updateEemployee);
    }

    @PutMapping("/updateEmp/name/{firstName}") // localhost:8080/employee/updateEmp/name/balaji
    public Employee updateEmployeeByFirstName(@PathVariable String firstName, @RequestBody Employee updateEemployee) {
        return employeeService.updateEmployeeByFirstName(firstName, updateEemployee);
    }

    @DeleteMapping("/deleteEmp/no/{empNo}") // localhost:8080/employee/deleteEmp/no/652
    public String deleteEmployeeByEmpNo(@PathVariable Long empNo) {
        employeeService.deleteEmployeeByEmpNo(empNo);
        return "Employee deleted successfully";
    }

    @DeleteMapping("/deleteEmp/name/{firstName}") // localhost:8080/employee/deleteEmp/name/surr
    public String deleteEmployeeByFirstName(@PathVariable String firstName) {
        employeeService.deleteEmployeeByFirstName(firstName);
        return "Employee deleted successfully";
    }
}
