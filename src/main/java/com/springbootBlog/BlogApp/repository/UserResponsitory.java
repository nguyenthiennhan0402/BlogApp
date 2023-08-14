package com.springbootBlog.BlogApp.repository;

import com.springbootBlog.BlogApp.entity.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserResponsitory extends Repository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
