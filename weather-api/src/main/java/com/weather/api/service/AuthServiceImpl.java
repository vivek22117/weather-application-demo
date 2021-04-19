package com.weather.api.service;

import com.weather.api.model.AuthenticationResponse;
import com.weather.api.model.LoginRequest;
import com.weather.api.model.RegisterRequest;
import com.weather.api.model.Profile;
import com.weather.api.repository.UserRepository;
import com.weather.api.security.AppJwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static com.weather.api.util.AppUtility.JWT_TOKEN_VALIDITY;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AppJwtTokenUtil jwtTokenUtil;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                           AuthenticationManager authenticationManager, AppJwtTokenUtil jwtTokenUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Transactional
    @Override
    public Optional<Profile> signup(RegisterRequest request) {
        Profile profile = new Profile();

        profile.setUsername(request.getUsername());
        profile.setDob(request.getDob());
        profile.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.saveUser(profile);
        return Optional.of(profile);
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        User principal = (User) authenticate.getPrincipal();

        String token = jwtTokenUtil.generateTokenWithUsername(principal.getUsername());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .username(loginRequest.getUsername())
                .expiresAt(Instant.now().plusMillis(JWT_TOKEN_VALIDITY * 1000))
                .build();
    }

    @Override
    public void logout(Profile profile) {

    }
}
