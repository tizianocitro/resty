package resty.entity;

/**
 * Possible types for a rest entity.
 */
public enum RestEntityType {
    PARAMETER("PARAMETER"),
    HEADER("HEADER");

    /**
     * Provides a type based on a string value.
     *
     * @param value the value for the type.
     */
    RestEntityType(String value) {
        this.value = value;
    }

    /**
     * Provides the values of a type as a string.
     *
     * @return the value of a type as a string
     */
    public String toValue() {
        return value;
    }

    /**
     * Checks if a given type is equal to the current type.
     *
     * @param type type to check if it's equal or not.
     * @return true if types are equals, false otherwise.
     */
    public boolean equals(RestEntityType type) {
        return value.equals(type.toValue());
    }

    /**
     * Value of the type as a string.
     */
    private final String value;
}
