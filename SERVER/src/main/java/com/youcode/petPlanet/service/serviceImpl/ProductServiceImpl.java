package com.youcode.petPlanet.service.serviceImpl;

import com.youcode.petPlanet.dto.dtoRequest.ProductRequest;
import com.youcode.petPlanet.dto.dtoResponse.PetResponse;
import com.youcode.petPlanet.dto.dtoResponse.ProductResponse;
import com.youcode.petPlanet.entity.Pet;
import com.youcode.petPlanet.entity.Product;
import com.youcode.petPlanet.exception.ResourceNotFoundException;
import com.youcode.petPlanet.repository.ProductRepository;
import com.youcode.petPlanet.service.serviceInterface.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductResponse> Add(ProductRequest product) {
        Product productToSave = modelMapper.map(product , Product.class);
        productRepository.save(productToSave);
        return Optional.of(modelMapper.map(product, ProductResponse.class));
    }


    @Override
    public Optional<ProductResponse> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return Optional.of(modelMapper.map(product,ProductResponse.class));
        }else{
            throw new ResourceNotFoundException("Pet not found with id : " + id);
        }
    }

    @Override
    public List<ProductResponse> getAll(int page, int size) {
        Page<Product> productsPage = productRepository.findAll(PageRequest.of(page, size));
        List<Product> products = productsPage.getContent();
        if(products.isEmpty()){
            products = productRepository.findAll();
        }
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }


    @Override
    public Optional<ProductResponse> update(Long id, ProductRequest product) {
        Optional<Product> productToUpdate = productRepository.findById(id);
        if(productToUpdate.isPresent()){
            productToUpdate.get().setName(product.getName());
            productToUpdate.get().setDescription(product.getDescription());
            productToUpdate.get().setImage(product.getImage());
            productRepository.save(productToUpdate.get());
            return Optional.ofNullable(modelMapper.map(productToUpdate, ProductResponse.class));
        }else{
            throw new ResourceNotFoundException("Product not found with name : " + id);
        }
    }

    @Override
    public Optional<ProductResponse> delete(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            productRepository.delete(product.get());
            return Optional.of(modelMapper.map(product, ProductResponse.class));
        }else{
            throw new ResourceNotFoundException("Product not found with name : " + id);
        }
    }
}
