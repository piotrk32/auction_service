package com.example.auction_service.services.user;


import com.example.auction_service.models.user.User;
import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("User", "No user found with email: " + email);
        }
        return userOptional.get();
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }



}
