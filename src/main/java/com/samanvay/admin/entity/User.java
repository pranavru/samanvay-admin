package com.samanvay.admin.entity;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = true)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "mandal_id", nullable = true)
    private Mandal mandal;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reference_contact_id", referencedColumnName = "id", nullable = true)
    private ReferenceContact referenceContact;
}
