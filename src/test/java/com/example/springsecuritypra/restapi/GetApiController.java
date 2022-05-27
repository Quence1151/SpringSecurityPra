package com.example.springsecuritypra.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/get")
@RestController
public class GetApiController {

    @GetMapping("/hello")
    public String getHello(){
        return "get Hello";
    }
}
