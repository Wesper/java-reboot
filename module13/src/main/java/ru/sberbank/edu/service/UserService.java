package ru.sberbank.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.edu.entity.UserEntity;
import ru.sberbank.edu.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

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