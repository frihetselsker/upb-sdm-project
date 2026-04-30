package com.example.publictransport.domain;

import jakarta.persistence.*;
import lombok.Data;
import com.example.publictransport.enums.ValidatorStatus;

@Data
@Entity
@Table(name = "validators")
public class Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "validator_id", unique = true, nullable = false)
    private String validatorId;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValidatorStatus status;
}
