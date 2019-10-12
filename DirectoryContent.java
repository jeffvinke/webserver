package com.company;

import java.io.File;
import java.util.StringTokenizer;

public class DirectoryContent implements IContent {
    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public byte[] getData(String request, File root, File requestedFile) {
        byte[] data = null;

        if (request.equals("/")) {
            data = getRoot(root);
        }
        else {
            // get all files in the directory and format them
            data = getTargetDirectory(request, requestedFile);
        }

        return data;
    }

    private byte[] getRoot(File root) {
        String s = "";

        // get file list
        File[] files = root.listFiles();

        // for each file, build links
        for (int i = 0; i < files.length; ++i) {
            s += "<a href=" + files[i].getName() + ">" + files[i].getName()+(files[i].isDirectory() ? "/" : "") +"</a><br />\n";
        }

        // return bytes
        return s.getBytes();
    }

    private byte[] getTargetDirectory(String target, File requestedFile) {
        String s = "";
        String parentDirectory = "";
        File[] files = requestedFile.listFiles();
        StringTokenizer parentDirectoryTokens = new StringTokenizer(target,"/");

        if (parentDirectoryTokens.countTokens() > 1) {
            for (int i = 0; i < parentDirectoryTokens.countTokens(); ++i) {
                parentDirectory += "/" + parentDirectoryTokens.nextToken();
            }
            s += "<a href=" +  parentDirectory + ">[parent directory]</a><br />\n";
        } else if (parentDirectoryTokens.countTokens() == 1) {
            s += "<a href=/>[parent directory]</a><br />\n";
        }

        for (int i = 0; i < files.length; ++i) {
            s += "<a href=" + target + "/" + files[i].getName() + ">" + files[i].getName()+(files[i].isDirectory() ? "/" : "") + "</a><br />\n";
        }

        return s.getBytes();
    }
}
