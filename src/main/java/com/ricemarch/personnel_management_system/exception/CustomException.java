package com.ricemarch.personnel_management_system.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private String message;

    public CustomException(int code, String message) {
        this.message = message;
    }

    public CustomException(String message) {
        this.message = message;
    }
}
