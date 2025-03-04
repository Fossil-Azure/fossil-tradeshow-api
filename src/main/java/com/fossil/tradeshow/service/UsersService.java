package com.fossil.tradeshow.service;

import com.fossil.tradeshow.exceptions.UserAlreadyExistsException;
import com.fossil.tradeshow.model.Users;
import com.fossil.tradeshow.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users addUser(Users user) {
        if (usersRepository.existsByEmailId(user.getEmailId())) {
            throw new UserAlreadyExistsException("User with emailId " + user.getEmailId() + " already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserData(String emailId) {
        return usersRepository.findByEmailIdIgnoreCase(emailId);
    }
}