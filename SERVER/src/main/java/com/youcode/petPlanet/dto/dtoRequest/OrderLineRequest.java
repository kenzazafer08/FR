package com.youcode.petPlanet.dto.dtoRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineRequest {

    @Min(0)
    private Long quantity;
    @Min(0)
    private BigDecimal price;
    @NotNull
    private Long productId;
}