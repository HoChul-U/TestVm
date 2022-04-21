//package com.nhnacademy.project.testvm;
//
//import java.io.IOException;
//import java.net.Socket;
//
//public class Client {
//    private final String hostName;
//    private final int port;
//
//    public Client(String hostName, int port) {
//        this.hostName = hostName;
////        this.port = port;
//    }
//
//    public void connect() {
//        try {
//            Socket socket = new Socket(hostName, port);
//
//            Thread sender = new Sender(socket);
//            Thread receiver = new Receiver(socket);
//
//            sender.start();
//            receiver.start();
//        }  catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}