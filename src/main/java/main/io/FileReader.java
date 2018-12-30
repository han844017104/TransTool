package main.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/22  13:30
 */
public class FileReader {

    public static String readAll(String path) throws IOException {
        File file = new File(path);
        byte[] filebytes = new byte[((Long) file.length()).intValue()];
        FileInputStream ips = new FileInputStream(file);
        try {
            ips.read(filebytes);
            ips.close();
        } finally {
            ips.close();
        }
        return new String(filebytes, StandardCharsets.UTF_8);
    }

    public static ArrayList<String> readLine(String path) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.FileReader(new File(path)));
        ArrayList<String> list = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

}
