package com.gbsfo.test.task.service;

import com.gbsfo.test.task.payload.request.AddItemRequest;
import com.gbsfo.test.task.payload.request.CreateOrderRequest;
import com.gbsfo.test.task.payload.response.UpdateStatusResponse;
import com.gbsfo.test.task.entity.Order;
import com.gbsfo.test.task.entity.OrderStatus;

public interface OrderService extends DefaultService<Order> {

    UpdateStatusResponse updateStatus(Long id, OrderStatus newStatus);

    Order addItemToOrder(AddItemRequest addItemRequest);

    Order create(CreateOrderRequest createOrderRequest);
}
