package com.nhnacademy.project.testvm;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket socket;
    private BufferedReader reader;
    private DataOutputStream writer;
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
            socket.bind(new InetSocketAddress("192.168.71.72", port));
            clientSocket = socket.accept();
            clientIp = String.valueOf(clientSocket.getInetAddress());

            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream printStream = new PrintStream(clientSocket.getOutputStream());
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
            writer = new DataOutputStream(clientSocket.getOutputStream());

            String l;
            while (!(l = reader.readLine()).isEmpty()) {
                //!(l = reader.readLine()).equals(null)
                request.append(l + "\n");
//                writer.println(readData);
//                writer.flush();
            }
            dataParser = new DataParser(request, clientIp);
            String body = dataParser.makeBody();
            StringBuilder header = dataParser.dataParsing();
//            writer.writeBytes(dataParser.dataParsing());
            //Scanner sc = new Scanner(dataParser.dataParsing().toString());
            System.out.println(header);
            System.out.println(body);

            printWriter.append(header);
            printWriter.append(body);
            printWriter.flush();
            //writer.writeBytes(sc.nextLine());
            //writer.flush();

            //System.out.println(request.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

