package com.youcode.petPlanet.dto.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetProductResponse {
    List<PetResponse> petResponses;
    List<ProductResponse> productResponses;
}
