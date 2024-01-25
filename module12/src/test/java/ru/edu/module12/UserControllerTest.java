package ru.edu.module12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.edu.module12.config.SecurityConfig;
import ru.edu.module12.controller.UserController;
import ru.edu.module12.entity.UserEntity;
import ru.edu.module12.service.UserService;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    List<UserEntity> users;

    @BeforeEach
    public void setup() {
        List<UserEntity> users = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.setAge(1);
        user1.setName("Olga");
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setAge(2);
        user2.setName("Viktor");
        user2.setId(2L);
        users.add(user1);
        users.add(user2);
        this.users = users;
    }

    @Test
    void whenAnonymousUser() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user", password = "654321", roles = "USER")
    void whenAuthorityUser() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("showAllUsers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"USER", "ADMIN"})
    void whenAuthorityAdmin() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("showAllUsers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andDo(MockMvcResultHandlers.print());
    }

}
