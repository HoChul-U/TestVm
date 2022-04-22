package com.nhnacademy.project.testvm.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import java.net.PortUnreachableException;
import java.util.HashMap;
import java.util.Map;

public class JsonData {

    private Map<String,String> args = new HashMap<>();

    private Map<String,String> headers = new HashMap<>();

    private String origin;

    private String url="{}";

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }
    public void putArgs(String key, String value){
        this.args.put(key, value);
    }
    public void putHeaders(String key, String value){
        this.headers.put(key, value);
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
