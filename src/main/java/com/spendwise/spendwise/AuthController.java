package com.spendwise.spendwise;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {

      private final AuthService authService;

      public AuthController(AuthService authService){
          this.authService=authService;
      }

      @PostMapping("/register")
      public String register(@RequestBody AuthRequest request){
           return authService.register(request.getEmail(),request.getPassword());
      }

      @PostMapping("/login")
      public String  login(@RequestBody AuthRequest request){
          String token = authService.login(request.getEmail(), request.getPassword());
          if(token == null){
              return "Invalid email or password";
          }
          return token;
      }
}
