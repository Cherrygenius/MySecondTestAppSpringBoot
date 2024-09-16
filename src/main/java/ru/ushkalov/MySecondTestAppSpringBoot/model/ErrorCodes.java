package ru.ushkalov.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCodes {
    EMPTY(""),
    VALIDATION_EXEPTION("ValidationException"),
    UNKNOWN_EXEPTION("UnknownException"),
    UNSUPPORTED_EXEPTION("UnsupportedException");

    private final String name;

    ErrorCodes(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
