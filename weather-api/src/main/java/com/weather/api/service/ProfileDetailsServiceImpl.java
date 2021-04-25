package com.weather.api.service;

import com.weather.api.model.Profile;
import com.weather.api.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ProfileDetailsServiceImpl implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public ProfileDetailsServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.getUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("No user found with name " + username));

        List<GrantedAuthority> authorityList = Arrays.asList(new SimpleGrantedAuthority("ADMIN"),
                new SimpleGrantedAuthority("USER"));

        return new User(profile.getUsername(), profile.getPassword(), authorityList);
    }
}
