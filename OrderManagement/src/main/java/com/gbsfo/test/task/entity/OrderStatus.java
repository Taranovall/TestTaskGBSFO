package com.gbsfo.test.task.entity;

import com.gbsfo.test.task.exception.IncorrectOrderStatus;

import java.util.Arrays;

public enum OrderStatus {
    CREATED,
    PROCESSING,
    SHIPPING,
    DELIVERED;

    public static OrderStatus getStatus(String name) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> {
                    String message = String.format("Order status '%s' doesn't exist", name);
                    return new IncorrectOrderStatus(message);
                });
    }
}
