package com.nhnacademy.project.testvm.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.project.testvm.data.JsonData;
import com.nhnacademy.project.testvm.data.ParsingData;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DataParser {
    private int count = 0;

    private String url;
    private String jsonString;

    private StringBuilder header = new StringBuilder();

    private final StringBuilder request;
    //private final BufferedReader reader;
    private final String clientIp;
    private final Date date = new Date();
    private final JsonData body = new JsonData();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("E, d ,M yyyy HH:mm:ss z");
    private final ParsingData parsingData = new ParsingData();

    private final MakeResponse responseMaker = new MakeResponse();

    public DataParser(StringBuilder request, String clientIp) {
        this.request = request;
        this.clientIp = clientIp;
    }

//    public DataParser(BufferedReader reader, String clientIp) {
//        this.reader = reader;
//        this.clientIp = clientIp;
//    }

    public StringBuilder dataParsing() throws JsonProcessingException {
        String dateString = dateFormat.format(date);
        Scanner scanner = new Scanner(request.toString());
        String line;
        int k=0;
        int b = Integer.MAX_VALUE;

        while ((scanner.hasNextLine())) {
            line = scanner.nextLine();
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

        this.url = responseMaker.makeUrl(this.body, parsingData.getPath());
        this.jsonString = responseMaker.makeBody(this.url, this.clientIp, this.body);
        this.header = responseMaker.makeHeader(dateString,parsingData.getHttp());

        return header;
    }

    private void checkParamList() {
        List<String> paramList = List.of(parsingData.getPath().split("\\?")[1].split("\\&"));
        if (paramList.isEmpty()) {
            parsingData.setParam(parsingData.getPath().split("\\?")[1]);
            paramList.add(parsingData.getParam());
        }
        paramList.forEach(a -> body.putArgs(a.split("=")[0], a.split("=")[1]));
    }

    public String getBody() {
        return this.jsonString;
    }
}
