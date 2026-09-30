package com.example.user.service;

import java.util.UUID;

import com.example.user.entity.User;
import com.example.user.exception.UserNotFoundException;
import com.example.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
public class UserService {

  private final UserRepository repository;

  public User createUser(User user) {
    if (user.getAge() < 0) {
      throw new IllegalArgumentException("나이는 음수일 수 없습니다.");
    }
    String userId = UUID.randomUUID().toString();
    user.setUserId(userId);
    repository.save(user);
    return user;
  }

  public User getUser(String userId) {
    User foundUser = repository.findById(userId);
    if (foundUser == null) {
      throw new UserNotFoundException("User를 찾을 수 없습니다. User ID: " + userId);
    }
    return foundUser;
  }

  public User updateUser(String userId, User user) {
    if (user.getAge() < 0) {
      throw new IllegalArgumentException("나이는 음수일 수 없습니다.");
    }

    User existingUser = getUser(userId);
    existingUser.setName(user.getName());
    existingUser.setAge(user.getAge());

    return repository.update(existingUser);
  }

  public void deleteUser(String userId) {
    getUser(userId);
    repository.deleteById(userId);
  }
}