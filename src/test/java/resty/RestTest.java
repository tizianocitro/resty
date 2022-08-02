package resty;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import resty.response.RestResponse;

import static java.lang.Boolean.parseBoolean;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static resty.RestClientProperty.DEV_MODE_ACTIVE;
import static resty.entity.RestEntity.NO_BODY;
import static resty.entity.RestEntity.NO_ENTITY;

public class RestTest {

    @Test
    public void get() {
        Resty resty = Resty.builder()
                .activeDevMode(parseBoolean(DEV_MODE_ACTIVE.toValue()))
                .build();
        String retrieveRoute = format("%s/retrieve", baseUrl);
        RestResponse response = resty.get(retrieveRoute, NO_ENTITY);
        assertTrue(response.isSuccess());
        System.out.printf("GET result: %s\n", response.getBody());
    }

    @Test
    public void post() {
        Resty resty = Resty.builder()
                .activeDevMode(parseBoolean(DEV_MODE_ACTIVE.toValue()))
                .build();
        String saveRoute = format("%s/save", baseUrl);
        RestResponse response = resty.post(saveRoute, NO_BODY, NO_ENTITY);
        assertTrue(response.isSuccess());
        System.out.printf("POST result: %s", response.getBody());
    }

    @BeforeAll
    public static void setup() {
        baseUrl = "https://80cc1f35-6d34-4d16-abb4-1522e182244d.mock.pstmn.io";
    }

    private static String baseUrl;
}
