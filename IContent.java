package com.company;

import java.io.File;

public interface IContent {
    public String getContentType();
    public byte[] getData(String request, File root, File requestedFile);
}
