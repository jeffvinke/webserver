package com.company;

import java.io.File;

public class NotImplementedContent implements IContent {
    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public byte[] getData(String request, File root, File requestedFile) {
        return "Not Implemented".getBytes();
    }
}
