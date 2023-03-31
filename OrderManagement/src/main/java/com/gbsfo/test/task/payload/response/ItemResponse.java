package com.gbsfo.test.task.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemResponse {

    private Long creatorId;
    private Long itemId;
    private String name;
    private Double price;
}
