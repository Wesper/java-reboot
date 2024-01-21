package com.example.module11;

import com.example.module11.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
class Module11ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private UserController controller;

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    void contextLoads() {
    }

    @Test
    void whenAllUsersShow() {
        ResponseEntity<String> rs = testRestTemplate.getForEntity("http://localhost:" + port + "/users/get", String.class);
        assertAll(
                () -> assertTrue(rs.getStatusCode().is2xxSuccessful()),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Artem")),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Anna")),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Maria")),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Oleg")),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Vika"))

        );
    }

    @Test
    void whenShowFormForAddNewUser() {
        ResponseEntity<String> rs = testRestTemplate.getForEntity("http://localhost:" + port + "/users/add", String.class);
        assertAll(
                () -> assertTrue(rs.getStatusCode().is2xxSuccessful()),
                () -> assertNotNull(rs.getBody())
        );
    }

    @Test
    void whenAddNewUser() {
        ResponseEntity<String> addUserRs = testRestTemplate.postForEntity("http://localhost:" + port + "/users/add?name=Slava&age=50", null, String.class);
        assertTrue(addUserRs.getStatusCode().is3xxRedirection());

        ResponseEntity<String> showAllUserRs = testRestTemplate.getForEntity("http://localhost:" + port + "/users/get", String.class);
        assertAll(
                () -> assertTrue(showAllUserRs.getStatusCode().is2xxSuccessful()),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Artem")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Anna")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Maria")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Oleg")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Vika")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Slava"))

        );
    }

    @Test
    void whenShowFormForEditUser() {
        ResponseEntity<String> rs = testRestTemplate.getForEntity("http://localhost:" + port + "/users/edit/1", String.class);
        assertAll(
                () -> assertTrue(rs.getStatusCode().is2xxSuccessful()),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Artem"))
        );
    }

    @Test
    void whenEditUser() {
        ResponseEntity<String> editUserRs = testRestTemplate.postForEntity("http://localhost:" + port + "/users/edit?id=1&name=Slava&age=50", null, String.class);
        assertTrue(editUserRs.getStatusCode().is3xxRedirection());

        ResponseEntity<String> showAllUserRs = testRestTemplate.getForEntity("http://localhost:" + port + "/users/get", String.class);
        assertAll(
                () -> assertTrue(showAllUserRs.getStatusCode().is2xxSuccessful()),
                () -> assertFalse(Objects.requireNonNull(showAllUserRs.getBody()).contains("Artem")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Slava")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Anna")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Maria")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Oleg")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Vika")),
                () -> assertTrue(Objects.requireNonNull(showAllUserRs.getBody()).contains("Slava"))

        );
    }

    @Test
    void whenDeleteUser() {
        ResponseEntity<String> rs = testRestTemplate.getForEntity("http://localhost:" + port + "/users/delete/1", String.class);
        assertAll(
                () -> assertTrue(rs.getStatusCode().is2xxSuccessful()),
                () -> assertFalse(Objects.requireNonNull(rs.getBody()).contains("Artem")),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Anna")),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Maria")),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Oleg")),
                () -> assertTrue(Objects.requireNonNull(rs.getBody()).contains("Vika"))

        );
    }
}
