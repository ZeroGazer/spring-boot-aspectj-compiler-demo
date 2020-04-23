package com.example.demo.controller;

import com.example.demo.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Test
  void testPrivateCacheableFunctionCall(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
    // Get all users from DB
    var usersJson = mockMvc.perform(get("/user/cacheable").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    List<User> users = objectMapper.readValue(usersJson,
        objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

    // Create a new user
    var newUser = new User();
    newUser.setUsername("demo3");
    mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser))).andExpect(status().isOk());

    // Get all users from DB
    var newUsersJson = mockMvc.perform(get("/user/cacheable").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    List<User> newUsers = objectMapper.readValue(newUsersJson,
        objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

    // Expect cached users are returned
    assertThat(users.size()).isEqualTo(2);
    assertThat(users).isEqualTo(newUsers);
  }

  @Test
  void testPrivateTransactionalFunctionCall(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
    // Get all users from DB
    var usersJson = mockMvc.perform(get("/user").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    List<User> users = objectMapper.readValue(usersJson,
        objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

    // Create a new user with error
    var newUser = new User();
    newUser.setUsername("demo3");
    mockMvc.perform(post("/user/error").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser))).andExpect(status().is5xxServerError());

    // Get all users from DB
    var newUsersJson = mockMvc.perform(get("/user").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    List<User> newUsers = objectMapper.readValue(newUsersJson,
        objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

    // Expect DB has been rolled back
    assertThat(users.size()).isEqualTo(2);
    assertThat(users).isEqualTo(newUsers);
  }

}