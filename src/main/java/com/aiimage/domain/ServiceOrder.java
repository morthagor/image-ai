package com.aiimage.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "service_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "protocol_number", unique = true, nullable = false)
    private String protocolNumber;

    private String clientName;

    private String clientAddress;

    private String clientType; // PESSOA_FISICA, PESSOA_JURIDICA

    private Instant serviceDate;

    private String status;

    private String technician;

}
