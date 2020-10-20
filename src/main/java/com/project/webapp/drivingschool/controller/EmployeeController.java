package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.model.Employee;
import com.project.webapp.drivingschool.service.EmployeeService;
import com.project.webapp.drivingschool.utils.EmployeeRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler dla pracowników szkoły nauki jazdy
 */
@CrossOrigin
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/allActual")
    @ResponseBody
    public List<Employee> getAllActualEmployees() {
        return employeeService.getAllActualEmployees();
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/allNotAdmins")
    @ResponseBody
    public List<Employee> getAllEmployeesNotAdmins() {
        return employeeService.getAllEmployeesNotAdmins();
    }

    @GetMapping("/allByRole")
    @ResponseBody
    public List<Employee> getAllEmployeesByEmployeeRole(@RequestParam("role") EmployeeRole role) {
        return employeeService.getAllEmployeesByEmployeeRole(role);
    }

    @GetMapping("/byEmail")
    @ResponseBody
    public Employee getEmployeeByEmail(@RequestParam("email") String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    @GetMapping("/allRoles")
    @ResponseBody
    public List<EmployeeRole> getAllEmployeeRoles() {
        return employeeService.getAllEmployeeRoles();
    }

    @GetMapping("/checkByEmployeeIdAndRole")
    @ResponseBody
    public Boolean checkExistingByEmployeeIdAndEmployeeRole(@RequestParam("id") Long id,
                                                            @RequestParam("role") EmployeeRole role) {
        return employeeService.checkExistingByEmployeeIdAndEmployeeRole(id, role);
    }

    @GetMapping("/checkEmail")
    @ResponseBody
    public Boolean emailExisting(@RequestParam("email") String email) {
        return employeeService.emailExisting(email);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/edit")
    public ResponseEntity<Employee> editEmployee(@RequestParam("email") String email,
                                                 @RequestBody Employee newEmployee) {
        return employeeService.editEmployee(email, newEmployee);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Employee> changePassword(@RequestParam("email") String email,
                                                   @RequestBody String newPassword) {
        return employeeService.changePassword(email, newPassword);
    }

    @PutMapping("/changeRole")
    public ResponseEntity<Employee> changeEmployeeRole(@RequestParam("email") String email,
                                                       @RequestBody EmployeeRole newRole) {
        return employeeService.changeEmployeeRole(email, newRole);
    }

}
