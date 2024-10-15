package com.samanvay.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.samanvay.admin.repository.ZoneRepository;

import com.samanvay.admin.entity.Zone;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ZoneController {
  @Autowired
  private final ZoneRepository zoneRepository;

  public ZoneController(ZoneRepository zoneRepository) {
    this.zoneRepository = zoneRepository;
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/api/zones")
  public Iterable<Zone> getZonesList() {
    return this.zoneRepository.findAll();
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/api/zones/{id}")
  public Zone getZoneById(@PathVariable Long id) {
    return this.zoneRepository.findById(id).orElse(null);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/api/zones")
  public Zone createZone(@RequestBody Zone zone) {
    return this.zoneRepository.save(zone);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @DeleteMapping("/api/zones/{id}")
  public void deleteZone(@PathVariable Long id) {
    this.zoneRepository.deleteById(id);
  }
}
