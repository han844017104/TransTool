package main.biz.impl;

import main.biz.Escaper;
import main.facad.Windows;
import main.filter.RexString;

import java.util.Arrays;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  16:29
 */
public class Escaper2 implements Escaper {

    private String rex;

    public String getRex() {
        return rex;
    }

    public void setRex(String rex) {
        this.rex = rex;
    }

    private String[] cache;

    @Override
    public String escaper(String orign) {
        String[] points = RexString.GetRegResult(this.rex, orign);
        cache = points;
        StringBuilder sb = new StringBuilder(orign);
        for (int i = 0; i < points.length; i++) {
            int start;
            sb.replace(start = sb.indexOf(points[i]), start + points[i].length(), "[[**]]");
        }
        return sb.toString();
    }

    @Override
    public String unEscaper(String orign) {
        String[] points = RexString.GetRegResult(this.rex, orign);
        StringBuilder sb = new StringBuilder(orign);
        try {
            for (int i = 0; i < points.length; i++) {
                int leng = 6;
                int start = sb.indexOf("[[**]]");
                if (start < 0) {
                    leng = 8;
                    start = sb.indexOf("[[* *]]");
                }
                if (start < 0) {
                    start = sb.indexOf("* * * * * *");
                    leng = 11;
                }
                if (start > 0) {
                    sb.replace(start, start + leng, cache[i]);
                } else {
                    Windows.warn("未找到 {} 变量坐标 ！语句 ：" + orign);
                }
            }
        } catch (Exception e) {
            Windows.error("反填充 {} 变量出错！ 语句 ： \n" + orign + "\n变量集合：" + Arrays.toString(points));
        }
        cache = null;
        return sb.toString();
    }
}
