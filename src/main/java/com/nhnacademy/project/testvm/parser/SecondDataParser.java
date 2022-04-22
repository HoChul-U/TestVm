package com.nhnacademy.project.testvm.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.project.testvm.data.JsonData;
import com.nhnacademy.project.testvm.data.ParsingData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SecondDataParser {
    private int count = 0;

    private String url;
    private String jsonString;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final StringBuilder request;
    private final String clientIp;
    private final Date date = new Date();
    private final JsonData body = new JsonData();
    private final MakeResponse makeResponse = new MakeResponse();
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
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            System.out.println(line);
            body.setData(line);
            System.out.println(body.getData());
            Map<String, String> map = objectMapper.readValue(line, Map.class);
            body.setJson(map);

            System.out.println(map.toString());
        }
        url = makeResponse.makeUrl(body.getHeaders().get("Host"), parsingData.getPath());
        jsonString = makeResponse.makeBody(body, clientIp, url);
        makeResponse.makeHeader(header, dateString, parsingData.getHttp(),
            makeResponse.contentLength);
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
