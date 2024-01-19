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
import ru.edu.module12.controller.AdminController;
import ru.edu.module12.entity.UserEntity;
import ru.edu.module12.service.UserService;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(AdminController.class)
@Import(SecurityConfig.class)
public class AdminControllerTest {

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
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user", password = "654321", roles = "USER")
    void whenUserWithIncorrectRoleShowUsersForm() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    @WithMockUser(username = "user", password = "654321", roles = "USER")
    void whenUserWithIncorrectRoleShowAddUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/add"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    @WithMockUser(username = "user", password = "654321", roles = "USER")
    void whenUserWithIncorrectRoleRqAddUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/add?id=3&name=Oleg&age=3"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    @WithMockUser(username = "user", password = "654321", roles = "USER")
    void whenUserWithIncorrectRoleShowEditUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    @WithMockUser(username = "user", password = "654321", roles = "USER")
    void whenUserWithIncorrectRoleRqEditUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/edit?id=1&name=Olga1&age=20"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    @WithMockUser(username = "user", password = "654321", roles = "USER")
    void whenUserWithIncorrectRoleRqDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"USER", "ADMIN"})
    void whenAdminShowUsersForm() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("showAllUsersAdmin"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"USER", "ADMIN"})
    void whenAdminShowAddUserForm() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("addUserForm"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"USER", "ADMIN"})
    void whenAdminRqAddUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/add?id=3&name=Oleg&age=3"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:users"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("users"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"USER", "ADMIN"})
    void whenAdminShowEditUserForm() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("editUserForm"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"USER", "ADMIN"})
    void whenAdminRqEditUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/edit?id=2&name=Viktor1&age=3"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:users"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("users"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"USER", "ADMIN"})
    void whenUserRqDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:../users"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("../users"))
                .andDo(MockMvcResultHandlers.print());
    }

}
