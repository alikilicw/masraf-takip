package com.masraf_takip.masraf_takip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masraf_takip.masraf_takip.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByName(String name);
}
