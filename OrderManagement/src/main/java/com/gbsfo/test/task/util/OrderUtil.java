package com.gbsfo.test.task.util;

import com.gbsfo.test.task.entity.Item;
import com.gbsfo.test.task.entity.Order;
import com.gbsfo.test.task.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class OrderUtil {

    /**
     *
     * @param order
     * @return price of the order
     */
    public Double getRequiredPrice(Order order) {
        return order.getItems().stream()
                .mapToDouble(Item::getPrice)
                .sum();
    }

    /**
     *
     * @param order
     * @return amount of money which has been paid for the order
     */
    public Double getPaidAmountOfMoney(Order order) {
        return order.getPayments().stream()
                .mapToDouble(Payment::getSum)
                .sum();
    }
}
