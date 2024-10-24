package com.samanvay.admin.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.samanvay.admin.entity.Role;
import com.samanvay.admin.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RoleController {

  @Autowired
  private final RoleRepository roleRepository;

  public RoleController(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/api/roles")
  public Iterable<Role> getRoles() {
    return this.roleRepository.findAll();
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/api/roles")
  public Role saveRole(@RequestBody Role role) {
    Role entity = this.roleRepository.save(role);

    return entity;
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @DeleteMapping("/api/roles/{id}")
  public String deleteRole(@PathVariable Long id) {
    this.roleRepository.deleteById(id);

    return "Role with ID: " + id + " deleted successfully";
  }  
}
