package com.nhnacademy.project.testvm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DataParser {
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

    void dataParsing() throws JsonProcessingException {
        String dateString = dateFormat.format(date);
        Scanner scanner = new Scanner(request.toString());
        String oneLine = scanner.nextLine();
        oneLine = oneLine.split("/ ")[1];
        header.append(oneLine + " 200 OK" + "\n");
        header.append("Content-Type: application/json" + "\n");
        header.append("Date: " + dateString + "\n");
        header.append("Content-length: " + "\n");
        header.append("Connection: keep-alive" + "\n");
        header.append("Server: gunicorn/19.9.0");
        header.append("Access-Control-Allow-Origin: *" + "\n");
        header.append("Access-Control-Allow-Credentials: true");

        System.out.println(header);
        makeBody();

    }

    void makeBody() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        body.setOrigin(this.clientIp);
        jsonString = objectMapper.writeValueAsString(body);
        System.out.println(jsonString);
    }
}
