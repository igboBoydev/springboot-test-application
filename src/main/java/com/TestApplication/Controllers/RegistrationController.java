package com.TestApplication.Controllers;


import com.TestApplication.Schemas.LoginResponse;
import com.TestApplication.Schemas.RegistrationRequest;
import com.TestApplication.Services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public LoginResponse confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
