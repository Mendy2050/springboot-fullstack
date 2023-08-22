package com.mendy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mendy
 * @create 2023-08-22 11:07
 */

@RestController
public class PingPongController {

    private static int COUNTER = 0;

    record PingPong(String result){}

    @GetMapping("/ping")
    public PingPong getPingPong(){
        return new PingPong("Pong: %s".formatted(++COUNTER));

    }

}

