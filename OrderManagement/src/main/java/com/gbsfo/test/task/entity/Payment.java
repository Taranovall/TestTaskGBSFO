package com.gbsfo.test.task.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
public class Payment extends com.gbsfo.test.task.entity.Entity {

    private String number;

    private Double sum;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone = "Europe/Kiev")
    private Date paymentDate;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private Order order;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment payment)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(number, payment.number) && Objects.equals(sum, payment.sum) && Objects.equals(paymentDate, payment.paymentDate) && Objects.equals(order, payment.order);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
