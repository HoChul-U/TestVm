package com.nhnacademy.project.testvm.data;

import java.util.HashMap;
import java.util.Map;

public class JsonData {

    private final Map<String, String> args = new HashMap<>();

    private String data = "";

    private final Map<String, String> files = new HashMap<>();

    private final Map<String, String> form = new HashMap<>();

    private final Map<String, String> headers = new HashMap<>();

    private Map<String, String> json;

    private String origin;

    private String url = "{}";

    public void putArgs(String key, String value) {
        this.args.put(key, value);
    }

    public void putFiles(String key, String value) {
        this.files.put(key, value);
    }

    public void putHeaders(String key, String value) {
        this.headers.put(key, value);
    }

    public String getUrl() {
        return url;
    }

    public String getData() {
        return data;
    }

    public String getOrigin() {
        return origin;
    }

    public Map<String, String> getForm() {
        return form;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public Map<String, String> getJson() {
        return json;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setData(String data) {

        this.data = data;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setJson(Map<String, String> json) {
        this.json = json;
    }
}
