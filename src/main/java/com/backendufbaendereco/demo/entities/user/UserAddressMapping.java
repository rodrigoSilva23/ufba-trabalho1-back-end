package com.backendufbaendereco.demo.entities.user;

import com.backendufbaendereco.demo.entities.andress.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "user_address")
public class UserAddressMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "address_id" ,nullable = false)
    private Address addressId;

    @Column(name = "is_main_address",nullable = false)
    private boolean isMainAddress;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}