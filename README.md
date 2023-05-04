# Resty
An HTTP client built on top of the Jersey client to provide an easy-to-use API.

## How to use
You can create a client instance as follows:
```java
Resty resty = Resty.builder().build();
```

To make a request, you can access sync and async methods for each HTTP method you need.
To make a GET request:
```java
resty.get("endpoint-here",
          withHeader("your-header-name", "your-header-value"),
          withParameter("your-parameter-name", "your-parameter-value"));
```

To access the response and its information:
```java
RestResponse response = resty.get(...);
if (response.isSuccess()) {
    YourClass yourClass = response.getBody(YourClass.class);
}
```
