package com.nhnacademy.project.testvm;

public class DataParser {

    StringBuilder request;
    StringBuilder response = new StringBuilder();

    public DataParser(StringBuilder request) {
        this.request = request;
    }

    void dataParsing() {

        String l = String.valueOf(request.toString().split(" "));

        System.out.println(l);

    }
}
