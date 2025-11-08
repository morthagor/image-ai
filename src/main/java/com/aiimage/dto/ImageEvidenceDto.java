package com.aiimage.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEvidenceDto {
    private String imageType; // ARRIVAL, FACADE, BADGE, ROUTER
    private String base64Image;
    private String metadataJson; // optional metadata extracted client-side
}
