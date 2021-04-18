package com.weather.api.service;

import com.weather.api.model.RegisterRequest;
import com.weather.api.model.User;
import com.weather.api.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }


    @Transactional
    @Override
    public Optional<User> signup(RegisterRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setDob(request.getDob());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        usersRepository.saveUser(user);
        return Optional.of(user);
    }
}
