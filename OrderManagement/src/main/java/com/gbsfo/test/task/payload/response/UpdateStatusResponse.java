package com.gbsfo.test.task.payload.response;

import com.gbsfo.test.task.entity.OrderStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateStatusResponse {

    private String orderNumber;
    private OrderStatus oldStatus;
    private OrderStatus newStatus;
}
