package com.example.SpringBootMongoDBAssessment.Repository;

import com.example.SpringBootMongoDBAssessment.Model.Employee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, ObjectId> {
}
