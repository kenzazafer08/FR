package com.youcode.petPlanet.controller;


import com.youcode.petPlanet.dto.dtoRequest.PetRequest;
import com.youcode.petPlanet.dto.dtoRequest.ProductRequest;
import com.youcode.petPlanet.dto.dtoResponse.PetResponse;
import com.youcode.petPlanet.dto.dtoResponse.ProductResponse;
import com.youcode.petPlanet.service.serviceImpl.ProductServiceImpl;
import com.youcode.petPlanet.service.serviceInterface.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> Add(@RequestBody ProductRequest productRequest){
        Optional<ProductResponse> savedProduct = productService.Add(productRequest);
        return ResponseEntity.ok(savedProduct.get());
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        List<ProductResponse> products = productService.getAll(page,size);
        System.out.println(page+" - "+size+" - "+products);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id){
        Optional<ProductResponse> product = productService.findById(id);
        return ResponseEntity.ok(product.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<ProductResponse>> delete(@PathVariable Long id){
        Optional<ProductResponse> product = productService.delete(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ProductResponse>> update(@PathVariable Long id, @RequestBody ProductRequest product){
        Optional<ProductResponse> updatedProduct = productService.update(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

}
