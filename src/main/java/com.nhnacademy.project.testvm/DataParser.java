package com.nhnacademy.project.testvm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DataParser {
    int length;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, d ,M yyyy HH:mm:ss z");

    private final StringBuilder request;
    private final String clientIp;
    private final JsonData body = new JsonData();
    StringBuilder header = new StringBuilder();
    private String jsonString;


    public DataParser(StringBuilder request, String clientIp) {
        this.request = request;
        this.clientIp = clientIp;
    }

    StringBuilder dataParsing() throws JsonProcessingException {
        String http;
        String dateString = dateFormat.format(date);
        Scanner scanner = new Scanner(request.toString());
        String oneLine = scanner.nextLine();
        System.out.println(oneLine);
        http = oneLine.split(" ")[2];
        header.append(http + " 200 OK" + System.lineSeparator());
        header.append("Content-Type: application/json" + System.lineSeparator());
        header.append("Date: " + dateString + System.lineSeparator());
        header.append("Content-length: " + length + System.lineSeparator());
        header.append("Connection: keep-alive" + System.lineSeparator());
        header.append("Server: gunicorn/19.9.0"+ System.lineSeparator());
        header.append("Access-Control-Allow-Origin: *" + System.lineSeparator());
        header.append("Access-Control-Allow-Credentials: true"+ System.lineSeparator());
        header.append(System.lineSeparator());
        return header;

    }

    String makeBody() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        body.setOrigin(this.clientIp.replace("/", ""));
        jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body)+System.lineSeparator();
        length = jsonString.getBytes().length;

        return jsonString;
    }
}
