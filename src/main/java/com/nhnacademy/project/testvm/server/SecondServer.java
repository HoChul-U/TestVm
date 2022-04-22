package com.nhnacademy.project.testvm.server;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.nhnacademy.project.testvm.parser.DataParser;
import com.nhnacademy.project.testvm.parser.SecondDataParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SecondServer {
    private ServerSocket socket;
    private InputStream reader;
    private PrintWriter writer;
    private Socket clientSocket;
    private StringBuilder request = new StringBuilder();
    private SecondDataParser dataParser;
    private String clientIp;

    @Parameter(names = {"port", "-l"})
    int port;
    @Parameter
    String value;

    public static void main(String... argv) {
        SecondServer main = new SecondServer();
        JCommander.newBuilder()
            .addObject(main)
            .build()
            .parse(argv);
        main.run();
        main.start(main.port);
    }

    public void run() {
        System.out.printf("%d\n", port);
    }

    public void start(int port) {
        try {
            socket = new ServerSocket();
            socket.bind(new InetSocketAddress("127.0.0.1", port));
            clientSocket = socket.accept();
            clientIp = String.valueOf(clientSocket.getInetAddress());

            reader = clientSocket.getInputStream();
            writer = new PrintWriter(clientSocket.getOutputStream());

            byte[] byteBufferString = new byte[4048];
            int readSize;
            readSize = reader.read(byteBufferString);

            String test = new String(byteBufferString);
            request = new StringBuilder(test.substring(0, readSize));

            dataParser = new SecondDataParser(request, clientIp);
            StringBuilder header = dataParser.dataParsing();
            String body = dataParser.getBody();
            writer.append(header);
            writer.append(body);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

