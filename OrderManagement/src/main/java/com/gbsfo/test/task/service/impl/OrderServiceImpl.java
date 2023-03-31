package com.gbsfo.test.task.service.impl;

import com.gbsfo.test.task.entity.Item;
import com.gbsfo.test.task.entity.Order;
import com.gbsfo.test.task.entity.OrderStatus;
import com.gbsfo.test.task.entity.Role;
import com.gbsfo.test.task.entity.User;
import com.gbsfo.test.task.payload.request.AddItemRequest;
import com.gbsfo.test.task.payload.request.CreateOrderRequest;
import com.gbsfo.test.task.payload.response.UpdateStatusResponse;
import com.gbsfo.test.task.repository.OrderRepository;
import com.gbsfo.test.task.service.ItemService;
import com.gbsfo.test.task.service.OrderService;
import com.gbsfo.test.task.util.EntityRetriever;
import com.gbsfo.test.task.util.UserUtil;
import com.gbsfo.test.task.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.gbsfo.test.task.util.Constant.NUMBER_PATTERN;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final EntityRetriever entityRetriever;
    private final ValidationUtil validationUtil;

    @Override
    public Order create(CreateOrderRequest createOrderRequest) {
        Set<Item> items = itemService.findByIds(createOrderRequest.getIds());
        Order order = new Order()
                .setStatus(OrderStatus.CREATED)
                .setItems(items)
                .setTotalItems(items.size())
                .setTotalPayments(0);
        order.setUser(UserUtil.getCurrentUser());

        orderRepository.save(order);

        String number = String.format(NUMBER_PATTERN, order.getId());
        order.setNumber(number);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Order getById(Long id) {
        return entityRetriever.retrieveEntityById(id, orderRepository);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getAll() {
        User currentUser = UserUtil.getCurrentUser();
        if (currentUser.getRole() == Role.USER) {
            return orderRepository.findAllByCreatorId(currentUser.getId());
        }
        return orderRepository.findAll();
    }

    @Override
    public Order update(Order order) {
        validationUtil.validateEntityUpdating(order);
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        validationUtil.validateEntityUpdating(getById(id));
        orderRepository.deleteById(id);
    }

    @Override
    public Order addItemToOrder(AddItemRequest addItemRequest) {
        Order order = getById(addItemRequest.getOrderId());

        validationUtil.validateAddingItemToOrder(order);

        Item item = itemService.getById(addItemRequest.getItemId());
        order.getItems().add(item);
        order.setTotalItems(order.getItems().size());

        return orderRepository.save(order);
    }

    @Override
    public UpdateStatusResponse updateStatus(Long id, OrderStatus newStatus) {
        Order order = getById(id);

        validationUtil.validateStatusUpdating(newStatus, order);

        UpdateStatusResponse updateStatusResponse = new UpdateStatusResponse()
                .setOrderNumber(order.getNumber())
                .setOldStatus(order.getStatus())
                .setNewStatus(newStatus);


        order.setStatus(newStatus);
        update(order);
        return updateStatusResponse;
    }
}
