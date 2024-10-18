package com.samanvay.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.samanvay.admin.entity.Address;
import com.samanvay.admin.entity.UserReference;
import com.samanvay.admin.entity.User;
import com.samanvay.admin.entity.DTO.UserDTO;
import com.samanvay.admin.repository.AddressRepository;
import com.samanvay.admin.repository.MandalRepository;
import com.samanvay.admin.repository.UserReferenceRepository;
import com.samanvay.admin.repository.RoleRepository;
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

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private MandalRepository mandalRepository;

  @Autowired
  private UserReferenceRepository userReferenceRepository;

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
  public User saveUser(@RequestBody UserDTO userToSave) {

      Address savedAddress = addressRepository.save(userToSave.getAddress());
      
      User updatedUser = User.builder()
        .name(userToSave.getName())
        .email(userToSave.getEmail())
        .phoneNumber(userToSave.getPhoneNumber())
        .dateOfBirth(userToSave.getDateOfBirth())
        
        // Updated Address
        .address(savedAddress)
        
        // Default values set for new user
        .isActive(false)
        .mandal(null)
        .referenceContacts(null)
        .role(null)
        .build();
      
      return this.userRepository.save(updatedUser);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @DeleteMapping("/api/users/{id}")
  public String deleteUser(@PathVariable Long id) {
    this.userRepository.deleteById(id);

    return "User with user ID: " + id + " deleted successfully";
  }
  
  @CrossOrigin(origins = "http://localhost:5173")
  @PutMapping("/api/users/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody UserDTO userToSave) {    
    String UserNotFoundMessage = "User not found with id: " + id;
    User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException(UserNotFoundMessage));

    if(!existingUser.getIsActive() && userToSave.getIsActive()) {
      if(userToSave.getRoleId() == null) {
        throw new RuntimeException("Role is required for active users");
      } 
      if(userToSave.getMandalId() == null) {
        throw new RuntimeException("Mandal is required for active users");
      }
      if(userToSave.getReferenceContacts() == null) {
        throw new RuntimeException("Reference Contacts are required for active users");
      }
    }

    if(existingUser.getIsActive()) {
      existingUser.setAddress(userToSave.getAddress());
      existingUser.setDateOfBirth(userToSave.getDateOfBirth());

      // Roles can be updated by Super Admin only
      // Will be added later

    } else {
      existingUser.setName(userToSave.getName());
      existingUser.setDateOfBirth(userToSave.getDateOfBirth());
      existingUser.setAddress(userToSave.getAddress());
      existingUser.setEmail(userToSave.getEmail());
      existingUser.setPhoneNumber(userToSave.getPhoneNumber());
      
      if(userToSave.getRoleId() != null) {
        roleRepository.findById(userToSave.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found with id: " + userToSave.getRoleId()));
        
        existingUser.setRole(roleRepository.findById(userToSave.getRoleId()).get());
      } 
      if(userToSave.getMandalId() != null) {
        mandalRepository.findById(userToSave.getMandalId()).orElseThrow(() -> new RuntimeException("Mandal not found with id: " + userToSave.getMandalId()));
        
        existingUser.setMandal(mandalRepository.findById(userToSave.getMandalId()).get());
      } 
      if(userToSave.getReferenceContacts() != null) {
        userRepository.findById(userToSave.getReferenceContacts().getPrimaryContactId()).orElseThrow(() -> new RuntimeException("Primary Contact not found with id: " + userToSave.getReferenceContacts().getPrimaryContactId()));
        userRepository.findById(userToSave.getReferenceContacts().getSecondaryContactId()).orElseThrow(() -> new RuntimeException("Secondary Contact not found with id: " + userToSave.getReferenceContacts().getSecondaryContactId()));

        User newUserPrimaryContact = userRepository.findById(userToSave.getReferenceContacts().getPrimaryContactId()).get();
        User newUserSecondaryContact = userRepository.findById(userToSave.getReferenceContacts().getSecondaryContactId()).get();

        UserReference newUserReference = UserReference
          .builder()
          .contactDescription(userToSave.getReferenceContacts().getContactDescription())
          .primaryContact(newUserPrimaryContact)
          .secondaryContact(newUserSecondaryContact)
          .build();

        UserReference referencesPairToSave = userReferenceRepository.save(newUserReference);
        
        existingUser.setReferenceContacts(referencesPairToSave); 
      }

      existingUser.setIsActive(userToSave.getIsActive());
    }

    User updatedEntity = userRepository.save(existingUser);

    return updatedEntity;
  }
}
