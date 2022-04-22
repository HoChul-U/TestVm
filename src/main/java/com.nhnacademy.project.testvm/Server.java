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
            socket.bind(new InetSocketAddress("127.0.0.1", port));
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
            String n = dataParser.makeBody();

//            writer.writeBytes(dataParser.dataParsing());
            Scanner sc = new Scanner(dataParser.dataParsing().toString());

            System.out.println(n);
            String result = "< HTTP/1.1 200 OK"
                + "\n" + "< Date: Thu, 21 Apr 2022 02:56:58 GMT"
                + "\n" + "< Content - Type: application/json"
                + "\n" + "< Content - Length: 33"
                + "\n" + "< Connection;: keep-alive"
                + "\n" + "< Server;: gunicorn/19.9.0"
                + "\n" + "< Access - Control - Allow - Origin;: *"
                + "\n" + "< Access - Control - Allow - Credentials: true";
            //printStream.println(result);
            printWriter.println(result);
            printWriter.flush();
            //writer.writeBytes(sc.nextLine());
            writer.flush();

            //System.out.println(request.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

