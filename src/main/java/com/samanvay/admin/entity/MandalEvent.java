package com.samanvay.admin.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class MandalEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    private String status;

    @PrePersist
    protected void onCreate() {
      this.createdDate = LocalDateTime.now();
    }
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @PreUpdate
    protected void onUpdate() {
      this.updatedDate = LocalDateTime.now();
    }
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
