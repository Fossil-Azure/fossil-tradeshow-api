package com.fossil.tradeshow.service;

import com.fossil.tradeshow.model.Users;
import com.fossil.tradeshow.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;

    public CustomUserDetailsService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        Optional<Users> optionalUser = userRepository.findByEmailId(emailId);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email Id: " + emailId);
        }

        // Unwrap the Optional to get the actual user
        Users user = optionalUser.get();

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmailId())
                .password(user.getPassword())
                .build();
    }
}