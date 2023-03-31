package com.gbsfo.test.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Item extends com.gbsfo.test.task.entity.Entity {

    private String name;

    private Double price;

    @ManyToMany(mappedBy = "items")
    @JsonIgnore
    @ToString.Exclude
    private List<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item item)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(name, item.name) && Objects.equals(price, item.price) && Objects.equals(orders, item.orders);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
