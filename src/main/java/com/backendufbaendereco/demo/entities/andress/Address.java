package com.backendufbaendereco.demo.entities.andress;

import com.backendufbaendereco.demo.entities.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "andresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8,nullable = false)
    private String postalCode;
    private String street;
    private String location;
    private String locationType;
    private String neighborhood;
    private int streetType;
    private String number;
    private String block;
    private String lot;
    private String complement;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City cityId;



    @ManyToOne
    @JoinColumn(name = "state_id",nullable = false)
    private State stateId;

    @ManyToMany(mappedBy = "addresses")
    private Set<User> users = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Getters and setters
}