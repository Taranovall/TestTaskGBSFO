package com.gbsfo.test.task.payload.response;

import com.gbsfo.test.task.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CreateOrderResponse {

    private String number;
    private OrderStatus status;
    private Integer totalItems;
    private Set<ItemResponse> items;
}
