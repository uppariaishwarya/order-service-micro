package com.example.orderservice.dto;

import com.example.orderservice.entity.Order;

public class OrderResponse {

    private Order order;

    private ProductDTO product;

    public OrderResponse() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
