package com.gbsfo.test.task.util;

import com.gbsfo.test.task.payload.response.CreateOrderResponse;
import com.gbsfo.test.task.payload.response.ItemResponse;
import com.gbsfo.test.task.entity.Item;
import com.gbsfo.test.task.entity.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Converter {

    public ItemResponse convertItemToDto(Item item) {
        return new ItemResponse(
                item.getUser().getId(),
                item.getId(),
                item.getName(),
                item.getPrice());
    }

    public CreateOrderResponse convertOrderToDto(Order order) {
        return new CreateOrderResponse(
                order.getNumber(),
                order.getStatus(),
                order.getTotalItems(),
                order.getItems()
                        .stream()
                        .map(this::convertItemToDto)
                        .collect(Collectors.toSet())
        );
    }

}
