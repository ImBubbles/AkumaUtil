package me.bubbles.bubblemod.files;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileManager {

    // FORMAT --- Name,File
    private HashMap<String, File> filesList = new HashMap<>();

    public FileManager() {
        
    }

    private HashMap<String, File> getFiles() {
        return filesList;
    }

    public File getFile(String key) {
        return filesList.get(key);
    }

    public void registerFile(String key, String file) throws IOException {
        try {
            File newFile = new File(file);
            newFile.createNewFile();
            filesList.put(key, newFile);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        FileHandler logHandler = new FileHandler(filesList.get("log"));

    }

    public void addFile(String name, File file) throws IOException {

        filesList.put(name, file);

        FileHandler logHandler = new FileHandler(filesList.get("log"));

    }


}
