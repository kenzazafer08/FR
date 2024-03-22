package com.youcode.petPlanet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int quantity;
    private String image;
    private BigDecimal price;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<PetProduct> petsProducts;

}
