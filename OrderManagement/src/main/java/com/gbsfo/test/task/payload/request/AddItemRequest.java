package com.gbsfo.test.task.payload.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AddItemRequest {

    @Min(0)
    private Long itemId;
    @Min(0)
    private Long orderId;
}
