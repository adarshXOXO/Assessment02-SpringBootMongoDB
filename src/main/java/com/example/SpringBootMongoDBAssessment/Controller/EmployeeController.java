package com.example.SpringBootMongoDBAssessment.Controller;

import com.example.SpringBootMongoDBAssessment.Model.Employee;
import com.example.SpringBootMongoDBAssessment.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService)
    {
        this.employeeService=employeeService;
    }

    //add a employee
    @PostMapping("add-employee")
    public String saveEmployee(@RequestBody Employee employee)
    {
        return employeeService.addEmployee(employee);
    }

    //get an employee
    @GetMapping("getAll")
    public String getEmployee()
    {
        return employeeService.getAllEmployees();
    }

    // update employee
    @PutMapping("/update/{id}")
    public String updateEmployee(@RequestBody Employee employee, @PathVariable String id) {return employeeService.updateEmployee(id,employee);}

    //delete employee by id
    @GetMapping("/del/{id}")
    public String  removeEmployeeById(@PathVariable String id) {
        return employeeService.removeEmpById(id);
    }

    // remove all employees
    @GetMapping("/delAll")
    public void removeAllEmployees() {
        employeeService.removeAllEmployees();
    }
}