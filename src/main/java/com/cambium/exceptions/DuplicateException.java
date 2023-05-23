package com.cambium.exceptions;

import com.cambium.exceptions.advice.models.enums.ErrorCode;

public class DuplicateException extends RuntimeException {
    private String code = ErrorCode.DUPLICATE_RECORD.getCode();

    public DuplicateException() {
        super(ErrorCode.DUPLICATE_RECORD.getCode());
    }

    public DuplicateException(String msg) {
        super(msg);
    }

    public DuplicateException(String code, String msg) {
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
