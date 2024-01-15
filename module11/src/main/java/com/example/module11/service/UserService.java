package com.example.module11.service;

import com.example.module11.entity.UserEntity;
import com.example.module11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    ApplicationContext context;

    public List<UserEntity> getAllUsers() {
        return repository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return repository.findById(id);
    }

    public UserEntity createUser(UserEntity user) {
        return repository.save(user);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public UserEntity updateById(UserEntity entity) {
        return repository.save(entity);
    }
}
