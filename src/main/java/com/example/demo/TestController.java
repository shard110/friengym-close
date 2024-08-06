package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/vsctest")
public class TestController {

    @GetMapping(value = {"", "/"})
    public String enter(){
        return "hello vsc";
    }
}
