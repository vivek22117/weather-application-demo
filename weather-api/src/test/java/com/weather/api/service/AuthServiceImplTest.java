package com.weather.api.service;

import com.weather.api.model.Profile;
import com.weather.api.repository.ProfileRepository;
import com.weather.api.security.AppJwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AppJwtTokenUtil appJwtTokenUtil;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(authentication.getPrincipal())
                .thenReturn(new User("vivek@gmail.com", "vivek@2244", new ArrayList<>()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void getCurrentUser() {
        Profile profile = new Profile();
        profile.setUsername("vivek@gmail.com");
        profile.setPassword("vivek@2244");
        profile.setDob(LocalDate.now().minusYears(33));

        Mockito.when(profileRepository.getUserByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(profile));

        Profile currentUser = authService.getCurrentUser();

        Assertions.assertEquals("vivek@gmail.com", currentUser.getUsername());
    }

    @Test
    void signup() {
    }

    @Test
    void login() {
    }
}