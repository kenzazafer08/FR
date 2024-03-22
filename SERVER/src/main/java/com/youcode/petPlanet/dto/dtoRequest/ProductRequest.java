package com.youcode.petPlanet.dto.dtoRequest;

import com.youcode.petPlanet.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "name must not be empty")
    private String name;
    @NotBlank(message = "description must not be empty")
    private String description;
    @Min(value = 1, message = "quantity must be more than 0")
    private Long quantity;
    @NotNull
    private String image;
    @Min(0)
    private BigDecimal price;
    @NotNull
    private Long categoryId;
}
