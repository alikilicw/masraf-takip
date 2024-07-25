package com.masraf_takip.masraf_takip.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.masraf_takip.masraf_takip.model.User;
import com.masraf_takip.masraf_takip.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);
        return (user.isPresent()) ? user.get() : null;
    }

    public User add(User user) {

        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);

        return userRepository.save(user);
    }

    public User update(String id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            User updateUser = user.get();
            if(userDetails.getName() != null) updateUser.setName((userDetails.getName()));
            if(userDetails.getEmail() != null) updateUser.setEmail(userDetails.getEmail());
            if(userDetails.getPassword() != null) updateUser.setPassword(userDetails.getPassword());
            return updateUser;
        } else {
            return null;
        }
    }

    public User deleteById(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.deleteById(id);
            return user.get();
        } else {
            return null;
        }
    }

}
