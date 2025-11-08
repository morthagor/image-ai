package com.aiimage.dto;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceOrderDto {
    private String protocolNumber;
    private String clientName;
    private String clientAddress;
    private String clientType;
    private Instant serviceDate;
    private String status;
    private String technician;
}
