package com.mendy.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mendy
 * @create 2023-07-30 11:51
 */


@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDao  {

    private  final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }



    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }


    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }


    @Override
    public boolean existsPersonWithId(Long id) {
        return customerRepository.existsCustomerById(id);

    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public void updateCustomer(Customer update) {
        customerRepository.save(update);
    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }


}
