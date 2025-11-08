package com.aiimage.service;

import com.aiimage.dto.ImageEvidenceDto;
import com.aiimage.dto.ServiceOrderDto;

import java.util.List;

public interface ServiceOrderService {
    ServiceOrderDto createOrder(ServiceOrderDto dto);

    ServiceOrderDto getOrder(String protocolNumber);

    void uploadArrivalImage(String protocolNumber, ImageEvidenceDto imageDto);

    void uploadCompletionImages(String protocolNumber, List<ImageEvidenceDto> images);
}
