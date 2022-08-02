package resty.response;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Models an async response for a REST request.
 */
@ToString
@NoArgsConstructor
public class AsyncRestResponse {

    /**
     * Builds an async response for a REST request.
     *
     * @param client is the client used for the request.
     * @param futureResponse is the future to manage the response.
     */
    public AsyncRestResponse(Client client, Future<Response> futureResponse) {
        this.client = client;
        this.futureResponse = futureResponse;
    }

    /**
     * Wait for the request to complete.
     *
     * @return the response based on data given back by the called service.
     */
    public RestResponse waitForResponse() throws ExecutionException, InterruptedException {
        Response response = futureResponse.get();
        restResponse = new RestResponse(response.getStatus(), response.readEntity(String.class));
        close();
        return restResponse;
    }

    /**
     * Provides the rest response if it has been waited.
     *
     * @return the rest response.
     */
    public RestResponse getRestResponse() {
        return restResponse;
    }
    
    /**
     * Closes the client used for the request.
     */
    public void close() {
        client.close();
    }

    /**
     * The client used for the request.
     */
    private Client client;

    /**
     * The future to manage the response.
     */
    private Future<Response> futureResponse;

    /**
     * The response given back by the called service.
     */
    private RestResponse restResponse;
}
