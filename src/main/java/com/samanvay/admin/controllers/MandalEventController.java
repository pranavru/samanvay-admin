package com.samanvay.admin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.samanvay.admin.entity.MandalEvent;
import com.samanvay.admin.entity.Message;
import com.samanvay.admin.entity.DTO.MandalEventDTO;
import com.samanvay.admin.repository.MandalEventRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class MandalEventController {

  @Autowired
  private final MandalEventRepository eventRepository;

  public MandalEventController(MandalEventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/api/events")
  public List<MandalEvent> getEventsList() {
    return this.eventRepository.findAll();
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/api/events/{id}")
  public MandalEvent getEventById(@PathVariable Long id) {
    return this.eventRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/api/event")
  public MandalEvent createEvent(@RequestBody MandalEventDTO event) {
    MandalEvent mandalEvent = MandalEvent.builder()
      .name(event.getName())
      .description(event.getDescription())
      .location(event.getLocation())
      .startDate(event.getStartDate())
      .endDate(event.getEndDate())
      .status(event.getStatus())
      .build();

    return this.eventRepository.save(mandalEvent);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PutMapping("/api/event/{id}")
  public Message updateEvent(@PathVariable Long id, @RequestBody MandalEventDTO event) {
    MandalEvent mandalEvent = this.eventRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

    mandalEvent.setName(event.getName());
    mandalEvent.setDescription(event.getDescription());
    mandalEvent.setLocation(event.getLocation());
    mandalEvent.setStartDate(event.getStartDate());
    mandalEvent.setEndDate(event.getEndDate());
    mandalEvent.setStatus(event.getStatus());

    this.eventRepository.save(mandalEvent);

    return new Message("Event with name: " + event.getName() + " updated successfully", "success");
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @DeleteMapping("/api/event/{id}")
  public Message deleteEvent(@PathVariable Long id) {
    MandalEvent mandalEvent = this.eventRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

      this.eventRepository.delete(mandalEvent);

    return new Message("Event with name: " + mandalEvent.getName() + " deleted successfully", "success");
  }
}
