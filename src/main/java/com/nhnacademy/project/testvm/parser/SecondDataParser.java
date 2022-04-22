package com.nhnacademy.project.testvm.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.project.testvm.data.JsonData;
import com.nhnacademy.project.testvm.data.ParsingData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SecondDataParser {
    private int count = 0;
    private int contentLength;

    private String url;
    private String jsonString;

    private final StringBuilder request;
    private final String clientIp;
    private final Date date = new Date();
    private final JsonData body = new JsonData();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("E, d ,M yyyy HH:mm:ss z");
    private final StringBuilder header = new StringBuilder();
    private final ParsingData parsingData = new ParsingData();

    public SecondDataParser(StringBuilder request, String clientIp) {
        this.request = request;
        this.clientIp = clientIp;
    }

    public StringBuilder dataParsing() throws JsonProcessingException {
        String dateString = dateFormat.format(date);
        Scanner scanner = new Scanner(request.toString());
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {

            if (count == 0) {
                parsingData.setPath(line.split(" ")[1]);
                parsingData.setHttp(line.split(" ")[2]);

                if (parsingData.getPath().contains("?")) {
                    checkParamList();
                }

                count++;
                continue;
            }
            body.putHeaders(line.split(":")[0], line.split(":")[1]);
        }
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            System.out.println(line);
            if(!line.isEmpty()){
                //ToDO
            }
        }
        makeUrl();
        makeBody();
        makeHeader(dateString);

        return header;
    }

    private void makeHeader(String dateString) {
        header.append(parsingData.getHttp() + " 200 OK" + System.lineSeparator());
        header.append("Content-Type: application/json" + System.lineSeparator());
        header.append("Date: " + dateString + System.lineSeparator());
        header.append("Content-length: " + contentLength + System.lineSeparator());
        header.append("Connection: keep-alive" + System.lineSeparator());
        header.append("Server: gunicorn/19.9.0" + System.lineSeparator());
        header.append("Access-Control-Allow-Origin: *" + System.lineSeparator());
        header.append("Access-Control-Allow-Credentials: true" + System.lineSeparator());
        header.append(System.lineSeparator());
    }

    private void checkParamList() {
        List<String> paramList = List.of(parsingData.getPath().split("\\?")[1].split("\\&"));
        if (paramList.isEmpty()) {
            parsingData.setParam(parsingData.getPath().split("\\?")[1]);
            paramList.add(parsingData.getParam());
        }
        paramList.forEach(a -> body.putArgs(a.split("=")[0], a.split("=")[1]));
    }


    void makeUrl() {
        url = "http://" + body.getHeaders().get("Host") + parsingData.getPath();
        url = url.replace(" ", "");
    }

    void makeBody() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        body.setOrigin(this.clientIp.replace("/", ""));
        body.setUrl(this.url);
        jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body) +
            System.lineSeparator();
        contentLength = jsonString.getBytes().length;
    }

    public String getBody() {
        return this.jsonString;
    }
}
