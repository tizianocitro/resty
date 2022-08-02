package resty.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;

/**
 * Models a response for a REST request.
 */
@Getter
@ToString
public class RestResponse {

    /**
     * Builds a response based on data given back by the called service.
     *
     * @param status is the response status.
     * @param body is the response body.
     */
    public RestResponse(int status, String body) {
        this.status = status;
        this.body = body;
        this.success = verifyStatus(MIN_SUCCESS_CODE, MAX_SUCCESS_CODE);
    }

    /**
     * Checks whether the status indicates that response succeeded or failed.
     *
     * @param min the minimum value for a positive status.
     * @param max the maximum value for a positive status.
     * @return true if the status that response succeeded, false otherwise.
     */
    public boolean verifyStatus(int min, int max) {
        return status >= min && status < max;
    }

    /**
     * Provides the response body as an object based on the given class.
     * Returns null if the response body cannot be converted to the given class.
     *
     * @param bodyClass the class for the response body.
     * @return the response body as an instance of the given class.
     */
    public <Body> Body getBody(Class<Body> bodyClass) {
        try {
            return new ObjectMapper().readValue(body, bodyClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Response status.
     */
    private final int status;

    /**
     * Whether the response succeeded or failed.
     */
    private final boolean success;

    /**
     * Response body.
     */
    private final String body;

    /**
     * Maximum value for a positive status.
     */
    public static final int MAX_SUCCESS_CODE = 300;

    /**
     * Minimum value for a positive status.
     */
    public static final int MIN_SUCCESS_CODE = 200;
}
