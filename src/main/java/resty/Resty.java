package resty;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.ClientProperties;
import resty.entity.RestEntity;
import resty.entity.TypedRestEntity;
import resty.response.AsyncRestResponse;
import resty.response.RestResponse;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.Future;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.glassfish.jersey.client.HttpUrlConnectorProvider.SET_METHOD_WORKAROUND;
import static resty.RestClientProperty.*;
import static resty.entity.TypedRestEntity.buildFromEntities;

/**
 * Provides methods for required HTTP methods in order to make requests.
 */
@Builder
@Slf4j
public class Resty implements RestClient {

    private boolean activeDevMode;

    /**
     * Performs a request for the GET HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public RestResponse get(String endpoint, RestEntity... entities) {
        log.debug("Using default values for timeout");
        return get(endpoint, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs a request for the GET HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public RestResponse get(String endpoint, int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.debug("Building request for GET method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.debug("Making GET request");
        Response response = invocationBuilder.get(Response.class);
        RestResponse restResponse = new RestResponse(response.getStatus(), response.readEntity(String.class));
        client.close();
        log.debug("GET request completed");
        return restResponse;
    }

    /**
     * Performs an async request for the GET HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public AsyncRestResponse asyncGet(String endpoint, RestEntity... entities) {
        log.debug("Using default value for timeouts and application/json for media type");
        return asyncGet(endpoint, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs an async request for the GET HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public AsyncRestResponse asyncGet(
            String endpoint, int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.debug("Building request for async GET method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.debug("Making async GET request");
        Future<Response> futureResponse = invocationBuilder.async().get(Response.class);
        log.debug("Async GET request made");
        return new AsyncRestResponse(client, futureResponse);
    }

    /**
     * Performs a request for the POST HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     * It also uses a default value (application/json) for the media type.
     *
     * @param endpoint  the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public <Body> RestResponse post(String endpoint, Body body, RestEntity... entities) {
        log.debug("Using default value for timeouts and application/json for media type");
        return post(endpoint, body, APPLICATION_JSON, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs a request for the POST HTTP method.
     *
     * @param endpoint  the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param mediaType the media type for the request to be performed.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public <Body> RestResponse post(String endpoint, Body body, String mediaType,
                                    int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.debug("Building request for POST method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.debug("Making POST request");
        Response response = invocationBuilder.post(Entity.entity(body, mediaType), Response.class);
        RestResponse restResponse = new RestResponse(response.getStatus(), response.readEntity(String.class));
        client.close();
        log.debug("POST request completed");
        return restResponse;
    }

    /**
     * Performs an async request for the POST HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     * It also uses a default value (application/json) for the media type.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public <Body> AsyncRestResponse asyncPost(String endpoint, Body body, RestEntity... entities) {
        log.debug("Using default value for timeouts and application/json for media type");
        return asyncPost(endpoint, body, APPLICATION_JSON, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs an async request for the POST HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param mediaType the media type for the request to be performed.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public <Body> AsyncRestResponse asyncPost(String endpoint, Body body, String mediaType,
                                              int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.debug("Building request for async POST method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.debug("Making async POST request");
        Future<Response> futureResponse = invocationBuilder.async().post(Entity.entity(body, mediaType), Response.class);
        log.debug("Async POST request made");
        return new AsyncRestResponse(client, futureResponse);
    }

    /**
     * Performs a request for the PUT HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     * It also uses a default value (application/json) for the media type.
     *
     * @param endpoint  the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public <Body> RestResponse put(String endpoint, Body body, RestEntity... entities) {
        log.debug("Using default value for timeouts and application/json for media type");
        return put(endpoint, body, APPLICATION_JSON, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs a request for the PUT HTTP method.
     *
     * @param endpoint  the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param mediaType the media type for the request to be performed.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public <Body> RestResponse put(String endpoint, Body body, String mediaType,
                                   int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.info("Building request for PUT method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.info("Making PUT request");
        Response response = invocationBuilder.put(Entity.entity(body, mediaType), Response.class);
        RestResponse restResponse = new RestResponse(response.getStatus(), response.readEntity(String.class));
        client.close();
        log.info("PUT request completed");
        return restResponse;
    }

    /**
     * Performs an async request for the PUT HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     * It also uses a default value (application/json) for the media type.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public <Body> AsyncRestResponse asyncPut(String endpoint, Body body, RestEntity... entities) {
        log.debug("Using default value as application/json for media type");
        return asyncPut(endpoint, body, APPLICATION_JSON, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs an async request for the PUT HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param mediaType the media type for the request to be performed.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public <Body> AsyncRestResponse asyncPut(String endpoint, Body body, String mediaType,
                                             int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.debug("Building request for async PUT method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.debug("Making async PUT request");
        Future<Response> futureResponse = invocationBuilder.async().put(Entity.entity(body, mediaType), Response.class);
        log.debug("Async PUT request made");
        return new AsyncRestResponse(client, futureResponse);
    }

    /**
     * Performs a request for the PATCH HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     * It also uses a default value (application/json) for the media type.
     *
     * @param endpoint  the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public <Body> RestResponse patch(String endpoint, Body body, RestEntity... entities) {
        log.info("Using default value as application/json for media type");
        return patch(endpoint, body, APPLICATION_JSON, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs a request for the PATCH HTTP method.
     *
     * @param endpoint  the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param mediaType the media type for the request to be performed.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public <Body> RestResponse patch(String endpoint, Body body, String mediaType,
                                     int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.info("Building request for PATCH method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.info("Making PATCH request");
        Response response = invocationBuilder.method("PATCH", Entity.entity(body, mediaType), Response.class);
        RestResponse restResponse = new RestResponse(response.getStatus(), response.readEntity(String.class));
        client.close();
        log.info("PATCH request completed");

        return restResponse;
    }

    /**
     * Performs an async request for the PATCH HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     * It also uses a default value (application/json) for the media type.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public <Body> AsyncRestResponse asyncPatch(String endpoint, Body body, RestEntity... entities) {
        log.debug("Using default value as application/json for media type");
        return asyncPatch(endpoint, body, APPLICATION_JSON, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs an async request for the PATCH HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param body the body associated to the request.
     * @param mediaType the media type for the request to be performed.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public <Body> AsyncRestResponse asyncPatch(String endpoint, Body body, String mediaType,
                                             int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.debug("Building request for async PATCH method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.debug("Making async PATCH request");
        Future<Response> futureResponse = invocationBuilder.async()
                .method("PATCH", Entity.entity(body, mediaType), Response.class);
        log.debug("Async PATCH request made");
        return new AsyncRestResponse(client, futureResponse);
    }

    /**
     * Performs a request for the DELETE HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public RestResponse delete(String endpoint, RestEntity... entities) {
        log.info("Using default values for timeout");
        return delete(endpoint, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs a request for the DELETE HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    @Override
    public RestResponse delete(String endpoint, int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.info("Building request for DELETE method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.info("Making DELETE request");
        Response response = invocationBuilder.delete(Response.class);
        RestResponse restResponse = new RestResponse(response.getStatus(), response.readEntity(String.class));
        client.close();
        log.info("DELETE request completed");

        return restResponse;
    }

    /**
     * Performs an async request for the DELETE HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public AsyncRestResponse asyncDelete(String endpoint, RestEntity... entities) {
        log.debug("Using default value for timeouts and application/json for media type");
        return asyncDelete(endpoint, getDefaultConnectionTimeout(), getDefaultReadTimeout(), entities);
    }

    /**
     * Performs an async request for the DELETE HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    @Override
    public AsyncRestResponse asyncDelete(
            String endpoint, int connectionTimeout, int readTimeout, RestEntity... entities) {
        log.debug("Building request for async DELETE method");
        Client client = generateClient(connectionTimeout, readTimeout);
        if (client == null) {
            throw new RuntimeException("Cannot generate client");
        }
        Invocation.Builder invocationBuilder = generateInvocationBuilder(client, endpoint, entities);
        log.debug("Making async DELETE request");
        Future<Response> futureResponse = invocationBuilder.async().delete(Response.class);
        log.debug("Async DELETE request made");
        return new AsyncRestResponse(client, futureResponse);
    }

    /**
     * Provides the default value for the connection timeout property of the REST client.
     *
     * @return the default value for the connection timeout property of the REST client
     */
    @Override
    public int getDefaultConnectionTimeout() {
        return Integer.parseInt(CONNECTION_TIMEOUT.toValue());
    }

