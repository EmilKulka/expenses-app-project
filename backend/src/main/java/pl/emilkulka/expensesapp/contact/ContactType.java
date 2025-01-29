package pl.emilkulka.expensesapp.contact;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ContactType {
    TECHNICAL_ISSUE("Technical issue"),
    FEATURE_REQUEST("Feature request"),
    OTHER("Other");

    private final String value;

    ContactType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ContactType fromValue(String value) {
        for (ContactType type : ContactType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum type: " + value);
    }


}
