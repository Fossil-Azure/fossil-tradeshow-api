package com.fossil.tradeshow.controller;

import com.fossil.tradeshow.model.Users;
import com.fossil.tradeshow.repository.UsersRepository;
import com.fossil.tradeshow.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Fetch user from database
            Optional<Users> optionalUser = usersRepository.findByEmailId(authRequest.getEmailId());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(401).body("User not found");
            }

            // Unwrap the Optional to get the actual user
            Users user = optionalUser.get();

            // Verify the password
            if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }

            // Check if this is the user's first login
            boolean isFirstLogin = (user.getLastLogin() == null);

            // Update lastLogin field
            user.setLastLogin(new Date());
            usersRepository.save(user);

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmailId());
            return ResponseEntity.ok(new AuthResponse(token, user, isFirstLogin));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred");
        }
    }

    // Make these inner classes public
    public static class AuthRequest {
        private String emailId;
        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }
    }

    public static class AuthResponse {
        private String token;
        private Users user;
        private boolean firstLogin;

        public AuthResponse(String token, Users user, boolean isFirstLogin) {
            this.token = token;
            this.user = user;
            this.firstLogin = isFirstLogin;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Users getUser() {
            return user;
        }

        public void setUser(Users user) {
            this.user = user;
        }

        public boolean isFirstLogin() {
            return firstLogin;
        }

        public void setFirstLogin(boolean firstLogin) {
            this.firstLogin = firstLogin;
        }
    }
}