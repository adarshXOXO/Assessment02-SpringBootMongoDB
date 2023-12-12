package com.example.SpringBootMongoDBAssessment.Service;

import com.example.SpringBootMongoDBAssessment.Model.Employee;
import com.example.SpringBootMongoDBAssessment.Repository.EmployeeRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    //Get all Employees
    public String getAllEmployees()
    {
        var movieList = employeeRepository.findAll();
        return  String.format("{\n\t\"message\": \"%d movies found\", \n\t \"data\": %s \n}", movieList.size(),movieList.toString());
    }

    //Add Employees
    public String addEmployee(Employee employee)
    {
        Employee savedMovie = employeeRepository.save(employee);
        return String.format("{\n\t\"message\":\"Movie Details saved\", \n\t \"data\": %s \n}",savedMovie.toString());
    }

    // update employee
    public String updateEmployee(String id, Employee emp) {

        ObjectId objectId = new ObjectId(id);

        // Use findById to retrieve the entity by its ID
        Optional<Employee> OptionalEmp = employeeRepository.findById(objectId);

        if(OptionalEmp.isEmpty()) {
            throw new RuntimeException("given employee doesn't exist");
        }
        var empRec = OptionalEmp.get();

        if (emp.getName() != null)
            empRec.setName(emp.getName());
        if (emp.getSalary() != 0)
            empRec.setSalary(emp.getSalary());
        Employee savedEmp = employeeRepository.save(empRec);
        return "{" +
                "\"message\":"+"Successfully updated employee\",\n"+
                "\"data\":"+savedEmp+",\n"+
                "}";
    }

    // remove employee
    public String removeEmpById(String id) {

        ObjectId objectId = new ObjectId(id);

        // Use findById to retrieve the entity by its ID
        Optional<Employee> OptionalEmp = employeeRepository.findById(objectId);

        if(OptionalEmp.isEmpty()) {
            throw new RuntimeException("Employee id" + id + "doesn't exist");
        }
        employeeRepository.deleteById(objectId);
        return "{" +
                "\"message\":"+"Successfully deleted employee\",\n"+
                "\"id\":"+ id +",\n"+
                "}";
    }

    //remove all employees
    public void removeAllEmployees() { employeeRepository.deleteAll();}
}