package com.TestApplication.Services;

import com.TestApplication.Config.Helpers;
import com.TestApplication.Config.JwtService;
import com.TestApplication.Repository.UsersRepository;
import com.TestApplication.Schemas.LoginRequest;
import com.TestApplication.Schemas.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final UsersRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final Helpers helpers;


    public LoginResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = appUserRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        helpers.revokeUsersTokens(user);

        String token = jwtService.generateToken(user);
        helpers.saveUserToken(user, token);
        return new LoginResponse(token);
    }
}
