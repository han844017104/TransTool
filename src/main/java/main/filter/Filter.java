package main.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  15:57
 */
public class Filter {

    private Map<String, String> item;

    private Rule otherRule;

    public void setOtherRule(Rule otherRule) {
        this.otherRule = otherRule;
    }

    public void setItem(Map<String, String> item) {
        this.item = item;
    }

    public void setItem(List<String> item) {
        HashMap<String, String> map = new HashMap<>();
        item.forEach(i -> map.put(i, ""));
        this.item = map;
    }

    public boolean check(String s) {
        if (otherRule != null && !otherRule.check(s)) {
            return false;
        }
        return this.item.get(s) == null;
    }

}
