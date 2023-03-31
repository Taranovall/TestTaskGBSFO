package com.gbsfo.test.task.controller;

import com.gbsfo.test.task.payload.request.AddItemRequest;
import com.gbsfo.test.task.payload.request.CreateOrderRequest;
import com.gbsfo.test.task.payload.response.CreateOrderResponse;
import com.gbsfo.test.task.payload.request.OrderStatusRequest;
import com.gbsfo.test.task.payload.response.UpdateStatusResponse;
import com.gbsfo.test.task.entity.Order;
import com.gbsfo.test.task.entity.OrderStatus;
import com.gbsfo.test.task.service.OrderService;
import com.gbsfo.test.task.util.Converter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Converter converter;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Order order = orderService.create(createOrderRequest);
        return ResponseEntity.ok(converter.convertOrderToDto(order));
    }

    @PatchMapping
    public ResponseEntity<Order> addItemToOrder(@RequestBody @Valid AddItemRequest addItemRequest) {
        return ResponseEntity.ok(orderService.addItemToOrder(addItemRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateStatusResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid OrderStatusRequest orderStatusRequest) {
        OrderStatus orderStatus = OrderStatus.getStatus(orderStatusRequest.getStatus());
        return ResponseEntity.ok(orderService.updateStatus(id, orderStatus));
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        orderService.delete(id);
    }
}
