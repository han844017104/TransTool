package main.biz.impl;

import main.biz.Fixer;
import main.filter.RexString;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  16:37
 */
public class Fixer1 implements Fixer {
    @Override
    public String fix(String text) {
        String out = text;
        String[] result = RexString.GetRegResult(RexString.duozhongkuohao, text);
        if (result != null && result.length >0){
            for (String s : result) {
                String s1 = s.replace("[", "");
                String s2 = s1.replace("]", "");
                out.replace(s, "["+ s2 + "]");
            }
        }
        String[] result2 = RexString.GetRegResult(RexString.daixingdezhongkuohao, out);
        if (result2 != null && result2.length >0){
            for (String s : result) {
                out = out.replace(s, "");
            }
        }
        return "\"" + out.replace("\"", "") + "\"";
    }
}
