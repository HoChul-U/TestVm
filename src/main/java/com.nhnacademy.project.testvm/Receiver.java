package com.nhnacademy.project.testvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver extends Thread {
    private final DataInputStream in;

    public Receiver(Socket socket) throws IOException {
        this.in = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (isReceivable()) {
            receiveMessage();
        }
        System.exit(-1);
    }

    private boolean isReceivable() {
        return this.in != null;
    }

    private void receiveMessage() {
        try {
            String output = in.readUTF();
            if(output.equals("^c")){
                System.exit(-1);
            }
            System.out.println(output);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
