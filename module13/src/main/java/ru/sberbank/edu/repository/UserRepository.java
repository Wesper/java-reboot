package ru.sberbank.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.edu.entity.UserEntity;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long> {
}