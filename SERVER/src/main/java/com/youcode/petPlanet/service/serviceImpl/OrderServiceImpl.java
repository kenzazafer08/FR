package com.youcode.petPlanet.service.serviceImpl;

import com.youcode.petPlanet.auth.user.User;
import com.youcode.petPlanet.auth.user.UserRepository;
import com.youcode.petPlanet.dto.dtoRequest.OrderLineRequest;
import com.youcode.petPlanet.dto.dtoRequest.OrderRequest;
import com.youcode.petPlanet.dto.dtoResponse.OrderResponse;
import com.youcode.petPlanet.dto.dtoResponse.ProductResponse;
import com.youcode.petPlanet.entity.Client;
import com.youcode.petPlanet.entity.Order;
import com.youcode.petPlanet.entity.OrderLine;
import com.youcode.petPlanet.exception.ResourceNotFoundException;
import com.youcode.petPlanet.repository.ClientRepository;
import com.youcode.petPlanet.repository.OrderLineRepository;
import com.youcode.petPlanet.repository.OrderRepository;
import com.youcode.petPlanet.service.serviceInterface.OrderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductServiceImpl productService;
    private final UserRepository userRepository;

    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, OrderLineRepository orderLineRepository, ProductServiceImpl productService, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<OrderResponse> add(@Valid OrderRequest orderRequest) {

        User client = userRepository.findById(Integer.parseInt(String.valueOf(orderRequest.getClientId())))
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        List<OrderLine> orderLinesList = new ArrayList<>();

        Order orderToSave = new Order(orderRequest.getStatus(), client, orderLinesList);
        orderToSave.setTotal(calculateTotalPrice(orderRequest));

        for (OrderLineRequest orderLineRequest : orderRequest.getOrderLines()) {
            OrderLine orderLine = modelMapper.map(orderLineRequest, OrderLine.class);
            orderLine.setId(null);
            orderLinesList.add(orderLine);
        }

        List<OrderLine> savedOrderLines = orderLineRepository.saveAllAndFlush(orderLinesList);

        orderToSave.setOrderLines(savedOrderLines);
        orderToSave = orderRepository.saveAndFlush(orderToSave);
        //modelMapper.map(orderToSave, OrderResponse.class)
        return Optional.of(
                new OrderResponse(
                )
        );
    }

    @Override
    public Optional<OrderResponse> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return Optional.of(modelMapper.map(order.get(), OrderResponse.class));
        } else {
            throw new ResourceNotFoundException("Order not found with ID: " + id);
        }
    }

    @Override
    public List<OrderResponse> getAll(int page, int size) {
        Page<Order> ordersPage = orderRepository.findAll(PageRequest.of(page, size));
        List<Order> orders = ordersPage.getContent();
        if(orders.isEmpty()){
            orders = orderRepository.findAll();
        }
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderResponse> delete(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.delete(order.get());
            return Optional.of(modelMapper.map(order.get(), OrderResponse.class));
        } else {
            throw new ResourceNotFoundException("Order not found with ID: " + id);
        }
    }

    @Override
    public Optional<OrderResponse> changeStatus(Long id, String newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(newStatus);
            orderRepository.save(order);
            return Optional.of(modelMapper.map(order, OrderResponse.class));
        } else {
            throw new ResourceNotFoundException("Order not found with ID: " + id);
        }
    }

    private BigDecimal calculateTotalPrice(OrderRequest orderRequest) {
        return orderRequest.getOrderLines().stream()
                .map(orderLineRequest -> {
                    ProductResponse product = productService.findById(orderLineRequest.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + orderLineRequest.getProductId()));
                    return product.getPrice().multiply(BigDecimal.valueOf(orderLineRequest.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
