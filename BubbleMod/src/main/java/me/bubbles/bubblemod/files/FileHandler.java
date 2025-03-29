package me.bubbles.bubblemod.files;

import me.bubbles.bubblemod.Client;
import net.minecraft.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileHandler {

    private File file;

    public FileHandler(File file) {
        this.file=file;
    }

    public FileHandler(String str) {
        this.file=new File(str);
    }

    public boolean isEmpty() {
        return file.length()==0;
    }

    public void write(String str) throws IOException {

        BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
        out.append(str+"\n");
        out.close();

    }

    public void replace(String key, String oldValue, String newValue, String regex) throws FileNotFoundException {

        String result;

        String input = null;
        Scanner sc = new Scanner(file);
        StringBuilder sb = new StringBuilder();

        boolean first = true;

        while (sc.hasNextLine()) {

            input = sc.nextLine();

            if(!first) {
                sb.append("\n"+input);
            }else {
                sb.append(input);
            }

            first=false;
        }

        result=sb.toString().replace(key+regex+oldValue,key+regex+newValue);

        PrintWriter writer = new PrintWriter(file);
        writer.append(result);
        writer.flush();

        sc.close();

    }

    public HashMap<String, String> getStringStringData(String regex) throws FileNotFoundException {

        HashMap<String, String> result = new HashMap<>();

        Scanner scanner = new Scanner(file);
        ArrayList<String> data = new ArrayList<>();

        while(scanner.hasNextLine()) {
            data.add(scanner.nextLine());
        }

        scanner.close();

        for(String entry : data) {
            String[] split = entry.split(regex);
            result.put(split[0],split[1]);
        }

        return result;

    }

    public ArrayList<String> getStringData() throws FileNotFoundException {

        Scanner scanner = new Scanner(file);
        ArrayList<String> data = new ArrayList<>();

        while(scanner.hasNextLine()) {
            data.add(scanner.nextLine());
        }

        scanner.close();

        return data;

    }

    private String fileToString(String filePath) throws Exception{
        String input = null;
        Scanner sc = new Scanner(new File(filePath));
        StringBuffer sb = new StringBuffer();
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            sb.append(input);
        }
        return sb.toString();
    }

}
