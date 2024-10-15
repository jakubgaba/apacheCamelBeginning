package com.example;

import org.apache.camel.builder.RouteBuilder;

public class Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        System.out.println("Hello there");
        
    }
}
