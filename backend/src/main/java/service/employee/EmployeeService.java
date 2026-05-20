package service.employee;

import model.person.Employee;
import service.audit.AuditService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService implements IEmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();
    private int nextEmployeeId = 1;
    private final AuditService auditService;

    public EmployeeService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public Employee registerEmployee(String name, String email, String position) {
        auditService.log("registerEmployee");
        Employee employee = new Employee(nextEmployeeId++, name, email, position);
        employees.put(employee.getId(), employee);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        auditService.log("getAllEmployees");
        return new ArrayList<>(employees.values());
    }
}
