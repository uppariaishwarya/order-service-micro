package com.example.orderservice.service;

import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.ProductDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository repository,
                        RestTemplate restTemplate) {

        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Order createOrder(Order order) {

        order.setOrderStatus("CREATED");

        return repository.save(order);
    }

    public Order updateStatus(Long id,
                              String status) {

        Order order = repository.findById(id)
                .orElseThrow(() ->
                new OrderNotFoundException(
                        "Order not found with id : " + id));


        order.setOrderStatus(status);

        return repository.save(order);
    }

    public OrderResponse getOrder(Long id) {

        Order order = repository.findById(id)
                .orElseThrow(() ->
                new OrderNotFoundException(
                        "Order not found with id : " + id));


        ProductDTO product =
                restTemplate.getForObject(
                        "http://localhost:8081/products/"
                                + order.getProductId(),
                        ProductDTO.class);

        OrderResponse response =
                new OrderResponse();

        response.setOrder(order);
        response.setProduct(product);

        return response;
    }
}