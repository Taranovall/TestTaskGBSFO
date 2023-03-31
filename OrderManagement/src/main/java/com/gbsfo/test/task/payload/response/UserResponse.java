package com.gbsfo.test.task.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gbsfo.test.task.entity.Order;
import com.gbsfo.test.task.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Data
@AllArgsConstructor
public class UserResponse {
    private String username;
    private Role role;
    @JsonInclude(Include.NON_NULL)
    private Set<Order> orders;
}
