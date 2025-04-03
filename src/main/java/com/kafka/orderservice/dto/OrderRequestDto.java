package com.kafka.orderservice.dto;

public record OrderRequestDto(
        String name,
        Double cost
) {
}
