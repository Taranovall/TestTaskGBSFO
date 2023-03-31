package com.gbsfo.test.task.service;

import com.gbsfo.test.task.payload.request.PaymentRequest;
import com.gbsfo.test.task.entity.Payment;

public interface PaymentService extends DefaultService<Payment> {

    Payment create(PaymentRequest paymentRequest);
}
