package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/17  22:25
 */
public class unbugText2 {

    private static final String filePath = "F:\\1\\1\\TakeOver-0.8-pc\\trans\\4";

    public static void main(String[] args) throws Exception{
        List<String> files = findFiles(filePath);
        System.out.println(files);
        for (String file : files) {
            System.out.println("---------" + file + " 开始处理" + "---------");
            doing(file);
            System.out.println("---------" + file + " 处理完毕" + "---------");
        }
    }

    private static List<String> findFiles(String filepath) {
        ArrayList<String> files = new ArrayList<>();
        try {
            File file = new File(filepath);
            if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        if (readfile.getName().endsWith(".rpy")) {
                            files.add(readfile.getPath());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("readfile()   Exception:\n" + e.getMessage());
        }
        return files;
    }

    private static void doing(String path) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line;

        int lineNum = 0;
        while ((line = br.readLine()) != null){
            String out = line;
            lineNum ++;
//            String s = line.replace("[[**]]", "").replace("[**]]", "").replace("[[**]", "").replace("[**]", "");
            String s = line.replace("/i", "");
            if (s.length() < line.length()){
                System.out.println(path + "line : " + lineNum + " before : " + line);
//                System.out.println(path + "line : " + lineNum + " after : " + s);
                out = s;
            }
//            write(path.replace("\\3\\", "\\4\\"), out);
        }
    }

    public static void write(String path, String text) {
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

    private static String[] GetRegResult(String rex, String text) {
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(text);

        ArrayList<String> tempList = new ArrayList<String>();
        while (m.find()) {
            tempList.add(m.group());
        }
        String[] res = new String[tempList.size()];
        int i = 0;
        for (String temp : tempList) {
            res[i] = temp;
            i++;
        }
        return res;
    }
}
