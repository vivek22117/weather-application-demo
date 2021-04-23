package com.weather.api.service;

import com.weather.api.exception.UserAuthenticationException;
import com.weather.api.model.AuthenticationResponse;
import com.weather.api.model.LoginRequest;
import com.weather.api.model.RegisterRequest;
import com.weather.api.model.Profile;
import com.weather.api.repository.ProfileRepository;
import com.weather.api.security.AppJwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.weather.api.util.AppUtility.JWT_TOKEN_VALIDITY;

@Service
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;
    private final AppJwtTokenUtil jwtTokenUtil;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, ProfileRepository profileRepository,
                           AuthenticationManager authenticationManager, AppJwtTokenUtil jwtTokenUtil) {
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Transactional(readOnly = true)
    @Override
    public Profile getCurrentUser() {
        User principal = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return profileRepository.getUserByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found - " + principal.getUsername()));
    }


    @Transactional
    @Override
    public Profile signup(RegisterRequest request) {
        Profile profile = null;
        try {
            profile = new Profile();

            profile.setUsername(request.getUsername());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(request.getDob(), formatter);
            System.out.println(date);
            profile.setDob(date);
            profile.setPassword(passwordEncoder.encode(request.getPassword()));

            profileRepository.saveUser(profile);
        } catch (Exception ex) {
            log.error("Failed to save new profile with Id: " + request.getUsername());
            throw new InvalidParameterException("Please provide valid values for registration");
        }

        return profile;
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        try {
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
        } catch (Exception ex) {
            log.error("Error while login api call" + ex.getMessage());
            throw new UserAuthenticationException("User validation failed, please provide valid credentials!");
        }
    }

    @Override
    public void logout(Profile profile) {

    }
}
