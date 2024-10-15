package com.samanvay.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.samanvay.admin.repository.MandalRepository;

import com.samanvay.admin.entity.Mandal;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MandalController {
  @Autowired
  private final MandalRepository mandalRepository;

  public MandalController(MandalRepository MandalRepository) {
    this.mandalRepository = MandalRepository;
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/api/mandals")
  public Iterable<Mandal> getMandalsList() {
    return this.mandalRepository.findAll();
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/api/mandals/{id}")
  public Mandal getMandalById(@PathVariable Long id) {
    return this.mandalRepository.findById(id).orElse(null);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/api/mandals")
  public Mandal createMandal(@RequestBody Mandal Mandal) {
    return this.mandalRepository.save(Mandal);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @DeleteMapping("/api/mandals/{id}")
  public void deleteMandal(@PathVariable Long id) {
    this.mandalRepository.deleteById(id);
  }
}
