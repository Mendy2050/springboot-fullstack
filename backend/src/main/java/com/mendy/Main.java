package com.mendy;

import com.github.javafaker.Faker;
import com.mendy.customer.Customer;
import com.mendy.customer.CustomerRepository;
import com.mendy.customer.Gender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.Random;

/**
 * @author Mendy
 * @create 2023-07-21 16:08
 */

@SpringBootApplication

public class Main {

    public static void main(String[] args) {
         SpringApplication.run(Main.class, args);

    }


    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){

        return args -> {
            var faker = new Faker();
            var name = faker.name();
            Random random = new Random();

            String firstName = name.firstName();
            String lastName = name.lastName();

            int age = random.nextInt(16, 99);

            Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;

            Customer customer = new Customer(
                    firstName + " " + lastName,
                    firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com",
                    age,
                    gender);

            customerRepository.save(customer);
        };
    }








}
