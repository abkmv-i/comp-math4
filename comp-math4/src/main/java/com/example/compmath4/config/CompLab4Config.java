package com.example.compmath4.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.example.compmath4.reader",
        "com.example.compmath4.approximation",
        "com.example.compmath4.service",
        "com.example.compmath4.gui"
})
public class CompLab4Config {
}
