package com.example.Upwork_Backend_8.security.services;


import com.example.Upwork_Backend_8.security.config.JwtService;
import com.example.Upwork_Backend_8.security.dto.AuthRequest;
import com.example.Upwork_Backend_8.security.dto.AuthResponse;
import com.example.Upwork_Backend_8.security.dto.RegisterRequest;
import com.example.Upwork_Backend_8.security.enums.Role;
import com.example.Upwork_Backend_8.users.entity.UserInfo;
import com.example.Upwork_Backend_8.users.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        if (authentication.isAuthenticated()) {
            UserInfo user = repository.findByUsername(authRequest.username())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Return AuthResponse with user details
            return AuthResponse.builder()
                    .accessToken(jwtService.generateToken(authRequest.username()))
                    .userId(user.getId())                          // Added line
                    .username(user.getUsername())                  // Added line
                    .roles(user.getRole().name())                  // Added line
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid users request..!!");
        }
    }

    public AuthResponse register(RegisterRequest request) {
        var user = UserInfo.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.User)
                .build();

        repository.save(user);

        return authenticate(AuthRequest.builder()
                .username(request.username())
                .password(request.password())                      // Corrected from request.username() to request.password()
                .build());
    }
}

