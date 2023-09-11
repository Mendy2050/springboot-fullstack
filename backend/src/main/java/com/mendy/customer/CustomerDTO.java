package com.mendy.customer;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

/**
 * @author Mendy
 * @create 2023-09-06 20:20
 */


public record CustomerDTO(
        Long id,
        String name,
        String email,
        Gender gender,
        Integer age,
        List<String> roles,
        String username
) {


}
