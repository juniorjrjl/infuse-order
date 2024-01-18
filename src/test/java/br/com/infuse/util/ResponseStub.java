package br.com.infuse.util;

import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ResponseStub extends Response {

    private final Map<String, String> headers = new HashMap<>();

    private int statusCode = -1;

    private String body;

    public ResponseStub(){
        super();
    }

    @Override
    public void header(String header, String value) {
        headers.put(header, value);
    }

    public void status(int statusCode) {
        this.statusCode = statusCode;
    }

    public void body(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }
}
