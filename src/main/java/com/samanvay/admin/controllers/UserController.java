package com.samanvay.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.samanvay.admin.entity.Address;
import com.samanvay.admin.entity.User;

import com.samanvay.admin.repository.AddressRepository;
import com.samanvay.admin.repository.UserRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {
  
  @Autowired
  private final UserRepository userRepository;

  @Autowired
  private AddressRepository addressRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/api/users")
  public Iterable<User> getUsersList() {
      return this.userRepository.findAll();
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/api/users/{id}")
  public User getUserById(@PathVariable Long id) {
    return this.userRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/api/users")
  public User saveUser(@RequestBody User userToSave) {

      Address savedAddress = addressRepository.save(userToSave.getAddress());

      userToSave.setAddress(savedAddress);
      
      User entity = this.userRepository.save(userToSave);

      return entity;
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @DeleteMapping("/api/users/{id}")
  public String deleteUser(@PathVariable Long id) {
    this.userRepository.deleteById(id);

    return "User with user ID: " + id + " deleted successfully";
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PutMapping("/api/users/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
    User existingUser = userRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    existingUser.setName(updatedUser.getName());
    existingUser.setRole(updatedUser.getRole());
    existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
    existingUser.setIsActive(updatedUser.getIsActive());

    Address savedAddress = addressRepository.save(updatedUser.getAddress());
    existingUser.setAddress(savedAddress);

    User updatedEntity = userRepository.save(existingUser);

    return updatedEntity;
  }
}
