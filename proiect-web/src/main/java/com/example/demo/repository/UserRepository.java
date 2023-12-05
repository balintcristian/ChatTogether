package com.example.demo.repository;

import com.example.demo.persistence.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLoginUsername(String loginUserName);

    @Override
    Optional<User> findById(Long aLong);
}
