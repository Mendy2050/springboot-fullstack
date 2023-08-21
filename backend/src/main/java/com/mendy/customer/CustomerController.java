package com.mendy.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mendy
 * @create 2023-07-26 13:51
 */

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public Customer getCustomers(@PathVariable("customerId") Long customerId){
        return customerService.getCustomer(customerId);
    }

    @PostMapping()
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping({"{customerId}"})
    public void deleteCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerUpdateRequest updateRequest){
        customerService.updateCustomer(customerId, updateRequest);
    }


}
