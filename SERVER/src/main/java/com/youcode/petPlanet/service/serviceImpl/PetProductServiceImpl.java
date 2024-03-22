package com.youcode.petPlanet.service.serviceImpl;

import com.youcode.petPlanet.dto.dtoRequest.PetProductRequest;
import com.youcode.petPlanet.dto.dtoResponse.PetProductResponse;
import com.youcode.petPlanet.entity.PetProduct;
import com.youcode.petPlanet.exception.ResourceNotFoundException;
import com.youcode.petPlanet.repository.PetProductRepository;
import com.youcode.petPlanet.service.serviceInterface.PetProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetProductServiceImpl implements PetProductService {

    private final ModelMapper modelMapper;
    private final PetProductRepository petProductRepository;

    public PetProductServiceImpl(ModelMapper modelMapper, PetProductRepository petProductRepository) {
        this.modelMapper = modelMapper;
        this.petProductRepository = petProductRepository;
    }

    @Override
    public Optional<PetProductResponse> Add(PetProductRequest petProductRequest) {
        PetProduct petProductToSave = modelMapper.map(petProductRequest , PetProduct.class);
        petProductRepository.save(petProductToSave);
        return Optional.of(modelMapper.map(petProductRequest, PetProductResponse.class));
    }

    @Override
    public List<PetProductResponse> getAll(int page, int size) {
        Page<PetProduct> petProductsPage = petProductRepository.findAll(PageRequest.of(page, size));
        List<PetProduct> petProducts = petProductsPage.getContent();
        if(petProducts.isEmpty()){
            petProducts = petProductRepository.findAll();
        }
        return petProducts.stream()
                .map(petProduct -> modelMapper.map(petProduct, PetProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PetProductResponse> delete(Long id) {
        Optional<PetProduct> petProduct = petProductRepository.findById(id);
        if(petProduct.isPresent()){
            petProductRepository.delete(petProduct.get());
            return Optional.of(modelMapper.map(petProduct, PetProductResponse.class));
        }else{
            throw new ResourceNotFoundException("Pet Product not found with name : " + id);
        }
    }
}
