package com.nhnacademy.project.testvm.server;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.nhnacademy.project.testvm.parser.DataParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    @Parameter(names = {"port", "-l"})
    int port;

    public static void main(String... argv) {
        Server main = new Server();
        JCommander.newBuilder()
            .addObject(main)
            .build()
            .parse(argv);
        main.start(main.port);
    }

    public void start(int port) {
        try {
            ServerSocket socket = new ServerSocket();
            socket.bind(new InetSocketAddress("127.0.0.1", port));
            Socket clientSocket = socket.accept();
            String clientIp = String.valueOf(clientSocket.getInetAddress());

            InputStream reader = clientSocket.getInputStream();
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

            byte[] byteBufferString = new byte[4048];
            byte[] byteBufferString2 = new byte[4048];
            int readSize = reader.read(byteBufferString);

            String bufferConvertString = new String(byteBufferString);
            StringBuilder request = new StringBuilder(bufferConvertString.substring(0, readSize));

            if(bufferConvertString.contains("multipart/form-data")) {
                readSize = reader.read(byteBufferString2);
                bufferConvertString = new String(byteBufferString2);
                request.append(bufferConvertString.substring(0, readSize));
            }

            DataParser dataParser = new DataParser(request, clientIp);
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

