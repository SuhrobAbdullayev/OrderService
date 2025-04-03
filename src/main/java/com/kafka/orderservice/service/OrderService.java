package com.kafka.orderservice.service;

import com.kafka.orderservice.dto.OrderRequestDto;
import com.kafka.orderservice.entity.Order;
import com.kafka.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void create(OrderRequestDto dto){
        Order order = new Order();
        order.setName(dto.name());
        order.setCost(dto.cost());
        orderRepository.save(order);
        String message = "Order created. Name: " + dto.name() + "Cost: " + dto.cost();
        kafkaTemplate.send("notification", message);
    }

    @Cacheable(value = "orders", key = "#id")
    public Order getById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no order with the id"));
        return order;
    }

    @CacheEvict(value = "orders", key = "#id")
    public void delete(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no order with the id"));
        orderRepository.delete(order);
    }

    public List<Order> getAll(){
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }
}
