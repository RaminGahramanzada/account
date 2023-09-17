package com.javadevcom.account.service;

import com.javadevcom.account.exceptions.CustomerNotFoundException;
import com.javadevcom.account.model.Customer;
import com.javadevcom.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    protected Customer findCustomerById(String id){
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer could not find by id:"+id));

    }
}
