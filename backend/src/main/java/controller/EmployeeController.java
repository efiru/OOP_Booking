package controller;

import model.person.Employee;
import org.springframework.web.bind.annotation.*;
import service.employee.IEmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public Employee registerEmployee(@RequestBody RegisterEmployeeRequest req) {
        return employeeService.registerEmployee(req.name(), req.email(), req.position());
    }

    record RegisterEmployeeRequest(String name, String email, String position) {}
}
