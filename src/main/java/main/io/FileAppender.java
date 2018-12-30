package main.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/22  13:41
 */
public class FileAppender {

    public static void append(String path, String text) {
        FileWriter fw = null;
        try {
            File f=new File(path);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(text);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
