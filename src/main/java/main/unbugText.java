package main;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/17  19:41
 */
public class unbugText {

    private static final String rex = "\"([^\"]*)\"([^\"]*)\"";

    private static final String rexBase = "\"([^\"]*)\"";

    private static final String filePath = "F:\\1\\1\\TakeOver-0.8-pc\\game\\vera.rpy";


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        StringBuffer error = new StringBuffer();
        String line;

        int lineNum = 0;
        while ((line = br.readLine()) != null){
            String out = line;
            lineNum ++;
            if (line.length() - line.replace("\"", "").length() > 0){
                if ((line.length() - line.replace("\"", "").length()) % 2 != 0){
                    String[] result = GetRegResult(rex, line);
                    if (GetRegResult(rexBase, line).length > 1){
                        System.out.println(" 多个规则双引号 : " + line);
                        error.append(lineNum + "  unknow do !!  " + line + "\n");
                    }else if(result.length == 0){
                        System.out.println(" == 0 : " + line);
                        error.append(lineNum + "  match none !!  " + line + "\n");
                    }else if(result.length > 1) {
                        System.out.println(" > 1 : " + line);
                        error.append(lineNum + "  match more than 1 !!  " + line + "\n");
                    }else{
                        System.out.println("else : result[0] = " + result[0]);
                        out = line.replace(result[0], "\"" + result[0].replace("\"", "") +"\"");
                    }
                }else {
                    if ((line.length() - line.replace("\"", "").length()) / 2 > 1){
                        System.out.println(" \" / 2 > 1 : " + line);
                        error.append(lineNum + "  unknow do !!  " + line + "\n");
                    }
                }
                if (!line.equals(out)) {
                    System.out.println("line " + lineNum + " before : " + line);
                    System.out.println("line " + lineNum + " after : " + out);
                }
            }

            write(filePath.replace("\\3\\", "\\4\\"), out);
        }
        System.out.println("------------End-----------");
        System.out.println("Error lines : \n" + error);
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
