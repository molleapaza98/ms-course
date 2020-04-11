package com.erickmp98.customerservice.service;

import com.erickmp98.customerservice.entity.Customer;
import com.erickmp98.customerservice.entity.Region;

import java.util.List;

public interface CustomerService {


    List<Customer> findCustomerAll();
    List<Customer> findCustomerByRegion(Region region);

    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer deleteCustomer(Customer customer);
    Customer getCustomer(Long id);

}
