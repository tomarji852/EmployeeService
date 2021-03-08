package com.paypal.bfs.test.employeeserv.api.utils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class ErrorResponseModel {
    private int errorCode;

    private String field;

    @Enumerated(EnumType.STRING)
    private ErrorMessage errorMessage;

    public ErrorResponseModel(String field, ErrorMessage errorMessage) {
        this.errorCode = errorMessage.getValue();
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

}