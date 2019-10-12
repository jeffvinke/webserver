package com.company;

public class Main {

    public static void main(String[] args) {
        SocketConnector socketConnector = new SocketConnector();
        Thread thread = new Thread(socketConnector);
        thread.run();
    }
}
