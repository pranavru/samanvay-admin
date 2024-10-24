package com.samanvay.admin.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "name")
    private String name; 

    @Column(name = "email", nullable = false, unique = true) 
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = true)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "mandal_id", nullable = true)
    private Mandal mandal;

    @ManyToOne
    @JoinColumn(name = "user_references_id", referencedColumnName = "id")
    private UserReference referenceContacts;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;
}
