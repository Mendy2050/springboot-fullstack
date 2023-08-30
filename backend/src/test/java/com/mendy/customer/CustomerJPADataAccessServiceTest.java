package com.mendy.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * @author Mendy
 * @create 2023-08-11 16:45
 */
class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {
        //initialize the mock itself
        autoCloseable = MockitoAnnotations.openMocks(this);

        underTest = new CustomerJPADataAccessService(customerRepository);
    }


    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    void selectAllCustomers() {
        //GIVEN

        //When
        underTest.selectAllCustomers();

        //Then
        verify(customerRepository)
                .findAll();

    }


    @Test
    void selectCustomerById() {
        //GIVEN
        Long id = 1L;

        //When
        underTest.selectCustomerById(id);

        //Then
        verify(customerRepository)
                .findById(id);
    }


    @Test
    void insertCustomer() {
        //GIVEN
        Customer customer = new Customer(1l,"Ali","ali@gmail.com",2, Gender.MALE);


        //When
        underTest.insertCustomer(customer);

        //Then
        verify(customerRepository).save(customer);
    }


    @Test
    void existsPersonWithEmail() {
        //GIVEN
        String email = "foo@gmail.com";

        //When
        underTest.existsPersonWithEmail(email);

        //Then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsPersonWithId() {
        //GIVEN
        Long id = 2l;

        //When
        underTest.existsPersonWithId(id);

        //Then
        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void deleteCustomerById() {
        //GIVEN
        Long id = 2l;

        //When
        underTest.deleteCustomerById(id);

        //Then
        verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        //GIVEN
        Customer customer = new Customer(1l,"Ali","ali@gmail.com",3, Gender.MALE);


        //When
        underTest.updateCustomer(customer);

        //Then
        verify(customerRepository).save(customer);
    }
}