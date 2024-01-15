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


    //TODO: service for login logic
    public LoginResponse login(LoginRequest request){
        //TODO: authenticate user email and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        //TODO: get user from database
        var user = appUserRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //TODO: set all already existing user tokens to expired and revoked
        helpers.revokeUsersTokens(user);


        //TODO: create a user access token
        String token = jwtService.generateToken(user);

        //TODO: save user access token
        helpers.saveUserToken(user, token);

        //TODO: return token
        return new LoginResponse(token);
    }
}
