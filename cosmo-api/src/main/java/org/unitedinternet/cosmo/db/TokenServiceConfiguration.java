package org.unitedinternet.cosmo.db;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.TokenService;

@Configuration
public class TokenServiceConfiguration {

    @Value("${cosmo.tickets.serverSecret}")
    private String serverSecret;

    @Value("${cosmo.tickets.serverInteger}")
    private Integer serverInteger;


    @Bean
    public TokenService getTokenService() {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();
        configureTokenService(tokenService);
        return tokenService;
    }

    private void configureTokenService(KeyBasedPersistenceTokenService tokenService) {
        tokenService.setServerSecret(serverSecret);
        tokenService.setServerInteger(serverInteger);
        tokenService.setPseudoRandomNumberBytes(16);
        tokenService.setSecureRandom(new SecureRandom());
    }
    
    public static void main(String[] args) {
        System.out.println(new SecureRandom().nextInt(Integer.MAX_VALUE));
    }
}
