package com.aiimage.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "image_evidences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEvidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_order_id")
    private ServiceOrder serviceOrder;

    private String imageType; // ARRIVAL, FACADE, BADGE, ROUTER

    @Column(columnDefinition = "text")
    private String metadataJson;

    private String minioPath;

    private Instant createdAt;

}
