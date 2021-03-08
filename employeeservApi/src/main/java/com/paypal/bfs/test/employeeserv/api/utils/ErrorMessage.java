package com.paypal.bfs.test.employeeserv.api.utils;

public enum ErrorMessage {
    NON_INTEGER_ID(0),
    URL_ID_REQUEST_ID_NOT_MATCHING(1),
    REQUIRED_FIELD(2),
    RESOURCE_ALREADY_EXISTS_WITH_SIMILAR_ID(3);

//    REQUEST_MODEL_NULL(0),
//    REQUIRED_FIELD(1),
//    FEE_NAME_ALREADY_PRESENT(2),
//    MAX_FEE_AMOUNT_IS_LESS_THAN_MIN_FEE_AMOUNT(3),
//    DEFAULT_FEE_AMOUNT_IS_NOT_IN_BETWEEN_MIN_MAX_FEE_AMOUNT(4),
//    GST_RATE_IS_REQUIRED_GST_IS_APPLICABLE(5),
//    DUPLICATE_ENTRY(6),
//    INVALID_SLAB_AMOUNT_RANGE(7),
//    INCORRECT_COMBINATION(8);

    private final int value;
    private ErrorMessage(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
