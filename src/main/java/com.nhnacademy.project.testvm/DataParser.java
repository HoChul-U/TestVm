package com.nhnacademy.project.testvm;

import java.io.BufferedReader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DataParser {

    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, d ,M yyyy HH:mm:ss z");

    StringBuilder request;
    BufferedReader reader;
    StringBuilder response = new StringBuilder();

    public DataParser(StringBuilder request) {
        this.request = request;
    }

    void dataParsing() {
        String dateString = dateFormat.format(date);
        Scanner scanner = new Scanner(request.toString());
        String oneLine = scanner.nextLine();
        oneLine = oneLine.split("/ ")[1];
        response.append(oneLine +" 200 OK"+"\n");
        response.append("Content-Type: application/json"+"\n");
        response.append("Date: " + dateString + "\n");
        response.append("Content-length: "+"\n");
        response.append("Connection: keep-alive"+"\n");
        response.append("Server: gunicorn/19.9.0");
        response.append("Access-Control-Allow-Origin: *" + "\n");
        response.append("Access-Control-Allow-Credentials: true");

        System.out.println(response);
    }
}
