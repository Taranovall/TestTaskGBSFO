package com.gbsfo.test.task.util;

import com.gbsfo.test.task.entity.Entity;
import com.gbsfo.test.task.entity.Order;
import com.gbsfo.test.task.entity.OrderStatus;
import com.gbsfo.test.task.entity.Role;
import com.gbsfo.test.task.entity.User;
import com.gbsfo.test.task.exception.EntityUpdatingException;
import com.gbsfo.test.task.exception.PaymentException;
import com.gbsfo.test.task.exception.StatusCannotBeUpdatedException;
import com.gbsfo.test.task.payload.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.gbsfo.test.task.util.Constant.ALL_ITEMS_MUST_BE_PAID_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.ENTITY_UPDATING_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.PAYMENT_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.ORDER_ALREADY_HAS_THIS_STATUS_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.ORDER_FULLY_PAID_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.PAYMENT_SUM_CONSTRAINT_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.YOU_CANNOT_ADD_NEW_ITEM_ERROR_MESSAGE;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrderUtil orderUtil;

    /**
     * Validates a payment creation request for a given order.
     * The method checks whether the current user is the same as the user who created the order,
     * and whether the payment amount is valid based on the current amount paid and the total required price of the order.
     *
     * @param paymentRequest the PaymentRequest object containing the payment information
     * @param order          the Order object for which the payment is being made
     * @throws PaymentException if the payment request is invalid, such as if the current user did not create the order, if the order is already fully paid, or if the payment amount exceeds the required price for the order
     */
    public void validatePaymentCreation(PaymentRequest paymentRequest, Order order) {
        User orderCreator = order.getUser();
        User currentUser = UserUtil.getCurrentUser();

        if (!orderCreator.equals(currentUser)) {
            throw new PaymentException(PAYMENT_ERROR_MESSAGE);
        }

        double sumPaid = orderUtil.getPaidAmountOfMoney(order);
        double requiredSum = orderUtil.getRequiredPrice(order);
        if (Double.compare(sumPaid, requiredSum) == 0) {
            throw new PaymentException(ORDER_FULLY_PAID_ERROR_MESSAGE);
        }

        if (Double.compare(sumPaid + paymentRequest.getSum(), requiredSum) > 0) {
            String message = String.format(PAYMENT_SUM_CONSTRAINT_ERROR_MESSAGE, requiredSum - sumPaid);
            throw new PaymentException(message);
        }
    }


    /**
     * Validates the addition of a new item to an order.
     *
     * @param order the Order object to which the new item is being added
     * @throws StatusCannotBeUpdatedException if the order is already in a state where new items cannot be added
     */
    public void validateAddingItemToOrder(Order order) {
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.SHIPPING) {
            String message = String.format(YOU_CANNOT_ADD_NEW_ITEM_ERROR_MESSAGE, order.getStatus().name());
            throw new StatusCannotBeUpdatedException(message);
        }
    }

    /**
     * Validates an update of the order's status.
     *
     * @param newStatus the new status to which the order is being updated
     * @param order     the Order object for which the status is being updated
     * @throws StatusCannotBeUpdatedException if the new status is the same as the current status, or if the new status cannot be updated based on the payment status of the order
     */
    public void validateStatusUpdating(OrderStatus newStatus, Order order) {
        if (order.getStatus() == newStatus) {
            String message = String.format(ORDER_ALREADY_HAS_THIS_STATUS_ERROR_MESSAGE, newStatus.name());
            throw new StatusCannotBeUpdatedException(message);
        }

        if ((newStatus == OrderStatus.SHIPPING || newStatus == OrderStatus.DELIVERED) && !isFullPricePaid(order)) {
            String message = String.format(ALL_ITEMS_MUST_BE_PAID_ERROR_MESSAGE, newStatus);
            throw new StatusCannotBeUpdatedException(message);
        }
    }

    /**
     * Validates entity updating.
     *
     * @param entity the entity to be updated
     * @throws EntityUpdatingException if the current user did not create this entity
     */
    public void validateEntityUpdating(Entity entity) {
        User entityCreator = entity.getUser();
        User currentUser = UserUtil.getCurrentUser();
        if (currentUser.getRole() != Role.MANAGER) {
            if (!entityCreator.equals(currentUser)) {
                throw new EntityUpdatingException(ENTITY_UPDATING_ERROR_MESSAGE);
            }
        }
    }

    private boolean isFullPricePaid(Order order) {
        double requiredPrice = orderUtil.getRequiredPrice(order);
        double paidAmountOfMoney = orderUtil.getPaidAmountOfMoney(order);
        return Double.compare(requiredPrice, paidAmountOfMoney) == 0;
    }
}
