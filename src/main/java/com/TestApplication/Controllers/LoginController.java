package com.TestApplication.Controllers;

import com.TestApplication.Config.Helpers;
import com.TestApplication.Config.JwtService;
import com.TestApplication.Repository.UsersRepository;
import com.TestApplication.Schemas.LoginRequest;
import com.TestApplication.Schemas.LoginResponse;
import com.TestApplication.Services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return loginService.login(request);
    }
}