    /**
     * Provides the default value for the read timeout property of the REST client.
     *
     * @return the default value for the read timeout property of the REST client
     */
    @Override
    public int getDefaultReadTimeout() {
        return Integer.parseInt(READ_TIMEOUT.toValue());
    }

    /**
     * Sets the headers for the REST request.
     *
     * @param invocationBuilder the invocation builder to which headers have to be associated to.
     * @param headers headers for the request.
     */
    private void setHeaders(Invocation.Builder invocationBuilder, List<RestEntity> headers) {
        log.debug("Setting headers for request");
        headers.forEach(h -> invocationBuilder.header(h.getName(), h.getValue()));
    }

    /**
     * Sets the query parameters for the REST request.
     *
     * @param webTarget the web target to which parameters have to be associated to.
     * @param parameters parameters for the request.
     * @return the web target with parameters associated to it.
     */
    private WebTarget setParameters(WebTarget webTarget, List<RestEntity> parameters) {
        log.debug("Setting query parameters for request");
        for (RestEntity p : parameters) {
            webTarget = webTarget.queryParam(p.getName(), p.getValue());
        }
        return webTarget;
    }

    /**
     * Generates a new invocation builder to actually make REST requests.
     *
     * @param client the client created and that is going to be used for requests.
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the instantiated invocation builder.
     */
    private Invocation.Builder generateInvocationBuilder(Client client, String endpoint, RestEntity[] entities) {
        TypedRestEntity typedRestEntity = buildFromEntities(entities);
        List<RestEntity> headers = typedRestEntity.getHeaders();
        WebTarget webTarget = generateWebTarget(client, endpoint, typedRestEntity);
        Invocation.Builder invocationBuilder = webTarget.request();
        setHeaders(invocationBuilder, headers);
        return invocationBuilder;
    }

