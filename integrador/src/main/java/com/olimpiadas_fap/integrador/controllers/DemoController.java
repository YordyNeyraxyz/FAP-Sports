package com.olimpiadas_fap.integrador.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class DemoController {
    @GetMapping("/demo")
    
    public String demoString(){
        return "Hello World!";
    }
}



