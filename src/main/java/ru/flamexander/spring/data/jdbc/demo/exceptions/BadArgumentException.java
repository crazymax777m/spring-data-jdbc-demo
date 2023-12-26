package ru.flamexander.spring.data.jdbc.demo.exceptions;

public class BadArgumentException extends IllegalArgumentException {
    public BadArgumentException(String message) {
        super(message);
    }
}
