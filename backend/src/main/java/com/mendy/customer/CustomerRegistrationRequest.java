package com.mendy.customer;

/**
 * @author Mendy
 * @create 2023-07-31 6:23
 */
public record CustomerRegistrationRequest (String name, String email, String password, Integer age, Gender gender){

}
