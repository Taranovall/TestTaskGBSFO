package com.gbsfo.test.task.payload.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PaymentRequest {

    @Min(0)
    private Double sum;
    @Min(0)
    private Long orderId;
}
