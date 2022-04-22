package com.nhnacademy.project.testvm.server;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.util.ByteBufferBackedOutputStream;
import com.google.common.io.ByteStreams;
import com.nhnacademy.project.testvm.parser.DataParser;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    private ServerSocket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket clientSocket;
    private StringBuilder request = new StringBuilder();
    private DataParser dataParser;
    private String clientIp;

    @Parameter(names = {"port", "-l"})
    int port;
    @Parameter
    String value;

    public static void main(String... argv) {
        Server main = new Server();
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

            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream());


            byte[] bytes = clientSocket.getInputStream().readAllBytes();
            String l;
            while (!(l = reader.readLine()).isEmpty()) {
                request.append(l + "\n");
            }
            dataParser = new DataParser(request, clientIp);
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

