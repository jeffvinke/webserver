package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketConnector implements Runnable {
    private ServerSocket _serverSocket;
    private Socket _socket;

    public void run() {
        System.out.println("Starting up server");
        try {
            _serverSocket = new ServerSocket(8080, 10);

            while (true) {
                _socket = _serverSocket.accept();
                new SocketWorker(_socket).run();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
