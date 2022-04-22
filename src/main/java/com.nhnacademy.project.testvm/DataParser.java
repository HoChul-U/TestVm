package com.nhnacademy.project.testvm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataParser {
    private static int count = 0;
    private String http;
    private String path;
    private String url;
    private String param;
    private List<String> paramList;
    int length;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, d ,M yyyy HH:mm:ss z");

    private final StringBuilder request;
    private final String clientIp;
    private final JsonData body = new JsonData();
    private final Map<String, String> map = new HashMap<>();
    StringBuilder header = new StringBuilder();

    private String jsonString;


    public DataParser(StringBuilder request, String clientIp) {
        this.request = request;
        this.clientIp = clientIp;
    }

    StringBuilder dataParsing() throws JsonProcessingException {
        String dateString = dateFormat.format(date);
        Scanner scanner = new Scanner(request.toString());
        String line;
        while ((scanner.hasNextLine())) {
            line = scanner.nextLine();
            if (count == 0) {
                path = line.split(" ")[1];
                http = line.split(" ")[2];

                if (path.indexOf("?")>0) {
                    checkParamList();
                }

                count++;
                continue;
            }
            map.put(line.split(":")[0], line.split(":")[1]);
        }
        body.setHeaders(map);
        makeUrl();
        makeBody();
        header.append(http + " 200 OK" + System.lineSeparator());
        header.append("Content-Type: application/json" + System.lineSeparator());
        header.append("Date: " + dateString + System.lineSeparator());
        header.append("Content-length: " + length + System.lineSeparator());
        header.append("Connection: keep-alive" + System.lineSeparator());
        header.append("Server: gunicorn/19.9.0" + System.lineSeparator());
        header.append("Access-Control-Allow-Origin: *" + System.lineSeparator());
        header.append("Access-Control-Allow-Credentials: true" + System.lineSeparator());
        header.append(System.lineSeparator());

        return header;
    }

    private void checkParamList() {
        paramList = List.of(path.split("\\?")[1].split("\\&"));
        if (paramList.isEmpty()) {
            param = path.split("\\?")[1];
            paramList.add(param);
        }
        paramList.forEach(a -> System.out.println(a));
    }

    void makeUrl() {
        url = "http://" + map.get("Host") + this.path;
        url = url.replace(" ","");
    }

    void makeBody() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        body.setOrigin(this.clientIp.replace("/", ""));
        body.setUrl(this.url);
        jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body) +
            System.lineSeparator();
        length = jsonString.getBytes().length;
    }

    String getBody() {
        return this.jsonString;
    }
}
