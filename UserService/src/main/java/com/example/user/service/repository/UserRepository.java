package com.example.user.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user.service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
