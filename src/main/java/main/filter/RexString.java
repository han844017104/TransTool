package main.filter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  16:16
 */
public class RexString {

    public static final String shuangyinhao = "\"([^\"]*)\"";
    public static final String zhongkuohao = "\\[[^\\[\\]]+\\]";
    public static final String dakuohao = "\\{([^\\}]+)\\}";
    public static final String daixingdezhongkuohao = "(\\[[\\**]*\\])";
    public static final String duozhongkuohao = "(\\[){2,}|(\\]){2,}";

    public static String[] GetRegResult(String rex, String text) {
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
