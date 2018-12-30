package main.facad;

import main.bean.ApiTypeInfo;
import main.biz.Escaper;
import main.biz.impl.Escaper1;
import main.biz.impl.Escaper2;
import main.biz.impl.Fixer1;
import main.filter.Filter;
import main.filter.RexString;
import main.filter.Rule;
import main.http.Transer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  16:42
 */
public class test {

    public static void main(String[] args) throws IOException {
        Translater translater = new Translater();
        translater.setTranser(new Transer(ApiTypeInfo.BaiDu));
        Filter fileFilter = new Filter();
        HashMap<String, String> map = new HashMap<>();
        map.put("blank.rpy", "blank.rpy");
        map.put("gui.rpy", "blank.rpy");
        map.put("institutions.rpy", "blank.rpy");
        map.put("game control.rpy", "blank.rpy");
        map.put("images.rpy", "blank.rpy");
        map.put("names.rpy", "blank.rpy");
        map.put("navigation.rpy", "blank.rpy");
        map.put("options.rpy", "blank.rpy");
        map.put("screens.rpy", "blank.rpy");
        map.put("script.rpy", "blank.rpy");
        map.put("update.rpy", "blank.rpy");
        fileFilter.setItem(map);
        translater.setFileFilter(fileFilter);
        Filter textFilter = new Filter();
        ArrayList<String> strings2 = new ArrayList<>();
        strings2.add("");
        textFilter.setItem(strings2);
        textFilter.setOtherRule((Rule) str -> {
            String[] result = RexString.GetRegResult(RexString.shuangyinhao, str);
            if (result == null || result.length == 0){
                return false;
            }
            for (String s : result) {
                if (s.contains(".png") || s.contains(".jpg") || s.contains(".mp3") || s.contains(".wav") || s.contains(".txt") || s.contains(".ttf")) {
                    return false;
                }
            }
            String cache = str;
            for (String s : result) {
                cache = cache.replace(s, "");
            }
            if (cache.contains("if") || cache.contains("==")){
                return false;
            }
            return true;
        });
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("Monday", "blank.rpy");
        map2.put("Tuesday", "blank.rpy");
        map2.put("Wednesday", "blank.rpy");
        map2.put("Thursday", "blank.rpy");
        map2.put("Friday", "blank.rpy");
        map2.put("Saturday", "blank.rpy");
        map2.put("Sunday", "blank.rpy");
        map2.put("Secretary", "blank.rpy");
        map2.put("Counselor", "blank.rpy");
        map2.put("Research", "blank.rpy");
        map2.put("Mechanic", "blank.rpy");
        textFilter.setItem(map2);
        translater.setTextFilter(textFilter);
        ArrayList<Escaper> list = new ArrayList<>();
        Escaper1 escaper1 = new Escaper1();
        escaper1.setRex(RexString.zhongkuohao);
        Escaper2 escaper2 = new Escaper2();
        escaper2.setRex(RexString.dakuohao);
        list.add(escaper1);
        list.add(escaper2);
        translater.setEscapers(list);
        translater.setFixer(new Fixer1());
        translater.setInPath("F:\\1\\1\\TakeOver-0.8-pc\\te");
        translater.setOutPath("F:\\1\\1\\TakeOver-0.8-pc\\TEST");
        translater.start();
    }

}
