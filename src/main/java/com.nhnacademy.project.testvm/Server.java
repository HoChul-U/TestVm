package com.nhnacademy.project.testvm;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            socket = new ServerSocket(port);
            clientSocket = socket.accept();

            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream());

            while (true) {
                //!(readData = reader.readLine()).equals(null)
                readData.append(reader.readLine());
                System.out.println(readData);
//                writer.println(readData);
//                writer.flush();
            }
//            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
