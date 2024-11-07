package com.samanvay.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.samanvay.admin.repository.MandalRepository;
import com.samanvay.admin.repository.ZoneRepository;
import com.samanvay.admin.entity.Mandal;
import com.samanvay.admin.entity.Message;
import com.samanvay.admin.entity.Zone;
import com.samanvay.admin.entity.DTO.MandalDTO;

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

  @Autowired
  private ZoneRepository zoneRepository;

  public MandalController(MandalRepository MandalRepository) {
    this.mandalRepository = MandalRepository;
  }

  @GetMapping("/api/mandals")
  public Iterable<Mandal> getMandalsList() {
    return this.mandalRepository.findAll();
  }

  @GetMapping("/api/mandals/{id}")
  public Mandal getMandalById(@PathVariable Long id) {
    return this.mandalRepository.findById(id).orElse(null);
  }

  @PostMapping("/api/mandals")
  public Message createMandal(@RequestBody Iterable<MandalDTO> mandals) {
    for (MandalDTO m : mandals) {
      
      Mandal newMandal = Mandal.builder()
        .name(m.getName())
        .location(m.getLocation())
        .zone(zoneRepository.findById(m.getZoneId()).get())
        .build();

      this.mandalRepository.save(newMandal);
    }

    return new Message("Mandals created successfully", "success");
  }

  @DeleteMapping("/api/mandals/{id}")
  public Message deleteMandal(@PathVariable Long id) {
    this.mandalRepository.deleteById(id);

    return new Message("Mandal deleted successfully", "success");
  }

  @PutMapping("/api/mandals")
  public Message updateMandal(@RequestBody Iterable<MandalDTO> mandals) {
    for (MandalDTO m : mandals) {
      if(this.mandalRepository.existsById(m.getId())) {
        Mandal updatedMandal = this.mandalRepository.findById(m.getId()).get();

        updatedMandal.setName(m.getName());
        updatedMandal.setLocation(m.getLocation());

        Zone zone = this.zoneRepository.findById(m.getZoneId()).orElseThrow(() -> new RuntimeException("Zone with id: " + m.getZoneId() + " was not found"));

        updatedMandal.setZone(zone);

        this.mandalRepository.save(updatedMandal);
      }
    }
    
    return new Message("Mandal updated successfully", "success");
  }
}
