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
        ServiceOrder order = orderRepository.findByProtocolNumber(protocolNumber).orElseThrow();
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
        images.forEach(img -> uploadArrivalImage(protocolNumber, img));
    }
}
