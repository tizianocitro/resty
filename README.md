# resty
A Rest client built on top of Jersey client to provide an easy to use API.

## How to use
You can create a client instance as follows:
```c
Resty resty = Resty.builder().build();
```

To make a request you have access to sync and async methods for each HTTP method you may need.
To make a GET request you can do:
```c
resty.get("endpoint-here",
          withHeader("your-parameter-name", "your-parameter-value"),
          withParameter("your-parameter-name", "your-parameter-value"));
```

To access the response and its information:
```c
RestResponse response = resty.get(...);
if (response.isSuccess()) {
    response.getBody(YourClass.class);
}
```

