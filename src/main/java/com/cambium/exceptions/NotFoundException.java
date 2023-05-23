package com.cambium.exceptions;


import com.cambium.exceptions.advice.models.enums.ErrorCode;

public class NotFoundException extends RuntimeException {

    private String code = ErrorCode.NOT_FOUND.getCode();

    public NotFoundException() {
        super(ErrorCode.NOT_FOUND.getCode());
    }

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
