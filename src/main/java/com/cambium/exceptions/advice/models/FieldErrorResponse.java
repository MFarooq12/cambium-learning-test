package com.cambium.exceptions.advice.models;

import java.util.Collections;
import java.util.List;

public class FieldErrorResponse extends ErrorResponse {
    public FieldErrorResponse() {
    }

    private List<FieldError> fieldErrors = Collections.emptyList();

    public FieldErrorResponse(String code, String message, List<FieldError> fieldErrors) {
        super(code, message);
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    void addFieldError(String field, String errorCode, String errorMessage) {
        fieldErrors.add(new FieldError(errorCode, field, errorMessage));
    }

    public static class FieldError {
        private String code;
        private String field;
        private String message;

        public FieldError() {
        }

        public FieldError(String field, String code, String message) {
            this.code = code;
            this.field = field;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setField(String field) {
            this.field = field;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
