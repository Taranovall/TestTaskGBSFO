package com.gbsfo.test.task.exception;

public class IncorrectOrderStatus extends RuntimeException {

    public IncorrectOrderStatus(String message) {
        super(message);
    }
}
