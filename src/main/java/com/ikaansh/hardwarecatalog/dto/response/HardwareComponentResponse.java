package com.ikaansh.hardwarecatalog.dto.response;

import com.ikaansh.hardwarecatalog.entity.HardwareComponent;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HardwareComponentResponse {

    private Long id;
    private String name;
    private String category;
    private String specifications;
    private BigDecimal price;
    private Integer stockQuantity;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static HardwareComponentResponse fromEntity(HardwareComponent entity) {
        return HardwareComponentResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(entity.getCategory())
                .specifications(entity.getSpecifications())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}