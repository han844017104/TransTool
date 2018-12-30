package main.io;

import main.facad.Windows;
import main.filter.Filter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/22  13:23
 */
public class FileFinder {

    private static Filter filter = null;

    public static void setFilter(Filter filter) {
        FileFinder.filter = filter;
    }

    public static List<String> findFiles(String filepath) {
        ArrayList<String> files = new ArrayList<>();
        try {
            File file = new File(filepath);
            if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        if (readfile.getName().endsWith(".rpy")) {
                            if (filter != null) {
                                if (filter.check(readfile.getName())) {
                                    files.add(readfile.getPath());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Windows.error("readfile()   Exception:\n" + e.getMessage());
        }
        return files;
    }
}
