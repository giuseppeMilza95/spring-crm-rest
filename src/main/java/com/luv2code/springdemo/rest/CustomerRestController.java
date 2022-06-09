package com.luv2code.springdemo.rest;

import com.luv2code.springdemo.CustomerNotFoundException;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api")

public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("customers/{customerId}")
    public Customer getCustomer(@PathVariable int customerId){
        if (customerId >= customerService.getCustomers().size() || customerId < 0){
            throw new CustomerNotFoundException("Customer not found with id - " + customerId);
        }
        return customerService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public Customer insertCustomer(@RequestBody Customer theCustomer){

        // also
        theCustomer.setId(0);
        customerService.saveCustomer(theCustomer);
        return theCustomer;
    }


    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer theCustomer){

        customerService.saveCustomer(theCustomer);
        return theCustomer;
    }

    @DeleteMapping("customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId){
        Customer theCustomer = customerService.getCustomer(customerId);

        if (theCustomer == null) {
            throw new CustomerNotFoundException("Customer not found with id - " + customerId);
        }

        customerService.deleteCustomer(customerId);
        return "Deleted customer id - " + customerId;
    }


}
