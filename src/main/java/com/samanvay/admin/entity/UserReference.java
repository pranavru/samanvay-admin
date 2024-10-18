package com.samanvay.admin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_references")
public class UserReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contactDescription;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "primary_contact_id",  referencedColumnName = "id", nullable = true) 
    private User primaryContact;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "secondary_contact_id",  referencedColumnName = "id", nullable = true) 
    private User secondaryContact;
}
