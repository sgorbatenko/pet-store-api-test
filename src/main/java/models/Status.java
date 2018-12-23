package models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");
    private final String value;

    Status(String value) {
        this.value = value;
    }

    @JsonValue
    final String value() {
        return this.value;
    }
}
