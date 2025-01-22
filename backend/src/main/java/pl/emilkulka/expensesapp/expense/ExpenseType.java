package pl.emilkulka.expensesapp.expense;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExpenseType {
    GROCERIES_AND_CHEMICALS("Groceries & Chemicals"),
    SHOES_AND_CLOTHES("Shoes & Clothes"),
    ACCESSORIES("Accessories"),
    REGULAR_PAYMENTS("Regular Payments"),
    ENTERTAINMENT("Entertainment"),
    HEALTHCARE("Healthcare"),
    RESTAURANTS("Restaurants"),
    HOBBIES("Hobbies");

    private final String value;

    ExpenseType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ExpenseType fromValue(String value) {
        for (ExpenseType type : ExpenseType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum type: " + value);
    }
}