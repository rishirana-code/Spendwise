package com.spendwise.spendwise;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

     public String getGreeting(){
         return "Hello! MEOWWWWWWWWWWW";
     }

     public String greetByName(String name){
          return "Hello MEOWWWWWWWWW, "+name+"!";
     }
      
}

