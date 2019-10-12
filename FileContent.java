package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileContent implements IContent{

    @Override
    public String getContentType() {
        return "text/plain";
    }

    @Override
    public byte[] getData(String request, File root, File requestedFile) {
        byte[] data = null;


        try {
            Path path = Paths.get(requestedFile.toString());
            data = Files.readAllBytes(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return data;
    }
}
