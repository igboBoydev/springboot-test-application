package com.TestApplication.Services;

import com.TestApplication.Config.EmailValidator;
import com.TestApplication.Config.Helpers;
import com.TestApplication.Config.JwtService;
import com.TestApplication.Enums.Role;
import com.TestApplication.Exception.ApiRequestException;
import com.TestApplication.Models.ConfirmationToken;
import com.TestApplication.Models.Users;
import com.TestApplication.Repository.UsersRepository;
import com.TestApplication.Schemas.LoginResponse;
import com.TestApplication.Schemas.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final UserService userService;
    private final JwtService jwtService;
    private final UsersRepository usersRepository;
    private final Helpers helpers;
    private final ConfirmationTokenService confirmationTokenService;

    public String register (RegistrationRequest request){
        //TODO: validate user email
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if(!isEmailValid){
            throw new IllegalStateException("email not valid");
        }

        //TODO: create a user email token from user details
        //NOTE: this token is supposed to be sent the user's email for email verification but since this is just for test I will not be implementing the email part of the service.
        String token = userService.signUpUser(
                new Users(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), Role.USER)
        );

        //TODO: return token
        return token;
    }


    @Transactional
    public LoginResponse confirmToken(String token) {
        //TODO: get token exists
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        {
                            throw new ApiRequestException("Token not found");
                        }
                );

        //TODO:  check if token ecists
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ApiRequestException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        // TODO: check if token is expired
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ApiRequestException("Token expired");
        }

        //TODO set confirmed date
        confirmationTokenService.setConfirmedAt(token);

        //TODO: set user enabled status to true
        userService.enableAppUser(
                confirmationToken.getUser().getEmail());

        //TODO: get user by confirmation token
        var user = usersRepository.findByEmail(confirmationToken.getUser().
                        getEmail()).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //TODO: generate jwt for user
        var jwtToken = jwtService.generateToken(user);

        //TODO: save user jwt token
        helpers.saveUserToken(user, jwtToken);

        //TODO: return token
        return new LoginResponse(jwtToken);
    }
}
