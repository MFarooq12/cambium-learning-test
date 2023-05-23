package com.cambium.exceptions.advice.models.enums;

import lombok.Getter;

public enum ErrorCode {


    NOT_FOUND("404"),

    INTERNAL_SERVER_ERROR("500"),

    DUPLICATE_RECORD("409"),

    BAD_REQUEST("400");


    @Getter
    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }
}
