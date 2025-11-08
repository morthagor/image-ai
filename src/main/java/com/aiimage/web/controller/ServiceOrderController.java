package com.aiimage.web.controller;

import com.aiimage.dto.ImageEvidenceDto;
import com.aiimage.dto.ServiceOrderDto;
import com.aiimage.service.ServiceOrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-orders")
@RequiredArgsConstructor
public class ServiceOrderController {

    private final ServiceOrderService service;

    @PostMapping
    @Operation(summary = "Criar ordem de serviço e gerar protocolo")
    public ResponseEntity<ServiceOrderDto> createOrder(@RequestBody ServiceOrderDto dto) {
        ServiceOrderDto created = service.createOrder(dto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/{protocol}/arrival-image")
    @Operation(summary = "Upload da imagem de chegada (arrival)")
    public ResponseEntity<Void> uploadArrival(@PathVariable("protocol") String protocol,
                                              @RequestBody ImageEvidenceDto image) {
        service.uploadArrivalImage(protocol, image);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{protocol}/completion-images")
    @Operation(summary = "Upload das imagens de conclusão (fachada, crachá, roteador)")
    public ResponseEntity<Void> uploadCompletion(@PathVariable("protocol") String protocol,
                                                 @RequestBody List<ImageEvidenceDto> images) {
        service.uploadCompletionImages(protocol, images);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{protocol}")
    @Operation(summary = "Consultar ordem de serviço por protocolo")
    public ResponseEntity<ServiceOrderDto> getOrder(@PathVariable("protocol") String protocol) {
        ServiceOrderDto dto = service.getOrder(protocol);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{protocol}/evidences")
    @Operation(summary = "Listar evidências de uma ordem de serviço")
    public ResponseEntity<List<?>> getEvidences(@PathVariable("protocol") String protocol) {
        // TODO: retornar DTOs de ImageEvidence
        return ResponseEntity.ok(List.of());
    }

}
