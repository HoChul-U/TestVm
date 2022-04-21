package com.nhnacademy.project.testvm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Sender extends Thread {
    private final DataOutputStream out;

    public Sender(Socket socket) throws IOException {
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            sendMessage();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isSend() {
        return this.out != null;
    }

    private void sendMessage() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (isSend()) {
                String input = scanner.nextLine();
                if(input.equals("^c")){
                    this.out.writeUTF(input);
                    System.exit(-1);
                }
                this.out.writeUTF(input);
            }
        }
    }
}