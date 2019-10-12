package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class SocketWorker extends Thread {
    private Socket _socket;
    private BufferedReader _inputStream;
    private PrintStream _printStream;
    private BufferedOutputStream _dataStream;

    public SocketWorker(Socket socket) { _socket = socket; }

    public void run() {
        try {
            IContent content = new NotImplementedContent();;
            StringTokenizer tokenizer;
            String verb = "";
            String target = "";
            String request = "";
            byte[] data;

            _inputStream = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            _printStream = new PrintStream(_socket.getOutputStream());
            _dataStream = new BufferedOutputStream(_socket.getOutputStream());

            request = _inputStream.readLine();

            if (request != null) {
                tokenizer = new StringTokenizer(request);
                verb = tokenizer.nextToken().trim().toUpperCase();
                target = tokenizer.nextToken().trim();
            }

            // ignore favorite icon for now
            if (request.contains("favicon")) {
               closeConnection();
               return;
            }

            File root = new File(".");
            File requestedFile = new File(root.getCanonicalPath() + target);

            switch (verb) {
                case "GET":
                    if (requestedFile.isDirectory()) {
                        content = new DirectoryContent();
                    } else {
                        content = new FileContent();
                    }
                    break;
            }

            data = content.getData(target, root, requestedFile);

            // respond with headers
            sendHeaders(data.length, content.getContentType());

            // respond with data
            _dataStream.write(data, 0, data.length);
            _dataStream.flush();

            closeConnection();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendHeaders(int length, String contentType) {
        _printStream.println("HTTP/1.1 200 OK");
        _printStream.println("Date: " + new Date());
        _printStream.println("Content-Type: " + contentType);
        _printStream.println("Content-Length: " + length);
        _printStream.println();
        _printStream.flush();
    }

    private void closeConnection() {
        try {
            _inputStream.close();
            _printStream.close();
            _dataStream.close();
            _socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
