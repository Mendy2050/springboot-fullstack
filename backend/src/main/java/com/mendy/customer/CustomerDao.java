package com.mendy.customer;

import java.util.List;
import java.util.Optional;

/**
 * @author Mendy
 * @create 2023-07-26 13:48
 */
public interface CustomerDao {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Long id);

    void insertCustomer(Customer customer);

    boolean existsPersonWithEmail(String email);

    boolean existsPersonWithId(Long id);

    void deleteCustomerById(Long customerId);

    void updateCustomer(Customer update);

    Optional<Customer> selectUserByEmail(String email);
}
