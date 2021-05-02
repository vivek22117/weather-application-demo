package com.weather.api.service;

import com.weather.api.exception.UserAuthenticationException;
import com.weather.api.model.AuthenticationResponse;
import com.weather.api.model.LoginRequest;
import com.weather.api.model.Profile;
import com.weather.api.model.RegisterRequest;
import com.weather.api.repository.ProfileRepository;
import com.weather.api.security.AppJwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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

    private ArgumentCaptor<Profile> profileArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(authentication.getPrincipal())
                .thenReturn(new User("vivek@gmail.com", "vivek@2244", new ArrayList<>()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void shouldGetCurrentUser() {
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
    @DisplayName(value = "Should successfully register new user")
    public void shouldSignupWithValidParameters() {
        Mockito.doNothing().when(profileRepository).saveUser(ArgumentMatchers.any(Profile.class));

        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                authService.signup(new RegisterRequest("vivek@gmail.com", "vivek@2244", "2020-11-11"));
            }
        });
    }

    @Test
    @DisplayName(value = "Should fail when request parameters are invalid")
    public void shouldSignupWithInvalidParameters() {
        Mockito.doNothing().when(profileRepository).saveUser(ArgumentMatchers.any(Profile.class));

        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                authService.signup(new RegisterRequest("vivek@gmail.com", "vivek@2244", "20-11-11"));
            }
        });
    }

    @Test
    public void shouldLoginWithValidCredentials() {
        Mockito.when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(authentication);
        Mockito.when(appJwtTokenUtil.generateTokenWithUsername(ArgumentMatchers.anyString())).thenReturn("Dummy-TOken");

        AuthenticationResponse login = authService.login(new LoginRequest("vivek@gmail.com", "vivek@2244"));

        Assertions.assertNotNull(login);
        Assertions.assertEquals(login.getUsername(), "vivek@gmail.com");
    }



}