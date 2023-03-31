package com.gbsfo.test.task.service.impl;

import com.gbsfo.test.task.entity.Order;
import com.gbsfo.test.task.entity.Payment;
import com.gbsfo.test.task.entity.Role;
import com.gbsfo.test.task.entity.User;
import com.gbsfo.test.task.payload.request.PaymentRequest;
import com.gbsfo.test.task.repository.PaymentRepository;
import com.gbsfo.test.task.service.OrderService;
import com.gbsfo.test.task.service.PaymentService;
import com.gbsfo.test.task.util.EntityRetriever;
import com.gbsfo.test.task.util.UserUtil;
import com.gbsfo.test.task.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.gbsfo.test.task.util.Constant.NUMBER_PATTERN;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final ValidationUtil validationUtil;
    private final EntityRetriever entityRetriever;

    @Override
    public Payment create(PaymentRequest paymentRequest) {
        Order order = orderService.getById(paymentRequest.getOrderId());

        validationUtil.validatePaymentCreation(paymentRequest, order);

        Payment payment = new Payment()
                .setOrder(order)
                .setSum(paymentRequest.getSum())
                .setPaymentDate(new Date(System.currentTimeMillis()));

        payment.setUser(UserUtil.getCurrentUser());

        paymentRepository.save(payment);

        payment.setNumber(String.format(NUMBER_PATTERN, payment.getId()));
        order.getPayments().add(payment);
        order.setTotalPayments(order.getPayments().size());
        orderService.update(order);

        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    @Override
    public Payment getById(Long id) {
        return entityRetriever.retrieveEntityById(id, paymentRepository);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Payment> getAll() {
        User currentUser = UserUtil.getCurrentUser();
        if (currentUser.getRole() == Role.USER) {
            return paymentRepository.findAllByCreatorId(currentUser.getId());
        }
        return paymentRepository.findAll();
    }

    @Override
    public Payment update(Payment payment) {
        validationUtil.validateEntityUpdating(payment);
        return paymentRepository.save(payment);
    }

    @Override
    public void delete(Long id) {
        validationUtil.validateEntityUpdating(getById(id));
        paymentRepository.deleteById(id);
    }
}
