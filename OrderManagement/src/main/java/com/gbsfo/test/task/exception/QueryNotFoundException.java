package com.gbsfo.test.task.exception;

public class QueryNotFoundException extends RuntimeException {

    public QueryNotFoundException(String message) {
        super(message);
    }
}
