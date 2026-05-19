package service.employee;

import model.person.Employee;

import java.util.List;

public interface IEmployeeService {
    Employee registerEmployee(String name, String email, String position);
    List<Employee> getAllEmployees();
}
