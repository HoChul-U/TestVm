package com.nhnacademy.project.testvm;

import java.net.PortUnreachableException;
import java.util.Map;

public class JsonData {

    private Map<String,String> args;
    private Map<String,String> headers;

    private String origin;
    private String url;

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getOrigin() {
        return origin;
    }

    public String getUrl() {
        return url;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
