package com.TestApplication.Config;


import com.TestApplication.Enums.TokenType;
import com.TestApplication.Models.Token;
import com.TestApplication.Models.Users;
import com.TestApplication.Repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class Helpers {
    private final TokenRepository tokenRepository;

    public Helpers(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void saveUserToken(Users user, String token) {
        var Token = com.TestApplication.Models.Token.builder().users(user).key(token).revoked(false).expired(false).tokenType(TokenType.BEARER).build();
        tokenRepository.save(Token);
    }

    public void revokeUsersTokens(Users user){
        var validUserTokens = tokenRepository.findAllValidTokensByUsers(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public boolean checkTokenValidity(String token){
        return tokenRepository.findByKey(token).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
    }

    public void logout(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByKey(jwt).orElse(null);
        if(storedToken != null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }
}
