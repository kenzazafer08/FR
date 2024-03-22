package com.youcode.petPlanet.dto.dtoResponse;

import com.youcode.petPlanet.entity.Order;
import com.youcode.petPlanet.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private int quantity;
    private BigDecimal price;
    private String status;
    private Order order;
    private Product product;
    private BigDecimal total;
}
