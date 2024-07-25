package com.masraf_takip.masraf_takip.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.masraf_takip.masraf_takip.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) {
        
        return User.withUsername(userRepository.findByName(name).getName())
                   .password(userRepository.findByName(name).getPassword())
                   .authorities(Collections.emptyList())
                   .build();
    }
}

