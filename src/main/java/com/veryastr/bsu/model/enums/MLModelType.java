package com.veryastr.bsu.model.enums;

public enum MLModelType {
    UNKNOWN("UNEXPECTED_VALUE"),
    LINEAR_REGRESSION("Linear regression"),
    LOGISTIC_REGRESSION("Logistic regression");

    private final String value;

    MLModelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public MLModelType fromValue(String value) {
        for (MLModelType modelType : MLModelType.values()) {
            if (modelType.value.equals(value))
                return modelType;
        }
        return UNKNOWN;
    }
}
