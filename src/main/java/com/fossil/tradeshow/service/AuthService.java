package com.fossil.tradeshow.service;

import com.fossil.tradeshow.model.Users;
import com.fossil.tradeshow.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Update Password Logic
    public String updatePassword(String email, String newPassword) {
        Optional<Users> optionalUser = usersRepository.findByEmailId(email);
        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        Users user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);

        return "Password updated successfully!";
    }
}
