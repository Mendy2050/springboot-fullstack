package com.mendy.auth;

/**
 * @author Mendy
 * @create 2023-09-07 13:56
 */
public record AuthenticationRequest(
        String username,
        String password

) {
}
