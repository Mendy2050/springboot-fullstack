package com.mendy.customer;

import com.mendy.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mendy
 * @create 2023-08-10 20:30
 */

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private  CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }



    @Test
    void selectAllCustomers() {
        //GIVEN
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + "-" +UUID.randomUUID(),
                20
        );
        underTest.insertCustomer(customer);

        //When
        List<Customer> actual = underTest.selectAllCustomers();

        //Then
        assertThat(actual).isNotEmpty();
    }



    @Test
    void selectCustomerById() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


       /* // 非流写法
        List<Customer> customers = underTest.selectAllCustomers();
        Long id = null;
        for (Customer c : customers) {
            if (c.getEmail().equals(email)) {
                id = c.getId();
                break;
            }
        }
        if (id == null) {
            throw new NoSuchElementException("No customer found with the given email.");
        }
        */

        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying( c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });

    }



    @Test
    void willReturnEmptyWhenSelectCustomerById(){
        //GIVEN
        Long id = 0L;

        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isEmpty();
    }




    @Test
    void insertCustomer() {
        //GIVEN

        //When

        //Then
    }



    @Test
    void existsPersonWithEmail() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,email,20);

        underTest.insertCustomer(customer);

        //When
        boolean actual = underTest.existsPersonWithEmail(email);

        //Then
        assertThat(actual).isTrue();
    }



    @Test
    void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //When
        boolean actual = underTest.existsPersonWithEmail(email);

        //Then
        assertThat(actual).isFalse();
    }



    @Test
    void existsPersonWithId() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,email,20);

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = underTest.existsPersonWithId(id);

        //Then
        assertThat(actual).isTrue();
    }


    @Test
    void existsPersonWithIdWillReturnFalseWhenIdNotPresent() {
        //GIVEN
        Long id = -1L;

        //When
        var actual = underTest.existsPersonWithId(id);

        //Then
        assertThat(actual).isFalse();
    }



    @Test
    void deleteCustomerById() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,email,20);

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        //When
        underTest.deleteCustomerById(id);

        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isNotPresent();
    }



    @Test
    void updateCustomerName() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,email,20);

        underTest.insertCustomer(customer);

        // check the above random-generated customer's exact id for the below test
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // add new customer
        var newName = "foo";

        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);

        // run the test method
        underTest.updateCustomer(update);


        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying( c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }



    @Test
    void updateCustomerEmail() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,email,20);

        underTest.insertCustomer(customer);

        // check the above random-generated customer's exact id for the below test
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // add new customer
        var newEmail = "foo@gmail";

        Customer update = new Customer();
        update.setId(id);
        update.setEmail(newEmail);

        // run the test method
        underTest.updateCustomer(update);


        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying( c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }




    @Test
    void updateCustomerAge() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,email,20);

        underTest.insertCustomer(customer);

        // check the above random-generated customer's exact id for the below test
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // add new customer
        var newAge = 25;

        Customer update = new Customer();
        update.setId(id);
        update.setAge(newAge);

        // run the test method
        underTest.updateCustomer(update);


        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying( c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(newAge);
        });
    }


    @Test
    void willUpdateAllPropertiesCustomer(){
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,email,20);

        underTest.insertCustomer(customer);

        // check the above random-generated customer's exact id for the below test
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // add new customer
        Customer update = new Customer();
        update.setId(id);
        update.setName("foo");
        update.setEmail(UUID.randomUUID().toString());
        update.setAge(35);

        // run the test method
        underTest.updateCustomer(update);


        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValue(update);
    }



    @Test
    void willNotUpdateWhenNothingToUpdate(){
        //GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,email,20);

        underTest.insertCustomer(customer);

        // check the above random-generated customer's exact id for the below test
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // add new customer
        Customer update = new Customer();
        update.setId(id);

        // run the test method
        underTest.updateCustomer(update);


        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying( c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }


}