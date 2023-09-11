package com.mendy.exception;

import java.time.LocalDateTime;

/**
 * @author Mendy
 * @create 2023-09-07 10:27
 */
public record ApiError(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
