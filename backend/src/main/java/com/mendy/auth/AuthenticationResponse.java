package com.mendy.auth;

import com.mendy.customer.CustomerDTO;

/**
 * @author Mendy
 * @create 2023-09-07 14:15
 */
public record AuthenticationResponse(String token, CustomerDTO customerDTO) {
}
