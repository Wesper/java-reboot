package ru.edu.module12;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.module12.entity.UserEntity;
import ru.edu.module12.repository.UserRepository;
import ru.edu.module12.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private final List<UserEntity> users = new ArrayList<>();

    @BeforeEach
    void setup() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setName("Olga");
        user1.setAge(1);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setName("Viktor");
        user2.setAge(2);
        users.add(user1);
        users.add(user2);
    }

    @AfterEach
    void teardown() {
        service = null;
    }

    @Test
    public void getAllUsers() {
        Mockito.when(repository.findAll()).thenReturn(users);
        Assertions.assertEquals(users, service.getAllUsers());
    }

    @Test
    public void getUserById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(users.get(0)));
        Assertions.assertEquals(users.get(0), service.getUserById(1L).get());
    }

    @Test
    public void createUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Olga");
        user.setAge(1);

        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        UserEntity savedUser = service.createUser(user);
        Assertions.assertEquals(savedUser.getId(), user.getId());
        Assertions.assertEquals(savedUser.getAge(), user.getAge());
        Assertions.assertEquals(savedUser.getName(), user.getName());
        Mockito.verify(repository, Mockito.times(1)).save(user);
    }

    @Test
    public void deleteById() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Olga");
        user.setAge(1);

        service.deleteById(user.getId());

        Mockito.verify(repository, Mockito.times(1)).deleteById(user.getId());
    }

    @Test
    public void updateById() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Olga");
        user.setAge(1);

        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        UserEntity editedUser = service.updateById(user);
        Assertions.assertEquals(editedUser.getId(), user.getId());
        Assertions.assertEquals(editedUser.getAge(), user.getAge());
        Assertions.assertEquals(editedUser.getName(), user.getName());
        Mockito.verify(repository, Mockito.times(1)).save(user);
    }
}
