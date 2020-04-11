package com.erickmp98.customerservice.controller;

import com.erickmp98.customerservice.entity.Customer;
import com.erickmp98.customerservice.entity.Region;
import com.erickmp98.customerservice.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerRest {

    @Autowired
    private CustomerService customerService;

    // -------------------Retrieve All Customers--------------------------------------------

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers( @RequestParam(name="idRegion", required = false) Long idRegion){
        List<Customer> customers = new ArrayList<>();
        if(idRegion==null){
             customers = customerService.findCustomerAll();
            if (customers.isEmpty()){
                return  ResponseEntity.noContent().build();
            }
        }else {
            Region region = new Region();
            region.setId(idRegion);
            customers= customerService.findCustomerByRegion(region);
            if (customers==null){
                log.error("Customer with idRegion {} not found.",idRegion);
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(customers);
    }

    // -------------------Retrieve Single Customer------------------------------------------
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id){
        log.info("Fetching Customer wth id {}",id);
        Customer customer = customerService.getCustomer(id);
        if (customer==null){
            log.error("Customer with id {} not found.",id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    // -------------------Create a Customer-------------------------------------------
    @PostMapping
    public  ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        log.info("Creating Customer : {}",customer);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Customer customerBD = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerBD);
    }

    // ------------------- Update a Customer ------------------------------------------------
    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable long id, @RequestBody Customer customer){
        log.info("Updating Customer with id {}",id);

        Customer currentCustomer = customerService.getCustomer(id);

        if (currentCustomer == null){
            log.error("Unable to update. Customer with id {} not found.",id);
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        currentCustomer= customerService.updateCustomer(customer);
        return  ResponseEntity.ok(currentCustomer);
    }

    // ------------------- Delete a Customer-----------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id){
        log.info("Fetching & deleting Customer with id {}",id);
        Customer customer = customerService.getCustomer(id);
        if(customer == null){
            log.info("Unable to delete. Customer with id {} not found.",id);
            return ResponseEntity.notFound().build();
        }
        customer= customerService.deleteCustomer(customer);
        return ResponseEntity.ok(customer);

    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }


}
