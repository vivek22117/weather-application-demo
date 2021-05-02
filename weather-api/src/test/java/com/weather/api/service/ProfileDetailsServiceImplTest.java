package com.weather.api.service;

import com.weather.api.model.Profile;
import com.weather.api.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProfileDetailsServiceImplTest {

    @InjectMocks
    private ProfileDetailsServiceImpl profileDetailsService;

    @Mock
    private ProfileRepository profileRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername() {
        Profile profile = new Profile();
        profile.setDob(LocalDate.now().minusYears(11));
        profile.setUsername("vivek@gmail.com");
        profile.setPassword("vivek@2244");

        Mockito.when(profileRepository.getUserByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(profile));

        UserDetails userDetails = profileDetailsService.loadUserByUsername("vivek@gmail.com");


    }
}