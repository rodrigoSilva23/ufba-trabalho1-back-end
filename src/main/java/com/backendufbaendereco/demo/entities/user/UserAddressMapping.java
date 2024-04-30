package com.backendufbaendereco.demo.entities.user;

import com.backendufbaendereco.demo.entities.andress.Address;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_address")
public class UserAddressMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address addressId;

    @Column(name = "is_main_address")
    private boolean isMainAddress;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}