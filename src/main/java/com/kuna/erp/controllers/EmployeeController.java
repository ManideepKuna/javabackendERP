package com.kuna.erp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuna.erp.entities.Employee;
import com.kuna.erp.exception.ResourceNotFoundException;
import com.kuna.erp.repos.EmployeeRepository;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository repository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return repository.findAll();
	}
	
	
	//create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		return repository.save(employee);
	}
	
	//get employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Integer id) {
	
		Employee emp = repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not exists with id: "+id));
		 return ResponseEntity.ok(emp);
		
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployeeById(@PathVariable(value = "id") Integer id,@RequestBody Employee employee)
	{
		Employee emp = repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not exists with id: "+id));
		
		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setEmailId(employee.getEmailId());
		
		Employee updatedEmployee = repository.save(emp);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable(value = "id") Integer id){
		Employee emp = repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not exists with id: "+id));
		repository.delete(emp);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
		
		
		
		
	//this is to test the accessability of this controller class
	@GetMapping("/test")
	public String test()
	{
		return "Employee Controller Working";
	}
	
	

}
