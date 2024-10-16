package com.samanvay.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.samanvay.admin.repository.MandalRepository;

import com.samanvay.admin.entity.Mandal;
import com.samanvay.admin.entity.Message;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public Message createMandal(@RequestBody Iterable<Mandal> mandals) {
    for (Mandal m : mandals) {
      
      Mandal newMandal = Mandal.builder()
        .name(m.getName())
        .location(m.getLocation())
        .zone(m.getZone())
        .build();

      this.mandalRepository.save(newMandal);
    }

    return new Message("Mandals created successfully", "success");
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @DeleteMapping("/api/mandals/{id}")
  public Message deleteMandal(@PathVariable Long id) {
    this.mandalRepository.deleteById(id);

    return new Message("Mandal deleted successfully", "success");
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PutMapping("/api/mandals")
  public Message updateMandal(@RequestBody Iterable<Mandal> mandals) {
    for (Mandal m : mandals) {
      if(this.mandalRepository.existsById(m.getId())) {
        Mandal updatedMandal = this.mandalRepository.findById(m.getId()).get();

        updatedMandal.setName(m.getName());
        updatedMandal.setLocation(m.getLocation());
        updatedMandal.setZone(m.getZone());

        this.mandalRepository.save(updatedMandal);
      }
    }
    
    return new Message("Mandal updated successfully", "success");
  }
}
