package com.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest.model.Customer;

@RestController
public class CustomerRestController {
	
	private static Long ID_VAR = 1L;
	
	private static Long getID() {
		return ++ID_VAR;
	}
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		System.out.println("------- GET list");
		return customerDAO.getCustomers();
	}
	
	@GetMapping("/customers/{id}")
	public ResponseEntity<?> getCustomer(@PathVariable("id") Long id){
		System.out.println("------- GET " + id);
		Customer customer = customerDAO.getCustomer(id);
		
		if(customer == null) {
			return new ResponseEntity<String>("Customer does not exist with id : " + id, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	
	
	//@RequestMapping(value="/customer", consumes=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST)
	@PostMapping("/customers")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
		System.out.println("------- Post");
		customer.setId(getID());
		Customer addedCustomer = customerDAO.addCustomer(customer);
		
		System.out.println(addedCustomer);
		
		return new ResponseEntity<Customer>(addedCustomer, HttpStatus.CREATED);
	}
	
	
}
