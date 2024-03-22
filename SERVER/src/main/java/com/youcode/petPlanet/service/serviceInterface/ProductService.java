package com.youcode.petPlanet.service.serviceInterface;

import com.youcode.petPlanet.dto.dtoRequest.PetRequest;
import com.youcode.petPlanet.dto.dtoRequest.ProductRequest;
import com.youcode.petPlanet.dto.dtoResponse.PetResponse;
import com.youcode.petPlanet.dto.dtoResponse.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductResponse> Add(ProductRequest fish);
    Optional<ProductResponse> findById(Long id);
    List<ProductResponse> getAll(int page, int size);
    Optional<ProductResponse> update(Long id, ProductRequest fish);
    Optional<ProductResponse> delete(Long id);
}
