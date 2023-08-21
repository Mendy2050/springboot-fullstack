package com.mendy.customer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mendy
 * @create 2023-07-30 11:42
 */


public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Long id);


}
