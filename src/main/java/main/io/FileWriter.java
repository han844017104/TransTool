package main.io;

import java.io.File;
import java.io.IOException;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/22  13:41
 */
public class FileWriter {
    public static boolean writer(String path, String out) {
        File outFile = null;
        try {
            String outPath = path.replace("\\game\\", "\\trans\\");
            outFile = new File(outPath);
            if (outFile.exists()) outFile.delete();
            outFile.createNewFile();
            java.io.FileWriter writer = new java.io.FileWriter(outPath);
            writer.write(out);
            writer.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
