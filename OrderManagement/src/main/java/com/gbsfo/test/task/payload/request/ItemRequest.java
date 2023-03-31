package com.gbsfo.test.task.payload.request;

import com.gbsfo.test.task.util.Constant;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ItemRequest {

    @Size(min = 4, max = 50, message = Constant.LENGTH_VALIDATION_MESSAGE)
    @NotNull
    private String name;

    @Min(value = 0, message = Constant.INCORRECT_PRICE_MESSAGE)
    @NotNull
    private Double price;
}
