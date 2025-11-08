package com.aiimage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "ImageEvidence", description = "Imagem enviada como evidência (base64) e metadados opcionais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEvidenceDto {

    @Schema(description = "Tipo da imagem", example = "ARRIVAL")
    private String imageType; // ARRIVAL, FACADE, BADGE, ROUTER

    @Schema(description = "Imagem em Base64 (pode ser grande). Preferir upload multipart em versões futuras.", example = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD...")
    private String base64Image;

    @Schema(description = "Metadados EXIF em JSON (opcional)")
    private String metadataJson; // optional metadata extracted client-side
}
