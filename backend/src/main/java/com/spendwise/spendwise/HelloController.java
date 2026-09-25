package com.spendwise.spendwise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService){
        this.helloService=helloService;
    }

    @GetMapping("/hello")
    public String sayhello(){
        return helloService.getGreeting();
    }

    @GetMapping("/greet")
    public String greet(@RequestParam("name") String name){
        return helloService.greetByName(name);
    }

}
