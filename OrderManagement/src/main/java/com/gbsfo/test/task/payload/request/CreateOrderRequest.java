package com.gbsfo.test.task.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @JsonProperty("list")
    @NotNull
    private List<Long> ids;
}
