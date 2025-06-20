package com.agbafune.tradesys.web;


import com.agbafune.tradesys.domain.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    void testCreateUser() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser245\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.username").value("testUser245"))
                .andExpect(jsonPath("$.gemCount").value(0));
    }


    @Test
    void testCreateUser_ShouldFailOnConflict() throws Exception {
        // create user successfully
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"sameUserName\"}"))
                .andExpect(status().isCreated());

        // attempt to create the same user
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"sameUserName\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetAllUsers() throws Exception {
        userService.create("user1");
        userService.create("user2");

        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].username")
                        .value(Matchers.hasItems("user1", "user2"))
                );
    }
}