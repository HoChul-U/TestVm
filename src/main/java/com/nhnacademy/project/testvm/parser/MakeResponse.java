package com.nhnacademy.project.testvm.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.project.testvm.data.JsonData;

public class MakeResponse {

    int contentLength;

    public void makeHeader(StringBuilder header, String dateString, String http,
                           int contentLength) {
        header.append(http + " 200 OK" + System.lineSeparator());
        header.append("Content-Type: application/json" + System.lineSeparator());
        header.append("Date: " + dateString + System.lineSeparator());
        header.append("Content-length: " + contentLength + System.lineSeparator());
        header.append("Connection: keep-alive" + System.lineSeparator());
        header.append("Server: gunicorn/19.9.0" + System.lineSeparator());
        header.append("Access-Control-Allow-Origin: *" + System.lineSeparator());
        header.append("Access-Control-Allow-Credentials: true" + System.lineSeparator());
        header.append(System.lineSeparator());
    }


    public String makeUrl(String host, String path) {
        String url = "http://" + host + path;
        return url.replace(" ", "");
    }

    public String makeBody(JsonData body, String clientIp, String url)
        throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        body.setOrigin(clientIp.replace("/", ""));
        body.setUrl(url);
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body) +
            System.lineSeparator();
        contentLength = jsonString.getBytes().length;
        return jsonString;
    }
}
