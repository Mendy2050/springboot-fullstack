package com.mendy.customer;

import com.mendy.exception.DuplicateResourceException;
import com.mendy.exception.RequestValidationException;
import com.mendy.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * @author Mendy
 * @create 2023-08-11 17:48
 */

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;

    @Mock
    private CustomerDao customerDao;


    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }


    @Test
    void getAllCustomers() {
        //GIVEN

        //When
        underTest.getAllCustomers();

        //Then
        verify(customerDao).selectAllCustomers();
    }



    @Test
    void canGetCustomer() {
        //GIVEN
        Long id =10l;
        Customer customer = new Customer(id,"Alex","alex@gmail.com",19, Gender.MALE);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //When
        Customer actual = underTest.getCustomer(id);

        //Then
        assertThat(actual).isEqualTo(customer);
    }


    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional() {
        //GIVEN
        Long id =10l;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //When


        //Then
        assertThatThrownBy( () -> underTest.getCustomer(id) )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage( "customer with id [%s] not found".formatted(id) );
    }

    @Test
    void addCustomer() {
        //GIVEN
        String email = "alex@gmail.com";

        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex",email,19, Gender.MALE);


        //When
        underTest.addCustomer(request);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor= ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());

    }



    @Test
    void willThrowWhenEmailExistsWhileAddingCustomer() {
        //GIVEN
        String email = "alex@gmail.com";
        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex",email,19, Gender.MALE);

        //When


        //Then
        assertThatThrownBy( () -> underTest.addCustomer(request) )
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage( "email already taken" );

        verify(customerDao, never()).insertCustomer(any());

    }



    @Test
    void deleteCustomerById() {
        //GIVEN
        Long id =10l;
        when(customerDao.existsPersonWithId(id)).thenReturn(true);

        //When
        underTest.deleteCustomerById(id);

        //Then
        verify(customerDao).deleteCustomerById(id);
    }



    @Test
    void willThrowWhenDeleteCustomerByIdNotExists() {
        //GIVEN
        Long id =10l;
        when(customerDao.existsPersonWithId(id)).thenReturn(false);

        //When
        assertThatThrownBy( () -> underTest.deleteCustomerById(id) )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage( "customer with id [%s] not found".formatted(id) );

        //Then
        verify(customerDao, never()).deleteCustomerById(id);
    }






    @Test
    void canUpdateAllCustomersProperties() {
        //GIVEN
        //1. first pre-condition of the test: customer id
        Long id =10l;

        //2. second pre-condition of the test: can get the customer
        Customer customer = new Customer(id,"Alex","alex@gmail.com",19, Gender.MALE);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //3. third-precondition of the test: CustomerUpdateRequest
        String newEmail = "alexandro@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", newEmail, 23);

        //4. fourth-precondition of the test: the email should not be taken
        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        //When
        // real test
        underTest.updateCustomer(id,updateRequest);

        //Then
        //verify the result:
        // The main purpose of this test is to
        // verify whether the addCustomer method correctly extracts information from the CustomerRegistrationRequest object
        // to create a new  Customer object, and passes it to the insertCustomer method.
        ArgumentCaptor<Customer> customerArgumentCaptor= ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());

    }



    @Test
    void canUpdateOnlyCustomerName() {
        //GIVEN
        //1. first pre-condition of the test: customer id
        Long id =10l;

        //2. second pre-condition of the test: can get the customer
        Customer customer = new Customer(id,"Alex","alex@gmail.com",19, Gender.MALE);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //3. third-precondition of the test: CustomerUpdateRequest
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", null, null);



        //When
        // real test
        underTest.updateCustomer(id,updateRequest);

        //Then
        //verify the result:
        // The main purpose of this test is to
        // verify whether the addCustomer method correctly extracts information from the CustomerRegistrationRequest object
        // to create a new  Customer object, and passes it to the insertCustomer method.
        ArgumentCaptor<Customer> customerArgumentCaptor= ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());

    }



    @Test
    void canUpdateOnlyCustomerEmail() {
        //GIVEN
        //1. first pre-condition of the test: customer id
        Long id =10l;

        //2. second pre-condition of the test: can get the customer
        Customer customer = new Customer(id,"Alex","alex@gmail.com",19, Gender.MALE);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //3. third-precondition of the test: CustomerUpdateRequest
        String newEmail = "alexandro@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail, null);

        //4. fourth-precondition of the test: the email should not be taken
        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        //When
        // real test
        underTest.updateCustomer(id,updateRequest);

        //Then
        //verify the result:
        // The main purpose of this test is to
        // verify whether the addCustomer method correctly extracts information from the CustomerRegistrationRequest object
        // to create a new  Customer object, and passes it to the insertCustomer method.
        ArgumentCaptor<Customer> customerArgumentCaptor= ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());

    }




    @Test
    void canUpdateOnlyCustomerAge() {
        //GIVEN
        //1. first pre-condition of the test: customer id
        Long id =10l;

        //2. second pre-condition of the test: can get the customer
        Customer customer = new Customer(id,"Alex","alex@gmail.com",19, Gender.MALE);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //3. third-precondition of the test: CustomerUpdateRequest
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, null, 29);



        //When
        // real test
        underTest.updateCustomer(id,updateRequest);

        //Then
        //verify the result:
        // The main purpose of this test is to
        // verify whether the addCustomer method correctly extracts information from the CustomerRegistrationRequest object
        // to create a new  Customer object, and passes it to the insertCustomer method.
        ArgumentCaptor<Customer> customerArgumentCaptor= ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());

    }


    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        //GIVEN
        //1. first pre-condition of the test: customer id
        Long id =10l;

        //2. second pre-condition of the test: can get the customer
        Customer customer = new Customer(id,"Alex","alex@gmail.com",19, Gender.MALE);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //3. third-precondition of the test: CustomerUpdateRequest
        String newEmail = "alexandro@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", newEmail, 23);

        //4. fourth-precondition of the test: the email should not be taken
        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(true);

        //When
        // real test
        assertThatThrownBy( ()-> underTest.updateCustomer(id, updateRequest)  )
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        //Then
        verify(customerDao,never()).updateCustomer(any());

    }



    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        //GIVEN
        //1. first pre-condition of the test: customer id
        Long id =10l;

        //2. second pre-condition of the test: can get the customer
        Customer customer = new Customer(id,"Alex","alex@gmail.com",19, Gender.MALE);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //3. third-precondition of the test: CustomerUpdateRequest

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(customer.getName(), customer.getEmail(), customer.getAge());


        //When
        // real test, when do this test, it will have an exception
        assertThatThrownBy( () -> underTest.updateCustomer(id,updateRequest) )
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");


        //Then
        //verify the result:
        verify(customerDao,never()).updateCustomer(any());

    }




}