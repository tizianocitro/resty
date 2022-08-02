package resty;

/**
 * Properties relevant for the REST client.
 */
public enum RestClientProperty {
    DEV_MODE_ACTIVE("true"),
    TLS("TLS"),
    CONNECTION_TIMEOUT("5000"),
    READ_TIMEOUT("5000");

    /**
     * Provides a property for the client.
     *
     * @param value the value for the property.
     */
    RestClientProperty(String value) {
        this.value = value;
    }

    /**
     * Provides a client property as a string.
     *
     * @return the client property as a string
     */
    public String toValue() {
        return value;
    }

    /**
     * Client property as a string.
     */
    private final String value;
}
