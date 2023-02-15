package com.example.bims;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class HelloWorld {
    @GetMapping("/hello")
    @ResponseBody
    public String helloWorld(){
        return "BIMS application";
    }
}
