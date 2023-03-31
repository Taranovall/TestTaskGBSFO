package com.gbsfo.test.task.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class OrderStatusRequest {

    @NotNull
    @Length
    private String status;
}
