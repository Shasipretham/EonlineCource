package com.Eonline.Education.repository;

import java.util.List;

import com.Eonline.Education.modals.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);
    public List<User> findAllByOrderByCreatedAtDesc();

    boolean existsByEmail(String userEmail);
}
