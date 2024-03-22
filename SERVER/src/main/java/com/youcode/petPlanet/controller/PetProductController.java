package com.youcode.petPlanet.controller;

import com.youcode.petPlanet.dto.dtoRequest.PetProductRequest;
import com.youcode.petPlanet.dto.dtoResponse.PetProductResponse;
import com.youcode.petPlanet.service.serviceImpl.PetProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/petProduct")
public class PetProductController {
    private final PetProductServiceImpl petProductService;

    public PetProductController(PetProductServiceImpl productService) {
        this.petProductService = productService;
    }


    @PostMapping("/add")
    public ResponseEntity<PetProductResponse> Add(@RequestBody PetProductRequest petProductRequest){
        Optional<PetProductResponse> savedPetProduct = petProductService.Add(petProductRequest);
        return ResponseEntity.ok(savedPetProduct.get());
    }

    @GetMapping("/list")
    public ResponseEntity<List<PetProductResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        List<PetProductResponse> petProducts = petProductService.getAll(page,size);
        return ResponseEntity.ok(petProducts);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<PetProductResponse>> delete(@PathVariable Long id){
        Optional<PetProductResponse> petProductResponse = petProductService.delete(id);
        return ResponseEntity.ok(petProductResponse);
    }

}
