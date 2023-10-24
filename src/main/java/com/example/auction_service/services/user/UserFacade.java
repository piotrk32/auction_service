package com.example.auction_service.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public boolean userExists(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = jwt.getClaim("email");
        return userService.userExistsByEmail(email);
    }
    public void deleteCustomerById(Long userId) {
        userService.deleteUserById(userId);
    }



}
