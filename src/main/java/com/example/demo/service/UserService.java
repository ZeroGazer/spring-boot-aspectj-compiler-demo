package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public List<User> getCacheableUsers() {
    log.debug("getCacheableUsers");
    return this.getPrivateUsersWithCacheableAnnotation();
  }

  @Cacheable(cacheNames = "users")
  private List<User> getPrivateUsersWithCacheableAnnotation() {
    log.debug("getPrivateCacheableUsers");
    return userRepository.findAll();
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Transactional
  public User saveUser(User user) {
    return userRepository.saveAndFlush(user);
  }

  public User saveUserWithError(User user)  {
    return this.savePrivateUserWithTransactionalAnnotation(user);
  }

  @Transactional
  private User savePrivateUserWithTransactionalAnnotation(User user) {
    userRepository.saveAndFlush(user);
    throw new RuntimeException();
  }

}
