package resty;

import resty.entity.RestEntity;
import resty.response.AsyncRestResponse;
import resty.response.RestResponse;

/**
 * Provides methods for required HTTP methods in order to make requests.
 */
public interface RestClient {

    /**
     * Performs a request for the GET HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    RestResponse get(String endpoint, RestEntity... entities);

    /**
     * Performs a request for the GET HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    RestResponse get(String endpoint, int connectionTimeout, int readTimeout, RestEntity... entities);

    /**
     * Performs an async request for the GET HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    AsyncRestResponse asyncGet(String endpoint, RestEntity... entities);

    /**
     * Performs an async request for the GET HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    AsyncRestResponse asyncGet(
            String endpoint, int connectionTimeout, int readTimeout, RestEntity... entities);

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
    <Body> RestResponse post(String endpoint, Body body, RestEntity... entities);

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
    <Body> RestResponse post(String endpoint, Body body, String mediaType,
                             int connectionTimeout, int readTimeout, RestEntity... entities);

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
    <Body> AsyncRestResponse asyncPost(String endpoint, Body body, RestEntity... entities);

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
    <Body> AsyncRestResponse asyncPost(String endpoint, Body body, String mediaType,
                                       int connectionTimeout, int readTimeout, RestEntity... entities);

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
    <Body> RestResponse put(String endpoint, Body body, RestEntity... entities);

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
    <Body> RestResponse put(String endpoint, Body body, String mediaType,
                            int connectionTimeout, int readTimeout, RestEntity... entities);

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
    <Body> AsyncRestResponse asyncPut(String endpoint, Body body, RestEntity... entities);

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
    <Body> AsyncRestResponse asyncPut(String endpoint, Body body, String mediaType,
                                      int connectionTimeout, int readTimeout, RestEntity... entities);

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
    <Body> RestResponse patch(String endpoint, Body body, RestEntity... entities);

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
    <Body> RestResponse patch(String endpoint, Body body, String mediaType,
                              int connectionTimeout, int readTimeout, RestEntity... entities);

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
    <Body> AsyncRestResponse asyncPatch(String endpoint, Body body, RestEntity... entities);

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
    <Body> AsyncRestResponse asyncPatch(String endpoint, Body body, String mediaType,
                                        int connectionTimeout, int readTimeout, RestEntity... entities);

    /**
     * Performs a request for the DELETE HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    RestResponse delete(String endpoint, RestEntity... entities);

    /**
     * Performs a request for the DELETE HTTP method.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param connectionTimeout is the value for the connection timeout property.
     * @param readTimeout is the value for the read timeout property.
     * @param entities headers and parameters for the request.
     * @return the response given back to the performed request.
     */
    RestResponse delete(String endpoint, int connectionTimeout, int readTimeout, RestEntity... entities);

    /**
     * Performs an async request for the DELETE HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    AsyncRestResponse asyncDelete(String endpoint, RestEntity... entities);

    /**
     * Performs an async request for the DELETE HTTP method.
     * It uses default values for both connectionTimeout and readTimeout properties.
     *
     * @param endpoint the endpoint of the service to which make the request.
     * @param entities headers and parameters for the request.
     * @return the async response to handle the result of the performed request.
     */
    AsyncRestResponse asyncDelete(
            String endpoint, int connectionTimeout, int readTimeout, RestEntity... entities);

    /**
     * Provides the default value for the connection timeout property of the REST client.
     *
     * @return the default value for the connection timeout property of the REST client
     */
    int getDefaultConnectionTimeout();

    /**
     * Provides the default value for the read timeout property of the REST client.
     *
     * @return the default value for the read timeout property of the REST client
     */
    int getDefaultReadTimeout();
}
