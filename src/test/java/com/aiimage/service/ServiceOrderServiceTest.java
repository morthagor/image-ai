package com.aiimage.service;

import com.aiimage.domain.ImageEvidence;
import com.aiimage.domain.ServiceOrder;
import com.aiimage.dto.ImageEvidenceDto;
import com.aiimage.dto.ServiceOrderDto;
import com.aiimage.repository.ImageEvidenceRepository;
import com.aiimage.repository.ServiceOrderRepository;
import com.aiimage.service.impl.ServiceOrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceOrderServiceTest {

    @Mock
    private ServiceOrderRepository orderRepository;

    @Mock
    private ImageEvidenceRepository evidenceRepository;

    @InjectMocks
    private ServiceOrderServiceImpl serviceOrderService;

    @Captor
    private ArgumentCaptor<ServiceOrder> orderCaptor;

    @Captor
    private ArgumentCaptor<ImageEvidence> evidenceCaptor;

    private ServiceOrderDto createValidOrderDto() {
        return ServiceOrderDto.builder()
                .protocolNumber("OS123456")
                .clientName("João Silva")
                .clientAddress("Rua das Flores, 123")
                .clientType("RESIDENTIAL")
                .serviceDate(Instant.now())
                .status("PENDING")
                .technician("TECH001")
                .build();
    }

    @Nested
    @DisplayName("Testes de Criação de Ordem de Serviço")
    class CreateOrderTests {
        
        @Test
        @DisplayName("Deve criar ordem de serviço com sucesso")
        void shouldCreateOrderSuccessfully() {
            // Given
            ServiceOrderDto dto = createValidOrderDto();
            ServiceOrder savedOrder = ServiceOrder.builder()
                    .protocolNumber("OS123456")
                    .build();
            when(orderRepository.save(any())).thenReturn(savedOrder);

            // When
            ServiceOrderDto result = serviceOrderService.createOrder(dto);

            // Then
            verify(orderRepository).save(orderCaptor.capture());
            ServiceOrder capturedOrder = orderCaptor.getValue();
            
            assertThat(result).isNotNull();
            assertThat(result.getProtocolNumber()).isEqualTo("OS123456");
            assertThat(capturedOrder.getClientName()).isEqualTo(dto.getClientName());
            assertThat(capturedOrder.getClientAddress()).isEqualTo(dto.getClientAddress());
        }

        @Test
        @DisplayName("Deve validar DTO nulo ao criar ordem")
        void shouldValidateNullDto() {
            // When/Then
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.createOrder(null));
            verify(orderRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve validar nome do cliente ao criar ordem")
        void shouldValidateClientName() {
            // Given
            ServiceOrderDto dto = createValidOrderDto();
            dto.setClientName(null);

            // When/Then
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.createOrder(dto));
            assertThat(ex.getMessage()).isEqualTo("Client name is required");
            verify(orderRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve validar endereço do cliente ao criar ordem")
        void shouldValidateClientAddress() {
            // Given
            ServiceOrderDto dto = createValidOrderDto();
            dto.setClientAddress("");

            // When/Then
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.createOrder(dto));
            assertThat(ex.getMessage()).isEqualTo("Client address is required");
            verify(orderRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve validar tipo de cliente ao criar ordem")
        void shouldValidateClientType() {
            // Given
            ServiceOrderDto dto = createValidOrderDto();
            dto.setClientType("   ");

            // When/Then
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.createOrder(dto));
            assertThat(ex.getMessage()).isEqualTo("Client type is required");
            verify(orderRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve validar data do serviço ao criar ordem")
        void shouldValidateServiceDate() {
            // Given
            ServiceOrderDto dto = createValidOrderDto();
            dto.setServiceDate(null);

            // When/Then
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.createOrder(dto));
            assertThat(ex.getMessage()).isEqualTo("Service date is required");
            verify(orderRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve validar técnico ao criar ordem")
        void shouldValidateTechnician() {
            // Given
            ServiceOrderDto dto = createValidOrderDto();
            dto.setTechnician("  ");

            // When/Then
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.createOrder(dto));
            assertThat(ex.getMessage()).isEqualTo("Technician is required");
            verify(orderRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Testes de Consulta de Ordem de Serviço")
    class GetOrderTests {

        @Test
        @DisplayName("Deve retornar ordem de serviço existente com sucesso")
        void shouldReturnExistingOrderSuccessfully() {
            // Given
            ServiceOrder order = ServiceOrder.builder()
                    .protocolNumber("OS123456")
                    .clientName("João Silva")
                    .clientAddress("Rua das Flores, 123")
                    .clientType("RESIDENTIAL")
                    .serviceDate(Instant.now())
                    .status("PENDING")
                    .technician("TECH001")
                    .build();
            when(orderRepository.findByProtocolNumber("OS123456")).thenReturn(Optional.of(order));

            // When
            ServiceOrderDto result = serviceOrderService.getOrder("OS123456");

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getProtocolNumber()).isEqualTo(order.getProtocolNumber());
            assertThat(result.getClientName()).isEqualTo(order.getClientName());
            assertThat(result.getClientAddress()).isEqualTo(order.getClientAddress());
            assertThat(result.getClientType()).isEqualTo(order.getClientType());
            assertThat(result.getServiceDate()).isEqualTo(order.getServiceDate());
            assertThat(result.getStatus()).isEqualTo(order.getStatus());
            assertThat(result.getTechnician()).isEqualTo(order.getTechnician());
        }

        @Test 
        @DisplayName("Deve retornar nulo quando ordem não existe")
        void shouldReturnNullWhenOrderNotFound() {
            // Given
            when(orderRepository.findByProtocolNumber("INVALID")).thenReturn(Optional.empty());

            // When
            ServiceOrderDto result = serviceOrderService.getOrder("INVALID");

            // Then
            assertThat(result).isNull();
        }
    }

    @Nested
    @DisplayName("Testes de Upload de Imagens")
    class ImageUploadTests {

        private ImageEvidenceDto createValidImageDto(String type) {
            return ImageEvidenceDto.builder()
                    .imageType(type)
                    .base64Image("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD...")
                    .metadataJson("{\"location\": \"123,456\", \"timestamp\": \"2025-11-09T10:00:00Z\"}")
                    .build();
        }
        
        private ImageEvidenceDto createValidImageDto() {
            return createValidImageDto("ARRIVAL");
        }

        @Test
        @DisplayName("Deve validar protocolo nulo ou vazio")
        void shouldValidateNullOrEmptyProtocol() {
            // Given
            ImageEvidenceDto validImage = createValidImageDto();

            // When/Then
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.uploadArrivalImage(null, validImage));
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.uploadArrivalImage("", validImage));
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.uploadArrivalImage("  ", validImage));

            verify(orderRepository, never()).findByProtocolNumber(any());
            verify(evidenceRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve validar DTO de imagem nulo")
        void shouldValidateNullImageDto() {
            // When/Then
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.uploadArrivalImage("OS123", null));
            verify(orderRepository, never()).findByProtocolNumber(any());
            verify(evidenceRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve fazer upload de imagem de chegada com sucesso")
        void shouldUploadArrivalImageSuccessfully() {
            // Given
            String protocolNumber = "OS123456";
            ServiceOrder existingOrder = new ServiceOrder();
            existingOrder.setProtocolNumber(protocolNumber);
            when(orderRepository.findByProtocolNumber(protocolNumber))
                    .thenReturn(Optional.of(existingOrder));

            ImageEvidenceDto imageDto = createValidImageDto();

            // When
            serviceOrderService.uploadArrivalImage(protocolNumber, imageDto);

            // Then
            verify(evidenceRepository).save(evidenceCaptor.capture());
            ImageEvidence savedEvidence = evidenceCaptor.getValue();
            
            assertThat(savedEvidence.getServiceOrder()).isEqualTo(existingOrder);
            assertThat(savedEvidence.getImageType()).isEqualTo(imageDto.getImageType());
            assertThat(savedEvidence.getMetadataJson()).isEqualTo(imageDto.getMetadataJson());
            assertThat(savedEvidence.getCreatedAt()).isNotNull();
            assertThat(savedEvidence.getMinioPath()).isNull(); // será preenchido após upload assíncrono
        }

        @Test
        @DisplayName("Deve fazer upload com metadados nulos")
        void shouldUploadWithNullMetadata() {
            // Given
            String protocolNumber = "OS123456";
            ServiceOrder existingOrder = new ServiceOrder();
            existingOrder.setProtocolNumber(protocolNumber);
            when(orderRepository.findByProtocolNumber(protocolNumber))
                    .thenReturn(Optional.of(existingOrder));

            ImageEvidenceDto imageDto = createValidImageDto();
            imageDto.setMetadataJson(null);

            // When
            serviceOrderService.uploadArrivalImage(protocolNumber, imageDto);

            // Then
            verify(evidenceRepository).save(evidenceCaptor.capture());
            ImageEvidence savedEvidence = evidenceCaptor.getValue();
            
            assertThat(savedEvidence.getMetadataJson()).isNull();
            assertThat(savedEvidence.getCreatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Deve fazer upload de múltiplas imagens de conclusão")
        void shouldUploadMultipleCompletionImages() {
            // Given
            String protocolNumber = "OS123456";
            ServiceOrder existingOrder = new ServiceOrder();
            existingOrder.setProtocolNumber(protocolNumber);
            when(orderRepository.findByProtocolNumber(protocolNumber))
                    .thenReturn(Optional.of(existingOrder));

            List<ImageEvidenceDto> images = Arrays.asList(
                    createValidImageDto("FACADE"),
                    createValidImageDto("BADGE"),
                    createValidImageDto("ROUTER")
            );

            // When
            serviceOrderService.uploadCompletionImages(protocolNumber, images);

            // Then
            verify(evidenceRepository, times(3)).save(evidenceCaptor.capture());
            List<ImageEvidence> savedImages = evidenceCaptor.getAllValues();
            
            assertThat(savedImages).hasSize(3);
            assertThat(savedImages.get(0).getImageType()).isEqualTo("FACADE");
            assertThat(savedImages.get(1).getImageType()).isEqualTo("BADGE");
            assertThat(savedImages.get(2).getImageType()).isEqualTo("ROUTER");
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar fazer upload de mais de 3 imagens de conclusão")
        void shouldThrowExceptionWhenUploadingMoreThanThreeCompletionImages() {
            // Given
            String protocolNumber = "OS123456";
            List<ImageEvidenceDto> images = Arrays.asList(
                    createValidImageDto("FACADE"),
                    createValidImageDto("BADGE"),
                    createValidImageDto("ROUTER"),
                    createValidImageDto("ROUTER") // quarta imagem
            );

            // When/Then
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.uploadCompletionImages(protocolNumber, images));
            verify(evidenceRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve validar tipo de imagem para upload de conclusão")
        void shouldValidateCompletionImageType() {
            // Given
            String protocolNumber = "OS123456";
            List<ImageEvidenceDto> images = Arrays.asList(
                    createValidImageDto("ARRIVAL"), // tipo inválido para conclusão
                    createValidImageDto("BADGE"),
                    createValidImageDto("ROUTER")
            );

            // When/Then
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.uploadCompletionImages(protocolNumber, images));
            verify(evidenceRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve validar conteúdo da imagem ao fazer upload")
        void shouldValidateImageContent() {
            // Given
            String protocolNumber = "OS123456";
            ImageEvidenceDto invalidImage = createValidImageDto();
            invalidImage.setBase64Image(null);

            // When/Then
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.uploadArrivalImage(protocolNumber, invalidImage));
            verify(evidenceRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar fazer upload para OS inexistente")
        void shouldThrowExceptionWhenOrderNotFound() {
            // Given
            String protocolNumber = "INVALID";
            when(orderRepository.findByProtocolNumber(protocolNumber))
                    .thenReturn(Optional.empty());

            // When/Then
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.uploadArrivalImage(protocolNumber, createValidImageDto()));
            verify(evidenceRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve validar tipo de imagem ao fazer upload de chegada")
        void shouldValidateArrivalImageType() {
            // Given
            String protocolNumber = "OS123456";
            ImageEvidenceDto invalidImage = createValidImageDto("FACADE"); // tipo incorreto

            // When/Then
            assertThrows(IllegalArgumentException.class,
                    () -> serviceOrderService.uploadArrivalImage(protocolNumber, invalidImage));
            verify(evidenceRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Testes de Consulta de Ordem de Serviço")
    class GetOrderTests {

        @Test
        @DisplayName("Deve retornar ordem de serviço quando encontrada")
        void shouldReturnOrderWhenFound() {
            // Given
            String protocolNumber = "OS123456";
            ServiceOrder existingOrder = ServiceOrder.builder()
                    .protocolNumber(protocolNumber)
                    .clientName("João Silva")
                    .clientAddress("Rua das Flores, 123")
                    .clientType("RESIDENTIAL")
                    .build();

            when(orderRepository.findByProtocolNumber(protocolNumber))
                    .thenReturn(Optional.of(existingOrder));

            // When
            ServiceOrderDto result = serviceOrderService.getOrder(protocolNumber);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getProtocolNumber()).isEqualTo(protocolNumber);
            assertThat(result.getClientName()).isEqualTo(existingOrder.getClientName());
            assertThat(result.getClientAddress()).isEqualTo(existingOrder.getClientAddress());
        }

        @Test
        @DisplayName("Deve retornar null quando ordem não encontrada")
        void shouldReturnNullWhenOrderNotFound() {
            // Given
            String protocolNumber = "INVALID";
            when(orderRepository.findByProtocolNumber(protocolNumber))
                    .thenReturn(Optional.empty());

            // When
            ServiceOrderDto result = serviceOrderService.getOrder(protocolNumber);

            // Then
            assertThat(result).isNull();
        }
    }
}