    /**
     * Generates a new web target to use for instantiating a new invocation build for REST requests.
     * It sets the query parameters for the request.
     *
     * @param client the client created and that is going to be used for requests.
     * @param endpoint the endpoint of the service to which make the request.
     * @param typedRestEntity it contains the query parameters to set for the request.
     * @return the created web target.
     */
    private WebTarget generateWebTarget(Client client, String endpoint, TypedRestEntity typedRestEntity) {
        List<RestEntity> parameters = typedRestEntity.getParameters();
        WebTarget webTarget = client.target(endpoint).property(SET_METHOD_WORKAROUND, true);
        webTarget = setParameters(webTarget, parameters);
        return webTarget;
    }

    /**
     * Generates a new client for REST requests.
     * Returns null if for some reason it is not allowed to create the client.
     *
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @return the created client.
     */
    private Client generateClient(int connectionTimeout, int readTimeout) {
        log.debug("Generating client");
        Client client = null;
        if (activeDevMode) {
            log.debug("Dev mode is active");
            try {
                SSLContext sslcontext = SSLContext.getInstance(TLS.toValue());
                sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) {
                    }
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) {
                    }
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }}, new java.security.SecureRandom());
                client = ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                log.debug("Cannot generate client due to: " + e.getMessage());
            }
        } else {
            log.debug("Dev mode is not active");
            client = newClient();
        }
        if (client != null) {
            client.property(ClientProperties.CONNECT_TIMEOUT, connectionTimeout);
            client.property(ClientProperties.READ_TIMEOUT, readTimeout);
            log.debug("Client generated");
        }
        return client;
    }
}
