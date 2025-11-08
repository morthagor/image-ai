package com.aiimage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

@Schema(name = "ServiceOrder", description = "Ordem de serviço / protocolo de instalação")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceOrderDto {

    @Schema(description = "Número do protocolo gerado", example = "PROTO-20251107-0001")
    private String protocolNumber;

    @Schema(description = "Nome do cliente", example = "João Silva")
    private String clientName;

    @Schema(description = "Endereço do cliente", example = "Rua Exemplo, 123, São Paulo, SP")
    private String clientAddress;

    @Schema(description = "Tipo de cliente", example = "PESSOA_FISICA")
    private String clientType;

    @Schema(description = "Data/Hora do serviço (ISO-8601)", example = "2025-11-07T20:00:00Z")
    private Instant serviceDate;

    @Schema(description = "Status da ordem", example = "EM_ANDAMENTO")
    private String status;

    @Schema(description = "Técnico responsável", example = "tecnico123")
    private String technician;
}
