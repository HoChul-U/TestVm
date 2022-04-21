package com.nhnacademy.project.testvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        int k = Integer.MIN_VALUE;
        boolean b = true;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (b) {
                k++;
                b = k < Integer.MAX_VALUE;
                try {
                    Socket socket = serverSocket.accept();
                    ClientSession client = new ClientSession(socket);
                    client.start();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static class ClientSession extends Thread {
        private final Socket socket;
        private final DataInputStream in;

        ClientSession(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            connect();
        }

        private void connect() {
            if (isConnect()) {
                try {
                    Thread sender = new Sender(this.socket);
                    Thread receiver = new Receiver(this.socket);

                    sender.start();
                    receiver.start();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        private boolean isConnect() {
            return this.in != null;
        }
    }
}