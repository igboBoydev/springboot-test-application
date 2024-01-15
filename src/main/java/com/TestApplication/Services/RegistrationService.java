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
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if(!isEmailValid){
            throw new IllegalStateException("email not valid");
        }

        String token = userService.signUpUser(
                new Users(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), Role.USER)
        );

        return token;
    }


    @Transactional
    public LoginResponse confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        {
                            throw new ApiRequestException("Token not found");
                        }
                );
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ApiRequestException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ApiRequestException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(
                confirmationToken.getUser().getEmail());
        var user = usersRepository.findByEmail(confirmationToken.getUser().
                        getEmail()).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        helpers.saveUserToken(user, jwtToken);
        return new LoginResponse(jwtToken);
    }
}
