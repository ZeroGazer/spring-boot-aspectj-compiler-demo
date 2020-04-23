package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
public class UserController {

  private final UserService userService;

  @GetMapping
  List<User> getUsers() {
    return userService.getUsers();
  }

  @GetMapping(path = "/cacheable")
  List<User> getCacheableUsers() {
    return userService.getCacheableUsers();
  }

  @PostMapping
  User saveUser(@RequestBody User user) {
    return userService.saveUser(user);
  }

  @PostMapping(path = "/error")
  User saveUserWithError(@RequestBody User user) {
    return userService.saveUserWithError(user);
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus
  public void return500() {
    // Do nothing
  }

}