package com.example.springgatewaywso2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class GatewayController {
    
    @GetMapping("/gateway/health")
    public Mono<String> health() {
        return Mono.just("It's work");
    }

    @GetMapping("/")
    public Mono<String> welcome() {
        return Mono.just("Welcome");
    }
}
