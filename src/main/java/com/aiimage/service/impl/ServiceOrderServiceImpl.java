package com.aiimage.service.impl;

import com.aiimage.domain.ImageEvidence;
import com.aiimage.domain.ServiceOrder;
import com.aiimage.dto.ImageEvidenceDto;
import com.aiimage.dto.ServiceOrderDto;
import com.aiimage.repository.ImageEvidenceRepository;
import com.aiimage.repository.ServiceOrderRepository;
import com.aiimage.service.ServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceOrderServiceImpl implements ServiceOrderService {

    private final ServiceOrderRepository orderRepository;
    private final ImageEvidenceRepository evidenceRepository;

    @Override
    public ServiceOrderDto createOrder(ServiceOrderDto dto) {
        validateOrderDto(dto);
        
        ServiceOrder order = ServiceOrder.builder()
                .protocolNumber(dto.getProtocolNumber())
                .clientName(dto.getClientName())
                .clientAddress(dto.getClientAddress())
                .clientType(dto.getClientType())
                .serviceDate(dto.getServiceDate())
                .status(dto.getStatus())
                .technician(dto.getTechnician())
                .build();
        order = orderRepository.save(order);
        dto.setProtocolNumber(order.getProtocolNumber());
        return dto;
    }

    private void validateOrderDto(ServiceOrderDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("OrderDto cannot be null");
        }
        if (dto.getClientName() == null || dto.getClientName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name is required");
        }
        if (dto.getClientAddress() == null || dto.getClientAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Client address is required");
        }
        if (dto.getClientType() == null || dto.getClientType().trim().isEmpty()) {
            throw new IllegalArgumentException("Client type is required");
        }
        if (dto.getServiceDate() == null) {
            throw new IllegalArgumentException("Service date is required");
        }
        if (dto.getTechnician() == null || dto.getTechnician().trim().isEmpty()) {
            throw new IllegalArgumentException("Technician is required");
        }
    }

    @Override
    public ServiceOrderDto getOrder(String protocolNumber) {
        return orderRepository.findByProtocolNumber(protocolNumber)
                .map(o -> ServiceOrderDto.builder()
                        .protocolNumber(o.getProtocolNumber())
                        .clientName(o.getClientName())
                        .clientAddress(o.getClientAddress())
                        .clientType(o.getClientType())
                        .serviceDate(o.getServiceDate())
                        .status(o.getStatus())
                        .technician(o.getTechnician())
                        .build())
                .orElse(null);
    }

    @Override
    public void uploadArrivalImage(String protocolNumber, ImageEvidenceDto imageDto) {
        if (protocolNumber == null || protocolNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Protocol number is required");
        }
        if (imageDto == null) {
            throw new IllegalArgumentException("Image data is required");
        }
        if (imageDto.getBase64Image() == null || imageDto.getBase64Image().trim().isEmpty()) {
            throw new IllegalArgumentException("Image content is required");
        }
        if (!"ARRIVAL".equals(imageDto.getImageType())) {
            throw new IllegalArgumentException("Invalid image type for arrival. Must be ARRIVAL");
        }

        ServiceOrder order = orderRepository.findByProtocolNumber(protocolNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + protocolNumber));

        ImageEvidence ev = ImageEvidence.builder()
                .serviceOrder(order)
                .imageType(imageDto.getImageType())
                .metadataJson(imageDto.getMetadataJson())
                .minioPath(null)
                .createdAt(Instant.now())
                .build();
        evidenceRepository.save(ev);
        // TODO: integrar com MinIO e análise LLM de forma assíncrona
    }

    @Override
    public void uploadCompletionImages(String protocolNumber, List<ImageEvidenceDto> images) {
        if (protocolNumber == null || protocolNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Protocol number is required");
        }
        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("At least one completion image is required");
        }
        if (images.size() > 3) {
            throw new IllegalArgumentException("Maximum of 3 completion images allowed");
        }

        ServiceOrder order = orderRepository.findByProtocolNumber(protocolNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + protocolNumber));

        List<String> validTypes = List.of("FACADE", "BADGE", "ROUTER");
        
        for (ImageEvidenceDto img : images) {
            if (img == null) {
                throw new IllegalArgumentException("Image data cannot be null");
            }
            if (img.getBase64Image() == null || img.getBase64Image().trim().isEmpty()) {
                throw new IllegalArgumentException("Image content is required");
            }
            if (!validTypes.contains(img.getImageType())) {
                throw new IllegalArgumentException("Invalid image type. Must be one of: " + validTypes);
            }

            ImageEvidence ev = ImageEvidence.builder()
                    .serviceOrder(order)
                    .imageType(img.getImageType())
                    .metadataJson(img.getMetadataJson())
                    .minioPath(null)
                    .createdAt(Instant.now())
                    .build();
            evidenceRepository.save(ev);
        }
    }
}
