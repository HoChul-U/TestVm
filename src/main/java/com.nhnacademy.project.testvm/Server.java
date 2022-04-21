package com.nhnacademy.project.testvm;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket clientSocket;
    private StringBuilder readData = new StringBuilder();

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
        System.out.printf("%d", port);
    }

    public void start(int port) {
        try {
            socket = new ServerSocket();
            socket.bind(new InetSocketAddress("192.168.71.79", port));
            clientSocket = socket.accept();

            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream());
            String l;
            while (!(l = reader.readLine()).isEmpty()) {
                //!(l = reader.readLine()).equals(null)
                readData.append(l);
//                writer.println(readData);
//                writer.flush();
            }
            System.out.println(readData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
