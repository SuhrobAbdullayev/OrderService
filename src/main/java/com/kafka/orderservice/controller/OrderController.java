package com.kafka.orderservice.controller;

import com.kafka.orderservice.dto.OrderRequestDto;
import com.kafka.orderservice.entity.Order;
import com.kafka.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> create(OrderRequestDto dto){
        orderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order is created");
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAll(){
        List<Order> orderList = orderService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }
}
