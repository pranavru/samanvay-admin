package com.samanvay.admin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zones")
public class Zone {
      
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "created_by")
    // private User createdBy;
}
