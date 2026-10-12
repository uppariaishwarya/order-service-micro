package com.example.orderservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.repository.OrderRepository;

public class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private org.springframework.web.client.RestTemplate restTemplate;

    @InjectMocks
    private OrderService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {

        Order order = new Order();

        order.setProductId(1L);
        order.setQuantity(2);

        when(repository.save(any(Order.class)))
                .thenReturn(order);

        Order result =
                service.createOrder(order);

        assertEquals(
                2,
                result.getQuantity());
    }
    @Test
    void testUpdateOrderStatus() {

        Order order = new Order();

        order.setId(1L);
        order.setOrderStatus("CREATED");

        when(repository.findById(1L))
                .thenReturn(Optional.of(order));

        when(repository.save(any(Order.class)))
                .thenReturn(order);

        Order result =
                service.updateStatus(1L,
                                     "SHIPPED");

        assertEquals(
                "SHIPPED",
                result.getOrderStatus());
    }
    @Test
    void testOrderNotFound() {

        when(repository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(
                OrderNotFoundException.class,
                () -> service.updateStatus(
                        100L,
                        "SHIPPED"));
    }
}